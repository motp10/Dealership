package ru.matvey.dealership.infrastracture.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.matvey.dealership.abstraction.UserRepository;
import ru.matvey.dealership.domain.users.User;
import ru.matvey.dealership.infrastracture.jparepositories.JpaUserRepository;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public void addUser(User user) {
        jpaUserRepository.save(user);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaUserRepository.findById(id);
    }

    @Override
    public void delete(UUID id) {
        jpaUserRepository.deleteById(id);
    }

    @Override
    public List<User> getAll() {
        return jpaUserRepository.findAll();
    }

    @Override
    public List<User> findByRole(String role) {
        return jpaUserRepository.findByRole(role);
    }

    @Override
    public UUID getRandomManager() {
        List<User> managers = jpaUserRepository.findByRole("MANAGER");
        if (managers.isEmpty()) {
            throw new IllegalStateException("No managers available in the database");
        }
        int randomIndex = new Random().nextInt(managers.size());
        return managers.get(randomIndex).getId();
    }
}