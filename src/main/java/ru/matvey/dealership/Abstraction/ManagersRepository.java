package ru.matvey.dealership.Abstraction;

import ru.matvey.dealership.domain.users.DealeshipManagerUser;

import java.util.UUID;

public interface ManagersRepository {
    void AddManager(DealeshipManagerUser manager);
    void  DeleteManager(UUID managerId);
    UUID getRandomManager();
}
