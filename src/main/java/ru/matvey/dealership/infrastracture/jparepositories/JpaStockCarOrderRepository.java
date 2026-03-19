package ru.matvey.dealership.infrastracture.jparepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.matvey.dealership.infrastracture.jpa.StockCarOrderEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaStockCarOrderRepository extends JpaRepository<StockCarOrderEntity, UUID> {}