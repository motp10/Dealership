package ru.matvey.dealership.infrastracture.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.matvey.dealership.abstraction.UserRepository;
import ru.matvey.dealership.domain.users.User;
import ru.matvey.dealership.infrastracture.jpa.UserEntity;
import ru.matvey.dealership.infrastracture.jparepositories.JpaUserRepository;
import ru.matvey.dealership.infrastracture.mappers.UserMapper;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final UserMapper userMapper;

    @Override
    public void addUser(User user) {
        UserEntity entity = userMapper.toEntity(user);
        entity.setRole(user.getClass().getSimpleName().toUpperCase());
        jpaUserRepository.save(entity);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaUserRepository.findById(id)
                .map(userMapper::toDomain);
    }

    @Override
    public void delete(UUID id) {
        jpaUserRepository.deleteById(id);
    }

    @Override
    public List<User> getAll() {
        return jpaUserRepository.findAll().stream()
                .map(userMapper::toDomain)
                .toList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends User> List<T> findByRole(Class<T> userClass) {
        String roleStr = userClass.getSimpleName().toUpperCase();
        return jpaUserRepository.findByRole(roleStr).stream()
                .map(entity -> (T) userMapper.toDomain(entity))
                .toList();
    }

    @Override
    public UUID getRandomManager() {
        List<UserEntity> managers = jpaUserRepository.findByRole("MANAGER");
        if (managers.isEmpty()) {
            throw new IllegalStateException("No managers available in the database");
        }
        int randomIndex = new Random().nextInt(managers.size());
        return managers.get(randomIndex).getId();
    }
}