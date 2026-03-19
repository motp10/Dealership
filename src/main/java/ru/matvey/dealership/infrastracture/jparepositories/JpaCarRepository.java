package ru.matvey.dealership.infrastracture.jparepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.matvey.dealership.infrastracture.jpa.CarEntity;

import java.util.UUID;

@Repository
public interface JpaCarRepository extends JpaRepository<CarEntity, UUID>, JpaSpecificationExecutor<CarEntity> {}