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

@Slf4j
@Repository
@RequiredArgsConstructor
public class TestDriveRepositoryAdapter implements TestDriveRepository {

    private final JpaTestDriveRepository jpaRepository;

    @Override
    public void add(TestDriveOrder order) {
        jpaRepository.save(order);
    }

    @Override
    public Optional<TestDriveOrder> findById(UUID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<TestDriveOrder> getAll() {
        return jpaRepository.findAll();
    }
}