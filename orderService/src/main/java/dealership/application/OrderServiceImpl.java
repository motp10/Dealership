package dealership.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dealership.abstraction.*;
import dealership.contracts.models.ConfigurationOrderDto;
import dealership.contracts.models.OrderDto;
import dealership.domain.message.MessageStatus;
import dealership.domain.message.OutboxMessage;
import dealership.domain.orders.Order;
import dealership.domain.orders.configurationOrder.ConfigurationOrder;
import dealership.events.OrderSentForApprovalEvent;
import dealership.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jboss.logging.MDC;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import dealership.contracts.mappers.ConfigurationOrderDtoMapper;
import dealership.contracts.mappers.OrderDtoMapper;
import dealership.contracts.operations.CarComplictation;
import dealership.contracts.services.OrderService;
import dealership.domain.orders.stockCarOrder.OrderStockCar;
import dealership.domain.orders.testDriveOrder.TestDriveOrder;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final StockCarOrderRepository stockCarOrderRepository;
    private final TestDriveRepository testDriveRepository;
    private final ComplictaionOrderRepository complictaionOrderRepository;
    private final MessageRepository messageRepository;

    private final ObjectMapper objectMapper;
    @Override
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'USER')")
    public UUID makeStockCarOrder(UUID carID, UUID clientId) {
        UUID managerId = null;
        OrderStockCar order = new OrderStockCar(clientId, carID, managerId);
        stockCarOrderRepository.addOrder(order);
        return order.getId();
    }

    @Override
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'USER')")
    public UUID makeOrderWithConfiguration(CarComplictation complictation, UUID clientId) {
        UUID assemblyOrderId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();

        ConfigurationOrder configurationOrder = new ConfigurationOrder(clientId, null, null);

        List<OrderSentForApprovalEvent.DetailItem> detailItems = List.of(
                new OrderSentForApprovalEvent.DetailItem("BODY", complictation.bodyType()),
                new OrderSentForApprovalEvent.DetailItem("COLOR", complictation.color()),
                new OrderSentForApprovalEvent.DetailItem("FUEL", complictation.fuelType()),
                new OrderSentForApprovalEvent.DetailItem("TRANSMISSION", complictation.transmissionType()),
                new OrderSentForApprovalEvent.DetailItem("DRIVELAYOUT", complictation.drivelayoutType())
        );

        OrderSentForApprovalEvent event = new OrderSentForApprovalEvent(
                orderId,
                (UUID) MDC.get("traceId"),
                Instant.now(),
                assemblyOrderId,
                detailItems
        );

        OutboxMessage outbox = new OutboxMessage();
        outbox.setId(UUID.randomUUID());
        try {
            outbox.setPayload(objectMapper.writeValueAsString(event));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Serialization error", e);
        }
        outbox.setEventType("OrderSentForApproval");
        outbox.setStatus(MessageStatus.PENDING);
        outbox.setCreatedAt(Instant.now());

        messageRepository.save(outbox);
        complictaionOrderRepository.add(configurationOrder);

        return orderId;
    }

    @Override
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'USER')")
    public UUID makeTestDriveOrder(UUID carId, UUID clientId) {
        UUID managerId = null;
        TestDriveOrder order = new TestDriveOrder(clientId, carId, managerId);
        testDriveRepository.add(order);
        return order.getId();
    }

    @Override
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'USER') or @SecurityChecker.isConfigOrderOwner(#id, #clientId)")
    public ConfigurationOrderDto getConfigurationOrder(UUID id, UUID clientId) {
        return complictaionOrderRepository.findById(id).map(ConfigurationOrderDtoMapper::toDto).orElseThrow(() -> new EntityNotFoundException("no order with this id:" + id));
    }

    @Override
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public List<ConfigurationOrderDto> getAllConfigurationOrders() {
        return complictaionOrderRepository.getAll().stream()
                .map(ConfigurationOrderDtoMapper::toDto)
                .toList();
    }

    @Override
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'USER') or @SecurityChecker.isConfigOrderOwner(#id, #clientId)")
    public OrderDto getStockCarOrder(UUID id, UUID clientId) {
        return stockCarOrderRepository.findById(id).map(OrderDtoMapper::toDto).orElseThrow(() -> new EntityNotFoundException("no order with this id:" + id));
    }

    @Override
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public List<OrderDto> getAllStockCarOrders() {
        return stockCarOrderRepository.getAll().stream()
                .map(OrderDtoMapper::toDto)
                .toList();
    }

    @Override
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN') or @SecurityChecker.isConfigOrderOwner(#id, #clientId)")
    public OrderDto getTestDriveOrder(UUID id, UUID clientId) {
        return testDriveRepository.findById(id).map(OrderDtoMapper::toDto).orElseThrow(() -> new EntityNotFoundException("no order with this id:" + id));
    }

    @Override
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public List<OrderDto> getAllTestDriveOrders() {
        return testDriveRepository.getAll().stream()
                .map(OrderDtoMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public void updateOrderStatus(UUID id) {
        Order order = Stream.of(
                        complictaionOrderRepository.findById(id),
                        stockCarOrderRepository.findById(id),
                        testDriveRepository.findById(id)
                )
                .flatMap(Optional::stream)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        UUID managerId = UUID.fromString(jwt.getSubject());
        if (order.getManagerId() == null) {
            order.setManagerId(managerId);

        }
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN') or @SecurityChecker.isConfigOrderOwner(#id, #clientId)")
    public void cancelOrder(UUID id, UUID clientId) {
        complictaionOrderRepository.findById(id).ifPresent(order -> order.cancel());
        stockCarOrderRepository.findById(id).ifPresent(order -> order.cancel());
        testDriveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("no order with this id:" + id)).cancel();
    }

    @Transactional
    @Override
    public void cancelOrderSystem(UUID id) {
        complictaionOrderRepository.findById(id).ifPresent(order -> order.cancel());
        stockCarOrderRepository.findById(id).ifPresent(order -> order.cancel());
        testDriveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("no order with this id:" + id)).cancel();
    }

    @Transactional
    @Override
    public void updateOrderStatusSystem(UUID id) {
        complictaionOrderRepository.findById(id).ifPresent(order -> order.nextStep());
        stockCarOrderRepository.findById(id).ifPresent(order -> order.nextStep());
        testDriveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("no order with this id:" + id)).nextStep();
    }
}
