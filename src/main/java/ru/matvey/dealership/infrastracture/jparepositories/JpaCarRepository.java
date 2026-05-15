package ru.matvey.dealership.infrastracture.jparepositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.matvey.dealership.domain.car.Car;

@Repository
public interface JpaCarRepository
    extends JpaRepository<Car, UUID>, JpaSpecificationExecutor<Car> {}
