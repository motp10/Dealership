package dealership.domain.orders.configurationOrder.status;

import dealership.domain.orders.Status;
import dealership.domain.orders.configurationOrder.status.ConfigurationOrderCanceled;
import dealership.domain.orders.configurationOrder.status.ConfigurationOrderConfirmed;

public class ConfigurationOrderCreated implements Status {
    @Override
    public Status next() {
        return new ConfigurationOrderConfirmed();
    }

    @Override
    public Status cancel() {
        return new ConfigurationOrderCanceled();
    }

    @Override
    public String getName() {
        return "Created";
    }
}
