package ru.matvey.dealership.domain.orders.configurationOrder.status;

import ru.matvey.dealership.domain.orders.Status;

public class ConfigurationOrderClosed implements Status {
    @Override
    public Status next() {
        return this;
    }

    @Override
    public Status cancel() {
        throw new RuntimeException("state cannot be canceled");
    }

    @Override
    public String getName() {
        return "closed";
    }
}
