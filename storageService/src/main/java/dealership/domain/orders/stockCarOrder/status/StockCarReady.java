package dealership.domain.orders.stockCarOrder.status;

import dealership.domain.orders.Status;
import dealership.domain.orders.stockCarOrder.status.StockCarOrderClosed;

public class StockCarReady implements Status {
    @Override
    public Status next() {
        return new StockCarOrderClosed();
    }

    @Override
    public Status cancel() {
        throw new RuntimeException("order cannot be canceld");
    }

    @Override
    public String getName() {
        return "ready";
    }
}
