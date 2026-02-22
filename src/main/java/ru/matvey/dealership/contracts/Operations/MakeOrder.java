package ru.matvey.dealership.contracts.Operations;

import java.util.UUID;

public interface MakeOrder {
    record Request(
            UUID carId,
            UUID userId
    ) {}

    sealed interface Response {
        record Success() implements MakeOrder.Response {}
        record Failure(String message) implements MakeOrder.Response {}
    }
}
