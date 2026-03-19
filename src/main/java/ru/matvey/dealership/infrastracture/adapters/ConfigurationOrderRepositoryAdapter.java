package ru.matvey.dealership.infrastracture.adapters;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.matvey.dealership.abstraction.ComplictaionOrderRepository;
import ru.matvey.dealership.domain.orders.configurationOrder.ConfigurationOrder;
import ru.matvey.dealership.infrastracture.jpa.ComplictationOrderEntity;
import ru.matvey.dealership.infrastracture.jparepositories.JpaConfigurationOrderRepository;
import ru.matvey.dealership.infrastracture.mappers.ConfigurationOrderMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ConfigurationOrderRepositoryAdapter implements ComplictaionOrderRepository {

    private final JpaConfigurationOrderRepository jpaRepository;
    private final ConfigurationOrderMapper mapper;

    @Override
    public void add(ConfigurationOrder order) {
        ComplictationOrderEntity entity = mapper.toEntity(order);
        jpaRepository.save(entity);
    }

    @Override
    public void delete(UUID orderId) {
        jpaRepository.deleteById(orderId);
    }

    @Override
    public Optional<ConfigurationOrder> findById(UUID orderId) {
        return jpaRepository.findById(orderId)
                .map(mapper::toDomain);
    }

    @Override
    public List<ConfigurationOrder> getAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }
}