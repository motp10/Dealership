package ru.matvey.dealership.domain.details.Wheel;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.Set;
import java.util.UUID;
import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.Detail;
import ru.matvey.dealership.domain.details.DetailType;

@Entity
@DiscriminatorValue("WHEEL")
public class Wheel extends Detail {

    protected Wheel() {}

    public Wheel(WheelType type, Cost cost, Set<String> compatibleModels) {
        super(UUID.randomUUID(), type.Value(), cost.value(), compatibleModels);
    }

    public Wheel(
        UUID id,
        WheelType type,
        Cost cost,
        Set<String> compatibleModels
    ) {
        super(id, type.Value(), cost.value(), compatibleModels);
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
