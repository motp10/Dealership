package ru.matvey.dealership.infrastracture;

import ru.matvey.dealership.abstraction.StockCarOrderRepository;
import ru.matvey.dealership.domain.orders.stockCarOrder.OrderStockCar;

import java.util.*;

public class InMemoryStockCarOrderRepository implements StockCarOrderRepository {
    private final List<OrderStockCar> storage = new ArrayList<OrderStockCar>();

    @Override
    public void addOrder(OrderStockCar order) {
        storage.add(order);
    }
    @Override
    public OrderStockCar findById(UUID id) {
        return storage.stream().filter((x)->{return x.getId() == id; }).toList().getFirst();
    }

    @Override
    public List<OrderStockCar> getAll() {
        return storage;
    }
}