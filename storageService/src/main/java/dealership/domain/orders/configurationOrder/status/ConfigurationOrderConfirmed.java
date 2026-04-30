package dealership.domain.orders.configurationOrder.status;

import dealership.domain.orders.Status;
import dealership.domain.orders.configurationOrder.status.ConfigurationOrderWaitingPayment;

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
