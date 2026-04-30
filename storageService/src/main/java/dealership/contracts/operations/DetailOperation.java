package dealership.contracts.operations;

import java.util.Set;

public interface DetailOperation {

    record Request(
            String detailType,
            String detailInfo,
            double price,
            Set<String> suitableCars
    ) {}

    sealed interface Response {
        record Success() implements Response {}
        record Failure() implements Response {}
    }
}