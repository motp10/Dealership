package ru.matvey.dealership.infrastracture.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.matvey.dealership.abstraction.TestDriveRepository;
import ru.matvey.dealership.domain.orders.testDriveOrder.TestDriveOrder;
import ru.matvey.dealership.infrastracture.jparepositories.JpaTestDriveRepository;
import ru.matvey.dealership.infrastracture.mappers.TestDriveOrderMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TestDriveRepositoryAdapter implements TestDriveRepository {

    private final JpaTestDriveRepository jpaRepository;
    private final TestDriveOrderMapper mapper;

    @Override
    public void add(TestDriveOrder order) {
        jpaRepository.save(mapper.toEntity(order));
    }

    @Override
    public Optional<TestDriveOrder> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<TestDriveOrder> getAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }
}