package ru.matvey.dealership.Abstraction;

import ru.matvey.dealership.domain.orders.configurationOrder.ConfigurationOrder;

import java.util.List;
import java.util.UUID;

public interface ComplictaionOrderRepository {
    void add(ConfigurationOrder order);
    void delete(UUID orderId);
    ConfigurationOrder getOrder(UUID orderId);
    List<ConfigurationOrder> getAll();
}
