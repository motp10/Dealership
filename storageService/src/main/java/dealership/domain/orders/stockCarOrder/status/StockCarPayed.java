package dealership.domain.orders.stockCarOrder.status;

import dealership.domain.orders.Status;
import dealership.domain.orders.stockCarOrder.status.StockCarReady;

public class StockCarPayed implements Status {
    @Override
    public Status next() {
        return new StockCarReady();
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
