package ru.matvey.dealership.domain.orders.configurationOrder;

import ru.matvey.dealership.domain.orders.Status;
import ru.matvey.dealership.domain.orders.configurationOrder.status.*;

public class ConfigurationOrderStatusFactory {
    public Status Create(String statusName) {
        String capsStatusName = statusName.toUpperCase();
        return switch (capsStatusName) {
            case "CREATED" -> new ConfigurationOrderCreated();
            case "CONFIRMED" -> new ConfigurationOrderConfirmed();
            case "WAITING_PAYMENT" -> new ConfigurationOrderWaitingPayment();
            case "PAYED" -> new ConfigurationOrderPayed();
            case "WAITING_DELIVERY" -> new ConfigurationOrderWaitingDelivery();
            case "WAITING_CLIENT" -> new ConfigurationOrderWaitingClient();
            case "CANCELED" -> new ConfigurationOrderCanceled();
            case "CLOSED" -> new ConfigurationOrderClosed();
            default -> throw new IllegalArgumentException("No such status for ConfigurationOrder: " + statusName);
        };
    }
}