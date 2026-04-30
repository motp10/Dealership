package dealership.domain.orders.configurationOrder.status;

import dealership.domain.orders.Status;

public class ConfigurationOrderWaitingPayment implements Status {
    @Override
    public Status next() {
        return new ConfigurationOrderPayed();
    }

    @Override
    public Status cancel() {
        return new ConfigurationOrderCanceled();
    }

    @Override
    public String getName() {
        return "waiting payment";
    }
}
