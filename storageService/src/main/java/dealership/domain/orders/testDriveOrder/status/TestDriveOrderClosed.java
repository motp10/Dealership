package dealership.domain.orders.testDriveOrder.status;

import dealership.domain.orders.Status;

public class TestDriveOrderClosed implements Status {
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
