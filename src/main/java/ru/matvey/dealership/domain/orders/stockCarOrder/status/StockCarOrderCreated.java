package ru.matvey.dealership.domain.orders.stockCarOrder.status;


import ru.matvey.dealership.domain.orders.IStatus;

public class StockCarOrderCreated implements IStatus {
    @Override
    public IStatus next() {
        return new StockCarOrderConfirmed();
    }

    @Override
    public IStatus cancel() {
        return new StockCarOrderCanceled();
    }

    @Override
    public String getName() {
        return "Оформлен";
    }
}