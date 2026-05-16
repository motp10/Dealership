package ru.matvey.dealership.infrastracture.jparepositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.matvey.dealership.domain.orders.stockCarOrder.OrderStockCar;

@Repository
public interface JpaStockCarOrderRepository
    extends JpaRepository<OrderStockCar, UUID> {}
