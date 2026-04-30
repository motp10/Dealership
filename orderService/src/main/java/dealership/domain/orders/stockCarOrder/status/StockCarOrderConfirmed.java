package dealership.domain.orders.stockCarOrder.status;

import dealership.domain.orders.Status;

public class StockCarOrderConfirmed implements Status {
    @Override
    public Status next() {
        return new StockCarOrderWaitingPayment();
    }

    @Override
    public Status cancel() {
        return new StockCarOrderCanceled();
    }

    @Override
    public String getName() {
        return "";
    }
}
