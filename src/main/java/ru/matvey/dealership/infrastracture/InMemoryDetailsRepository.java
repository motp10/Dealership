package ru.matvey.dealership.infrastracture;

import ru.matvey.dealership.abstraction.DetailsRepository;
import ru.matvey.dealership.domain.details.IDetail;
import ru.matvey.dealership.domain.details.IDetailType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryDetailsRepository implements DetailsRepository {
    private List<IDetail> storage = new ArrayList<>();

    @Override
    public List<IDetail> getDetails() {
        return new ArrayList<>(storage);
    }

    @Override
    public void addDetail(IDetail detail) {
        if (detail != null) {
            storage.add(detail);
        }
    }

    @Override
    public void deleteDetail(UUID id) {
        storage.removeIf(detail -> detail.getId().equals(id));
    }

    @Override
    public List<IDetail> getDetails(IDetailType type) {
        return storage.stream()
                .filter(detail -> detail.Type().equals(type))
                .toList();
    }

    @Override
    public IDetail getDetail(UUID id) {
        return storage.stream()
                .filter(detail -> detail.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
