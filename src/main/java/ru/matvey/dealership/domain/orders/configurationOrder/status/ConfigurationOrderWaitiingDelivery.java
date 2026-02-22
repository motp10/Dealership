package ru.matvey.dealership.domain.orders.configurationOrder.status;

import ru.matvey.dealership.domain.orders.IStatus;

public class ConfigurationOrderWaitiingDelivery implements IStatus {
    @Override
    public IStatus next() {
        return null;
    }

    @Override
    public IStatus cancel() {
        return null;
    }

    @Override
    public String getName() {
        return "";
    }
}
