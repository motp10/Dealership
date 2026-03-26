package ru.matvey.dealership.domain.details.Interior;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.Set;
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
        Cost cost,
        Set<String> compatibleModels
    ) {
        super(UUID.randomUUID(), type.Value(), cost.value(), compatibleModels);
    }

    public Interior(
        UUID id,
        InteriorType type,
        Cost cost,
        Set<String> compatibleModels
    ) {
        super(id, type.Value(), cost.value(), compatibleModels);
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
