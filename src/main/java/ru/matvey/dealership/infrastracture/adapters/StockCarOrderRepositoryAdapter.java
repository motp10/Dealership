package ru.matvey.dealership.infrastracture.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.matvey.dealership.abstraction.StockCarOrderRepository;
import ru.matvey.dealership.domain.orders.stockCarOrder.OrderStockCar;
import ru.matvey.dealership.infrastracture.jparepositories.JpaStockCarOrderRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class StockCarOrderRepositoryAdapter implements StockCarOrderRepository {

    private final JpaStockCarOrderRepository jpaRepository;

    @Override
    public void addOrder(OrderStockCar order) {
        jpaRepository.save(order);
    }

    @Override
    public Optional<OrderStockCar> findById(UUID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<OrderStockCar> getAll() {
        return jpaRepository.findAll();
    }
}