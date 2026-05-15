package ru.matvey.dealership.domain.orders.stockCarOrder.status;

import ru.matvey.dealership.domain.orders.Status;

public class StockCarReady implements Status {
    @Override
    public Status next() {
        return new StockCarOrderClosed();
    }

    @Override
    public Status cancel() {
        throw new RuntimeException("order cannot be canceld");
    }

    @Override
    public String getName() {
        return "ready";
    }
}
