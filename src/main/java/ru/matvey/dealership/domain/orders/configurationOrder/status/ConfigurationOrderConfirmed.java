package ru.matvey.dealership.domain.orders.configurationOrder.status;

import ru.matvey.dealership.domain.orders.Status;

public class ConfigurationOrderConfirmed implements Status {
    @Override
    public Status next() {
        return new ConfigurationOrderWaitingPayment();
    }

    @Override
    public Status cancel() {
        return new ConfigurationOrderCanceled();
    }

    @Override
    public String getName() {
        return "confirmed";
    }
}
