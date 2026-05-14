package dealership.contracts.services;

import dealership.contracts.models.DetailDto;
import dealership.contracts.operations.DetailOperation;
import dealership.events.OrderSentForApprovalEvent;

import java.util.List;
import java.util.UUID;

public interface DetailService {
    UUID addDetail(DetailOperation.Request request);
    void updateDetail(UUID id, DetailOperation.Request request);
    void deleteDetail(UUID id);
    DetailDto getDetail(UUID id);
    List<DetailDto> getAllDetails();
    void processAssemblyOrder(OrderSentForApprovalEvent event);

}
