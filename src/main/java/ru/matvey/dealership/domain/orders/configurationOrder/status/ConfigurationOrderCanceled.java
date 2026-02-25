package ru.matvey.dealership.domain.orders.configurationOrder.status;

import ru.matvey.dealership.domain.orders.Status;

public class ConfigurationOrderCanceled implements Status {
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
        return "canceled";
    }
}
