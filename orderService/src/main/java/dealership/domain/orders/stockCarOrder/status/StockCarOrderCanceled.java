package dealership.domain.orders.stockCarOrder.status;


import dealership.domain.orders.Status;

public class StockCarOrderCanceled implements Status {
    @Override
    public Status next() {
        return this;
    }

    @Override
    public Status cancel() {
        return this;
    }

    @Override
    public String getName() {
        return "canceled";
    }
}
