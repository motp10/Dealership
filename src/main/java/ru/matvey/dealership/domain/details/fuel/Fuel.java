package ru.matvey.dealership.domain.details.fuel;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.Set;
import java.util.UUID;
import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.Detail;
import ru.matvey.dealership.domain.details.DetailType;

@Entity
@DiscriminatorValue("FUEL")
public class Fuel extends Detail {

    protected Fuel() {}

    public Fuel(FuelType type, Cost cost, Set<String> compatibleModels) {
        super(UUID.randomUUID(), type.Value(), cost.value(), compatibleModels);
    }

    public Fuel(
        UUID id,
        FuelType type,
        Cost cost,
        Set<String> compatibleModels
    ) {
        super(id, type.Value(), cost.value(), compatibleModels);
    }

    @Override
    public String Type() {
        return "Fuel";
    }

    @Override
    public DetailType Info() {
        return new FuelType(getValue());
    }
}
