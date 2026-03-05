package ru.matvey.dealership.abstraction;

import ru.matvey.dealership.domain.orders.stockCarOrder.OrderStockCar;

import java.util.List;
import java.util.UUID;

public interface StockCarOrderRepository{
    void addOrder(OrderStockCar order);
    OrderStockCar findById(UUID id);
    List<OrderStockCar> getAll();
}