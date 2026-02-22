package ru.matvey.dealership.infrastracture;

import ru.matvey.dealership.Abstraction.ComplictaionOrderRepository;
import ru.matvey.dealership.domain.orders.IOrder;
import ru.matvey.dealership.domain.orders.configurationOrder.ConfigurationOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryComplictationOrderRepository implements ComplictaionOrderRepository {
    private List<ConfigurationOrder> storege = new ArrayList<>();
    @Override
    public void add(ConfigurationOrder order) {
        storege.add(order);
    }

    @Override
    public void delete(UUID orderId) {
        IOrder orderToDelete = storege.stream().filter((x)->{return x.getId() == orderId; }).toList().getFirst();
        storege.remove(orderToDelete);
    }

    @Override
    public ConfigurationOrder getOrder(UUID orderId) {
        return storege.stream().filter((x)->{return x.getId() == orderId; }).toList().getFirst();
    }

    @Override
    public List<ConfigurationOrder> getAll() {
        return storege;
    }
}
