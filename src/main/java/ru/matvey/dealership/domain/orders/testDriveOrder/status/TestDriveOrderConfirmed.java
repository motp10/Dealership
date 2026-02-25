package ru.matvey.dealership.domain.orders.testDriveOrder.status;

import ru.matvey.dealership.domain.orders.Status;

public class TestDriveOrderConfirmed implements Status {
    @Override
    public Status next() {
        return new TestDriveOrderWaiting();
    }

    @Override
    public Status cancel() {
        return null;
    }

    @Override
    public String getName() {
        return "confirmed";
    }
}
