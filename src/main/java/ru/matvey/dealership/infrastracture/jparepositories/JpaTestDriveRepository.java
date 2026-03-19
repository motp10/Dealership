package ru.matvey.dealership.infrastracture.jparepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.matvey.dealership.infrastracture.jpa.StockCarOrderEntity;
import ru.matvey.dealership.infrastracture.jpa.TestDriveOrderEntity;

import java.util.UUID;

@Repository
public interface JpaTestDriveRepository extends JpaRepository<TestDriveOrderEntity, UUID> {}