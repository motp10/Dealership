package ru.matvey.dealership.domain.orders.stockCarOrder.status;

import ru.matvey.dealership.domain.orders.Status;

public class StockCarOrderClosed implements Status {
    @Override
    public Status next() {
        return this;
    }

    @Override
    public Status cancel() {
        return this;
    }

    @Override
    public String getName() {
        return "closed";
    }
}
