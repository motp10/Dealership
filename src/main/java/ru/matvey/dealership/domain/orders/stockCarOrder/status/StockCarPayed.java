package ru.matvey.dealership.domain.orders.stockCarOrder.status;

import ru.matvey.dealership.domain.orders.IStatus;

public class StockCarPayed implements IStatus {
    @Override
    public IStatus next() {
        return null;
    }

    @Override
    public IStatus cancel() {
        return null;
    }

    @Override
    public String getName() {
        return "";
    }
}
