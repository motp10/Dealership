package ru.matvey.dealership.domain.details.fuel;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.UUID;
import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.Detail;
import ru.matvey.dealership.domain.details.DetailType;

@Entity
@DiscriminatorValue("FUEL")
public class Fuel extends Detail {

    protected Fuel() {}

    public Fuel(FuelType type, Cost cost) {
        super(UUID.randomUUID(), type.Value(), cost.value());
    }

    public Fuel(
        UUID id,
        FuelType type,
        Cost cost
    ) {
        super(id, type.Value(), cost.value());
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
