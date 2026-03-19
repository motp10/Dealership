package ru.matvey.dealership.infrastracture.jparepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.matvey.dealership.infrastracture.jpa.UserEntity;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, UUID> {
    List<UserEntity> findByRole(String role);
}