package ru.matvey.dealership.infrastracture.adapters;

import lombok.extern.slf4j.Slf4j;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.matvey.dealership.abstraction.StockCarOrderRepository;
import ru.matvey.dealership.domain.orders.stockCarOrder.OrderStockCar;
import ru.matvey.dealership.infrastracture.jparepositories.JpaStockCarOrderRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Repository
@RequiredArgsConstructor
public class StockCarOrderRepositoryAdapter implements StockCarOrderRepository {

    private final JpaStockCarOrderRepository jpaRepository;

    @Override
    public void addOrder(OrderStockCar order) {
        try {
            jpaRepository.save(order);
        } catch (RuntimeException exception) {
            log.error("Failed to save stock car order",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "StockCarOrderRepositoryAdapter"),
                    kv("operation", "save"),
                    kv("orderId", order.getId()),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception);
            throw exception;
        }
        log.info("Stock car order saved",
                kv("component", "repository-adapter"),
                kv("adapter_name", "StockCarOrderRepositoryAdapter"),
                kv("operation", "save"),
                kv("orderId", order.getId()));
    }

    @Override
    public Optional<OrderStockCar> findById(UUID id) {
        try {
            Optional<OrderStockCar> order = jpaRepository.findById(id);
            log.debug("Stock car order lookup completed",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "StockCarOrderRepositoryAdapter"),
                    kv("operation", "findById"),
                    kv("orderId", id),
                    kv("found", order.isPresent()));
            return order;
        } catch (RuntimeException exception) {
            log.error("Failed to find stock car order",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "StockCarOrderRepositoryAdapter"),
                    kv("operation", "findById"),
                    kv("orderId", id),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception);
            throw exception;
        }
    }

    @Override
    public List<OrderStockCar> getAll() {
        try {
            List<OrderStockCar> orders = jpaRepository.findAll();
            log.debug("Stock car orders retrieved",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "StockCarOrderRepositoryAdapter"),
                    kv("operation", "findAll"),
                    kv("count", orders.size()));
            return orders;
        } catch (RuntimeException exception) {
            log.error("Failed to retrieve stock car orders",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "StockCarOrderRepositoryAdapter"),
                    kv("operation", "findAll"),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception);
            throw exception;
        }
    }
}