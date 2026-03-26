package ru.matvey.dealership.contracts.services;

import ru.matvey.dealership.contracts.models.DetailDto;
import ru.matvey.dealership.contracts.operations.DetailOperation;

import java.util.List;
import java.util.UUID;

public interface DetailService {
    UUID addDetail(DetailOperation.Request request);
    void updateDetail(UUID id, DetailOperation.Request request);
    void deleteDetail(UUID id);
    DetailDto getDetail(UUID id);
    List<DetailDto> getAllDetails();

}
