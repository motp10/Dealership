package dealership.domain.orders.stockCarOrder.status;


import dealership.domain.orders.Status;
import dealership.domain.orders.stockCarOrder.status.StockCarOrderCanceled;
import dealership.domain.orders.stockCarOrder.status.StockCarOrderConfirmed;


public class StockCarOrderCreated implements Status {
    @Override
    public Status next() {
        return new StockCarOrderConfirmed();
    }

    @Override
    public Status cancel() {
        return new StockCarOrderCanceled();
    }

    @Override
    public String getName() {
        return "created";
    }
}