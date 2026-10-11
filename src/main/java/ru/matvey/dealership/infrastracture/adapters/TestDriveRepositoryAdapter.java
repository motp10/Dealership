package ru.matvey.dealership.infrastracture.adapters;

import lombok.extern.slf4j.Slf4j;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.matvey.dealership.abstraction.TestDriveRepository;
import ru.matvey.dealership.domain.orders.testDriveOrder.TestDriveOrder;
import ru.matvey.dealership.infrastracture.jparepositories.JpaTestDriveRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TestDriveRepositoryAdapter implements TestDriveRepository {

    private final JpaTestDriveRepository jpaRepository;

    @Override
    public void add(TestDriveOrder order) {
        try {
            jpaRepository.save(order);
        } catch (RuntimeException exception) {
            log.error("Failed to save test drive order",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "TestDriveRepositoryAdapter"),
                    kv("operation", "save"),
                    kv("orderId", order.getId()),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception);
            throw exception;
        }
        log.info("Test drive order saved",
                kv("component", "repository-adapter"),
                kv("adapter_name", "TestDriveRepositoryAdapter"),
                kv("operation", "save"),
                kv("orderId", order.getId()));
    }

    @Override
    public Optional<TestDriveOrder> findById(UUID id) {
        try {
            Optional<TestDriveOrder> order = jpaRepository.findById(id);
            log.debug("Test drive order lookup completed",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "TestDriveRepositoryAdapter"),
                    kv("operation", "findById"),
                    kv("orderId", id),
                    kv("found", order.isPresent()));
            return order;
        } catch (RuntimeException exception) {
            log.error("Failed to find test drive order",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "TestDriveRepositoryAdapter"),
                    kv("operation", "findById"),
                    kv("orderId", id),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception);
            throw exception;
        }
    }

    @Override
    public List<TestDriveOrder> getAll() {
        try {
            List<TestDriveOrder> orders = jpaRepository.findAll();
            log.debug("Test drive orders retrieved",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "TestDriveRepositoryAdapter"),
                    kv("operation", "findAll"),
                    kv("count", orders.size()));
            return orders;
        } catch (RuntimeException exception) {
            log.error("Failed to retrieve test drive orders",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "TestDriveRepositoryAdapter"),
                    kv("operation", "findAll"),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception);
            throw exception;
        }
    }
}