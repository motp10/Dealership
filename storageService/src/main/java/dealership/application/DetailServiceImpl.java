package dealership.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import dealership.abstraction.AssemblyOrderRepository;
import dealership.abstraction.StorageOutboxRepository;
import dealership.domain.assemblyOrder.AssemblyOrder;
import dealership.domain.message.OutboxMessage;
import dealership.events.OrderApprovedEvent;
import dealership.events.OrderSentForApprovalEvent;
import dealership.exceptions.EntityNotFoundException;
import dealership.topics.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import dealership.abstraction.DetailsRepository;
import dealership.contracts.mappers.DetailDtoMapper;
import dealership.contracts.models.DetailDto;
import dealership.contracts.operations.DetailOperation;
import dealership.contracts.services.DetailService;
import dealership.domain.details.Detail;
import dealership.domain.details.DetailsFactory;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class DetailServiceImpl implements DetailService {
    private final DetailsRepository detailsRepository;
    private final AssemblyOrderRepository assemblyOrderRepository;
    private final StorageOutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;
    @Override
    @PreAuthorize("hasAnyRole('WAREHOUSEADMIN', 'ADMIN')")
    public UUID addDetail(DetailOperation.Request request) {
        DetailsFactory detailsFactory = new DetailsFactory();
        Detail detail = detailsFactory.createDetail(request.detailType(), request.detailInfo(), request.price(), request.suitableCars());
        detailsRepository.add(detail);
        return detail.getId();
    }

    @Override
    @PreAuthorize("hasAnyRole('WAREHOUSEADMIN', 'ADMIN')")
    public void updateDetail(UUID id, DetailOperation.Request updatedDetail) {
        DetailsFactory factory = new DetailsFactory();
        Detail detail = factory.createDetail(updatedDetail.detailType(), updatedDetail.detailInfo(), updatedDetail.price(), updatedDetail.suitableCars());
        detailsRepository.delete(id);
        detailsRepository.add(detail);
    }

    @Override
    @PreAuthorize("hasAnyRole('WAREHOUSEADMIN', 'ADMIN')")
    public void deleteDetail(UUID id) {
        detailsRepository.delete(id);
    }

    @Override
    @PreAuthorize("hasAnyRole('WAREHOUSEADMIN', 'ADMIN')")
    public DetailDto getDetail(UUID id) {
        var detail = detailsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Detail with id " + id + " not found"));

        return DetailDtoMapper.toDto(detail);
    }

    @Override
    @PreAuthorize("hasAnyRole('WAREHOUSEADMIN', 'ADMIN')")
    public List<DetailDto> getAllDetails() {
        return detailsRepository.getDetails().stream()
                .map(DetailDto::new)
                .toList();
    }

    @Override
    public void processAssemblyOrder(OrderSentForApprovalEvent event) {
        if (assemblyOrderRepository.existsBySourceOrderId(event.orderId())) {
            log.warn("Order {} already processed", event.orderId());
            return;
        }

        boolean allDetailsExist = true;
        for (var detail : event.details()) {
            allDetailsExist &= detailsRepository.detailExists(detail.detailType(), detail.detailInfo());
        }


        AssemblyOrder assembly = new AssemblyOrder();
        assembly.setSourceOrderId(event.orderId());
        assembly.setTraceId(event.traceId());
        assembly.setStatus("ASSEMBLED");
        assemblyOrderRepository.save(assembly);

        var responseEvent = new OrderApprovedEvent(
                event.orderId(), event.traceId(), Instant.now(), allDetailsExist, assembly.getId()
        );

        try {
            OutboxMessage outbox = new OutboxMessage();
            outbox.setEventType(KafkaTopics.STORAGE_STATUS);
            outbox.setId(event.traceId());
            outbox.setPayload(objectMapper.writeValueAsString(responseEvent));
            outboxRepository.save(outbox);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize response event", e);
        }
    }
}
