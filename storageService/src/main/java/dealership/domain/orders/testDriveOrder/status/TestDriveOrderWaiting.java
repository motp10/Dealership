package dealership.domain.orders.testDriveOrder.status;


import dealership.domain.orders.Status;
import dealership.domain.orders.testDriveOrder.status.TestDriveOrderCanceled;

public class TestDriveOrderWaiting implements Status {
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
        return "waiting";
    }
}
