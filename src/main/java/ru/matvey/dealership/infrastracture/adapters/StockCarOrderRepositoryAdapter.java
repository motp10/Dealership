package ru.matvey.dealership.infrastracture.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.matvey.dealership.abstraction.StockCarOrderRepository;
import ru.matvey.dealership.domain.exceptions.EntityNotFoundException;
import ru.matvey.dealership.domain.orders.stockCarOrder.OrderStockCar;
import ru.matvey.dealership.infrastracture.jparepositories.JpaStockCarOrderRepository;
import ru.matvey.dealership.infrastracture.mappers.StockCarOrderMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class StockCarOrderRepositoryAdapter implements StockCarOrderRepository {

    private final JpaStockCarOrderRepository jpaRepository;
    private final StockCarOrderMapper mapper;

    @Override
    public void addOrder(OrderStockCar order) {
        jpaRepository.save(mapper.toEntity(order));
    }

    @Override
    public Optional<OrderStockCar> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<OrderStockCar> getAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }
}