package dealership.domain.orders.stockCarOrder;


import dealership.domain.orders.Status;
import dealership.domain.orders.stockCarOrder.status.*;

public class StockCarOrderStatusFactory {
    public Status Create(String statusName) {
        String capsStatusName = statusName.toUpperCase();
        return switch (capsStatusName) {
            case "CREATED" -> new StockCarOrderCreated();
            case "CONFIRMED" -> new StockCarOrderConfirmed();
            case "WAITING_PAYMENT" -> new StockCarOrderWaitingPayment();
            case "PAYED" -> new StockCarPayed();
            case "READY" -> new StockCarReady();
            case "CANCELED" -> new StockCarOrderCanceled();
            case "CLOSED" -> new StockCarOrderClosed();
            default -> throw new IllegalArgumentException("No such status for StockCarOrder: " + statusName);
        };
    }
}