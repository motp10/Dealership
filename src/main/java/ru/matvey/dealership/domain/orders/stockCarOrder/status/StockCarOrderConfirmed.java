package ru.matvey.dealership.domain.orders.stockCarOrder.status;

import ru.matvey.dealership.domain.orders.Status;

public class StockCarOrderConfirmed implements Status {
    @Override
    public Status next() {
        return new StockCarOrderWaitingPayment();
    }

    @Override
    public Status cancel() {
        return new StockCarOrderCanceled();
    }

    @Override
    public String getName() {
        return "";
    }
}
