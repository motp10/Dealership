package ru.matvey.dealership.domain.details.Body;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.Set;
import java.util.UUID;
import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.Detail;
import ru.matvey.dealership.domain.details.DetailType;

@Entity
@DiscriminatorValue("BODY")
public class Body extends Detail {

    protected Body() {}

    public Body(BodyType type, Cost cost, Set<String> compatibleModels) {
        super(UUID.randomUUID(), type.Value(), cost.value(), compatibleModels);
    }

    public Body(
        UUID id,
        BodyType type,
        Cost cost,
        Set<String> compatibleModels
    ) {
        super(id, type.Value(), cost.value(), compatibleModels);
    }

    @Override
    public String Type() {
        return "Body";
    }

    @Override
    public DetailType Info() {
        return new BodyType(getValue());
    }
}
