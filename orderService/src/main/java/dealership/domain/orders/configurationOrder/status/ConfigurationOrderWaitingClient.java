package dealership.domain.orders.configurationOrder.status;

import dealership.domain.orders.Status;
public class ConfigurationOrderWaitingClient implements Status {
    @Override
    public Status next() {
        return new ConfigurationOrderClosed();
    }

    @Override
    public Status cancel() {
        throw new RuntimeException("order cannot be canceld");
    }

    @Override
    public String getName() {
        return "waiting client";
    }
}