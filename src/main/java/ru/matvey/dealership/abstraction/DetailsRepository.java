package ru.matvey.dealership.abstraction;

import ru.matvey.dealership.domain.details.IDetail;
import ru.matvey.dealership.domain.details.IDetailType;

import java.util.List;
import java.util.UUID;

public interface DetailsRepository {
    List<IDetail> getDetails();
    void addDetail(IDetail detail);
    void deleteDetail(UUID id);
    List<IDetail> getDetails(IDetailType type);
    IDetail getDetail(UUID id);
}
