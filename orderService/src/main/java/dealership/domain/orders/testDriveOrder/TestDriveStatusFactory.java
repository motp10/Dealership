package dealership.domain.orders.testDriveOrder;

import dealership.domain.orders.Status;
import dealership.domain.orders.testDriveOrder.status.*;

public class TestDriveStatusFactory {
    public Status Create(String statusName) {
        String capsStatusName = statusName.toUpperCase();
        return switch (statusName.toUpperCase()) {
            case "WAITING" -> new TestDriveOrderWaiting();
            case "READY" -> new TestDriveOrderReady();
            case "CONFIRMED" -> new TestDriveOrderConfirmed();
            case "CLOSED" -> new TestDriveOrderClosed();
            case "CANCELED", "CANCELLED" -> new TestDriveOrderCanceled();
            default -> throw new IllegalArgumentException("No such status for TestDriveOrder: " + statusName);
        };
    }
}
