package ru.matvey.dealership.infrastracture.jparepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.matvey.dealership.infrastracture.jpa.ComplictationOrderEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaConfigurationOrderRepository extends JpaRepository<ComplictationOrderEntity, UUID> {}