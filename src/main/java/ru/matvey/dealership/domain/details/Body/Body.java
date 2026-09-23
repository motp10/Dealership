package ru.matvey.dealership.domain.details.Body;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.UUID;
import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.Detail;
import ru.matvey.dealership.domain.details.DetailType;

@Entity
@DiscriminatorValue("BODY")
public class Body extends Detail {

    protected Body() {}

    public Body(BodyType type, Cost cost) {
        super(UUID.randomUUID(), type.Value(), cost.value());
    }

    public Body(
        UUID id,
        BodyType type,
        Cost cost
    ) {
        super(id, type.Value(), cost.value());
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
