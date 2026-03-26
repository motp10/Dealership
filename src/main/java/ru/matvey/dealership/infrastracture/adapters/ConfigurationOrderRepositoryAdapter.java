package ru.matvey.dealership.infrastracture.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.matvey.dealership.abstraction.ComplictaionOrderRepository;
import ru.matvey.dealership.domain.orders.configurationOrder.ConfigurationOrder;
import ru.matvey.dealership.infrastracture.jparepositories.JpaConfigurationOrderRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ConfigurationOrderRepositoryAdapter implements ComplictaionOrderRepository {

    private final JpaConfigurationOrderRepository jpaRepository;

    @Override
    public void add(ConfigurationOrder order) {
        jpaRepository.save(order);
    }

    @Override
    public void delete(UUID orderId) {
        jpaRepository.deleteById(orderId);
    }

    @Override
    public Optional<ConfigurationOrder> findById(UUID orderId) {
        return jpaRepository.findById(orderId);
    }

    @Override
    public List<ConfigurationOrder> getAll() {
        return jpaRepository.findAll();
    }
}