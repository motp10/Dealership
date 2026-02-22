package ru.matvey.dealership.infrastracture;

import ru.matvey.dealership.Abstraction.DetailsRepository;
import ru.matvey.dealership.domain.details.IDetail;
import ru.matvey.dealership.domain.details.IDetailType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryDetailsRepository implements DetailsRepository {
    private List<IDetail> storege = new ArrayList<>();

    @Override
    public List<IDetail> getDetails() {
        return storege;
    }

    @Override
    public void addDetail(IDetail detail) {
        storege.add(detail);
    }

    @Override
    public void deleteDetail(UUID id) {
        IDetail detailToDelete = getDetail(id);
        storege.remove(detailToDelete);
    }

    @Override
    public List<IDetail> getDetails(IDetailType type) {
        return storege.stream()
                .filter(detail -> detail.Type().equals(type)).toList();
    }

    @Override
    public IDetail getDetail(UUID id) {
        return null;
    }
}
