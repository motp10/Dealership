package ru.matvey.dealership.infrastracture.adapters;

import lombok.extern.slf4j.Slf4j;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.matvey.dealership.abstraction.ComplictaionOrderRepository;
import ru.matvey.dealership.domain.orders.configurationOrder.ConfigurationOrder;
import ru.matvey.dealership.infrastracture.jparepositories.JpaConfigurationOrderRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ConfigurationOrderRepositoryAdapter implements ComplictaionOrderRepository {

    private final JpaConfigurationOrderRepository jpaRepository;

    @Override
    public void add(ConfigurationOrder order) {
        try {
            jpaRepository.save(order);
        } catch (RuntimeException exception) {
            log.error("Failed to save configuration order",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "ConfigurationOrderRepositoryAdapter"),
                    kv("operation", "save"),
                    kv("orderId", order.getId()),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception);
            throw exception;
        }
        log.info("Configuration order saved",
                kv("component", "repository-adapter"),
                kv("adapter_name", "ConfigurationOrderRepositoryAdapter"),
                kv("operation", "save"),
                kv("orderId", order.getId()));
    }

    @Override
    public void delete(UUID orderId) {
        try {
            jpaRepository.deleteById(orderId);
        } catch (RuntimeException exception) {
            log.error("Failed to delete configuration order",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "ConfigurationOrderRepositoryAdapter"),
                    kv("operation", "delete"),
                    kv("orderId", orderId),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception);
            throw exception;
        }
        log.info("Configuration order deleted",
                kv("component", "repository-adapter"),
                kv("adapter_name", "ConfigurationOrderRepositoryAdapter"),
                kv("operation", "delete"),
                kv("orderId", orderId));
    }

    @Override
    public Optional<ConfigurationOrder> findById(UUID orderId) {
        try {
            Optional<ConfigurationOrder> order = jpaRepository.findById(orderId);
            log.debug("Configuration order lookup completed",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "ConfigurationOrderRepositoryAdapter"),
                    kv("operation", "findById"),
                    kv("orderId", orderId),
                    kv("found", order.isPresent()));
            return order;
        } catch (RuntimeException exception) {
            log.error("Failed to find configuration order",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "ConfigurationOrderRepositoryAdapter"),
                    kv("operation", "findById"),
                    kv("orderId", orderId),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception);
            throw exception;
        }
    }

    @Override
    public List<ConfigurationOrder> getAll() {
        try {
            List<ConfigurationOrder> orders = jpaRepository.findAll();
            log.debug("Configuration orders retrieved",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "ConfigurationOrderRepositoryAdapter"),
                    kv("operation", "findAll"),
                    kv("count", orders.size()));
            return orders;
        } catch (RuntimeException exception) {
            log.error("Failed to retrieve configuration orders",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "ConfigurationOrderRepositoryAdapter"),
                    kv("operation", "findAll"),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception);
            throw exception;
        }
    }
}