package dealership.infrastracture.jparepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dealership.domain.orders.stockCarOrder.OrderStockCar;

import java.util.UUID;

@Repository
public interface JpaStockCarOrderRepository
    extends JpaRepository<OrderStockCar, UUID> {}
