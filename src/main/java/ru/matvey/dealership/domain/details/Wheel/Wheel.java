package ru.matvey.dealership.domain.details.Wheel;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.UUID;
import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.Detail;
import ru.matvey.dealership.domain.details.DetailType;

@Entity
@DiscriminatorValue("WHEEL")
public class Wheel extends Detail {

    protected Wheel() {}

    public Wheel(WheelType type, Cost cost) {
        super(UUID.randomUUID(), type.Value(), cost.value());
    }

    public Wheel(
        UUID id,
        WheelType type,
        Cost cost
    ) {
        super(id, type.Value(), cost.value());
    }

    @Override
    public String Type() {
        return "Wheel";
    }

    @Override
    public DetailType Info() {
        return new WheelType(getValue());
    }
}
