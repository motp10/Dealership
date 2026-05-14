package dealership.abstraction;


import dealership.domain.orders.configurationOrder.ConfigurationOrder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ComplictaionOrderRepository {
    void add(ConfigurationOrder order);
    void delete(UUID orderId);
    Optional<ConfigurationOrder> findById(UUID orderId);
    List<ConfigurationOrder> getAll();
}
