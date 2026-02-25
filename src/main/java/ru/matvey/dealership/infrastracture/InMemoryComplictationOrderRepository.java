package ru.matvey.dealership.infrastracture;

import ru.matvey.dealership.Abstraction.ComplictaionOrderRepository;
import ru.matvey.dealership.domain.orders.configurationOrder.ConfigurationOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryComplictationOrderRepository implements ComplictaionOrderRepository {
    private List<ConfigurationOrder> storage = new ArrayList<>();
    @Override
    public void add(ConfigurationOrder order) {
        storage.add(order);
    }

    @Override
    public void delete(UUID orderId) {
        storage.removeIf(order -> order.getId().equals(orderId));
    }

    @Override
    public ConfigurationOrder getOrder(UUID orderId) {
        return storage.stream()
                .filter(order -> order.getId().equals(orderId))
                .map(order -> (ConfigurationOrder) order)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<ConfigurationOrder> getAll() {
        return storage;
    }
}
