package ru.matvey.dealership.contracts.Operations;

import ru.matvey.dealership.contracts.Models.CarDto;

import java.util.List;
import java.util.UUID;

public interface CarId {
    record Request(
            UUID uuid
    ) {}

    sealed interface Response {
        record Success(List<CarDto> cars) implements CarId.Response {
        }

        record Failure(String message) implements CarId.Response {
        }
    }}