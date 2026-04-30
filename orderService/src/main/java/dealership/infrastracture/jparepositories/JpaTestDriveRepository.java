package dealership.infrastracture.jparepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dealership.domain.orders.testDriveOrder.TestDriveOrder;

import java.util.UUID;

@Repository
public interface JpaTestDriveRepository extends JpaRepository<TestDriveOrder, UUID> {}