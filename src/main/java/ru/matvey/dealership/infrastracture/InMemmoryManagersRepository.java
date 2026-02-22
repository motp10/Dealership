package ru.matvey.dealership.infrastracture;

import lombok.NonNull;
import ru.matvey.dealership.Abstraction.ManagersRepository;
import ru.matvey.dealership.domain.users.DealeshipManagerUser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemmoryManagersRepository implements ManagersRepository {
    private List<DealeshipManagerUser> storege = new ArrayList<>();
    @Override
    public void AddManager(DealeshipManagerUser manager) {
        if (manager == null) throw new RuntimeException();
        storege.add(manager);
    }

    @Override
    public void DeleteManager(UUID managerId) {
        DealeshipManagerUser managerToDelete = storege.stream().filter((x)->{return x.GetId() == managerId; }).toList().getFirst();
        storege.remove(managerToDelete);
    }

    @Override
    public UUID getRandomManager() {
        return storege.getFirst().GetId();
    }
}
