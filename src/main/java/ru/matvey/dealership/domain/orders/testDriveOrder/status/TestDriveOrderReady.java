package ru.matvey.dealership.domain.orders.testDriveOrder.status;

import ru.matvey.dealership.domain.orders.Status;

public class TestDriveOrderReady implements Status {
    @Override
    public Status next() {
        return new TestDriveOrderClosed();
    }

    @Override
    public Status cancel() {
        return new TestDriveOrderCanceled();
    }

    @Override
    public String getName() {
        return "ready";
    }
}
