package dealership.domain.orders.stockCarOrder.status;

import dealership.domain.orders.Status;

public class StockCarOrderWaitingPayment implements Status {
    @Override
    public Status next() {
        return new StockCarPayed();
    }

    @Override
    public Status cancel() {
        return new StockCarOrderCanceled();
    }

    @Override
    public String getName() {
        return "waiting payment";
    }
}
