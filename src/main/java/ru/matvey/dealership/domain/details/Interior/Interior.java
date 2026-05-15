package ru.matvey.dealership.domain.details.Interior;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.Set;
import java.util.UUID;
import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.Detail;
import ru.matvey.dealership.domain.details.DetailInfo;

@Entity
@DiscriminatorValue("INTERIOR")
public class Interior extends Detail {

    protected Interior() {}

    public Interior(
        InteriorInfo type,
        Cost cost,
        Set<String> compatibleModels
    ) {
        super(UUID.randomUUID(), type.Value(), cost.value(), compatibleModels);
    }

    public Interior(
        UUID id,
        InteriorInfo type,
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
    public DetailInfo Info() {
        return new InteriorInfo(getInfo());
    }
}
