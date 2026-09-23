package ru.matvey.dealership.contracts.operations;

public interface DetailOperation {

    record Request(
            String detailType,
            String detailInfo,
                double price
    ) {}

    sealed interface Response {
        record Success() implements DetailOperation.Response {}
        record Failure() implements DetailOperation.Response {}
    }
}