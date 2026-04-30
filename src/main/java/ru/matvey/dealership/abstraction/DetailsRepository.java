package ru.matvey.dealership.abstraction;

import ru.matvey.dealership.domain.details.Detail;
import ru.matvey.dealership.domain.details.DetailInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DetailsRepository {
    List<Detail> getDetails();
    void add(Detail detail);
    void delete(UUID id);
    List<Detail> getDetails(String type);
    Optional<Detail> findById(UUID id);
}
