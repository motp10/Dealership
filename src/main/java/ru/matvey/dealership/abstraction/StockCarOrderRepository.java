package ru.matvey.dealership.abstraction;

import ru.matvey.dealership.domain.orders.stockCarOrder.OrderStockCar;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StockCarOrderRepository{
    void addOrder(OrderStockCar order);
    Optional<OrderStockCar> findById(UUID id);
    List<OrderStockCar> getAll();
}