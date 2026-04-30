package dealership.domain.orders.configurationOrder.status;

import dealership.domain.orders.Status;
import dealership.domain.orders.configurationOrder.status.ConfigurationOrderWaitingDelivery;

public class ConfigurationOrderPayed implements Status {
    @Override
    public Status next() {
        return new ConfigurationOrderWaitingDelivery();
    }

    @Override
    public Status cancel() {
        throw new RuntimeException("order cannot be canceld");
    }

    @Override
    public String getName() {
        return "payed";
    }
}
