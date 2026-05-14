package dealership.contracts.operations;

import java.util.UUID;

public interface MakeOrder {
    record Request(
            UUID carId,
            UUID userId
    ) {}

    sealed interface Response {
        record Success() implements Response {}
        record Failure(String message) implements Response {}
    }
}
