package ru.matvey.dealership.infrastracture;

import ru.matvey.dealership.abstraction.ManagersRepository;
import ru.matvey.dealership.domain.users.DealeshipManagerUser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemmoryManagersRepository implements ManagersRepository {
    private List<DealeshipManagerUser> storage = new ArrayList<>();
    @Override
    public void AddManager(DealeshipManagerUser manager) {
        if (manager == null) throw new IllegalArgumentException("Manager cannot be null");
        storage.add(manager);
    }

    @Override
    public void DeleteManager(UUID managerId) {
        storage.removeIf(m -> m.GetId().equals(managerId));
    }

    @Override
    public UUID getRandomManager() {
        if (storage.isEmpty()) {
            throw new RuntimeException("Storage is empty");
        }
        return storage.getFirst().GetId();
    }
}
