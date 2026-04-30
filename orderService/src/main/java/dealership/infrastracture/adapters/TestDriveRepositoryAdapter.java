package dealership.infrastracture.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import dealership.abstraction.TestDriveRepository;
import dealership.domain.orders.testDriveOrder.TestDriveOrder;
import dealership.infrastracture.jparepositories.JpaTestDriveRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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