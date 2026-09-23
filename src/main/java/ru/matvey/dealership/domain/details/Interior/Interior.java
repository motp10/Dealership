package ru.matvey.dealership.domain.details.Interior;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.UUID;
import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.Detail;
import ru.matvey.dealership.domain.details.DetailType;

@Entity
@DiscriminatorValue("INTERIOR")
public class Interior extends Detail {

    protected Interior() {}

    public Interior(
        InteriorType type,
        Cost cost
    ) {
        super(UUID.randomUUID(), type.Value(), cost.value());
    }

    public Interior(
        UUID id,
        InteriorType type,
        Cost cost
    ) {
        super(id, type.Value(), cost.value());
    }

    @Override
    public String Type() {
        return "Interior";
    }

    @Override
    public DetailType Info() {
        return new InteriorType(getValue());
    }
}
