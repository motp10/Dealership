package ru.matvey.dealership.infrastracture.jparepositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.matvey.dealership.domain.users.User;

@Repository
public interface JpaUserRepository extends JpaRepository<User, UUID> {
    List<User> findByRole(String role);
}
