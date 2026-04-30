package ru.matvey.dealership.infrastracture.jparepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.matvey.dealership.domain.orders.testDriveOrder.TestDriveOrder;

import java.util.UUID;

@Repository
public interface JpaTestDriveRepository extends JpaRepository<TestDriveOrder, UUID> {}