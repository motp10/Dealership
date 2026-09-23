package ru.matvey.dealership.domain.details.SteeringWheel;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.UUID;
import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.Detail;
import ru.matvey.dealership.domain.details.DetailType;

@Entity
@DiscriminatorValue("STEERINGWHEEL")
public class SteeringWheel extends Detail {

    protected SteeringWheel() {}

    public SteeringWheel(
        SteeringWheelType type,
        Cost cost
    ) {
        super(UUID.randomUUID(), type.Value(), cost.value());
    }

    public SteeringWheel(
        UUID id,
        SteeringWheelType type,
        Cost cost
    ) {
        super(id, type.Value(), cost.value());
    }

    @Override
    public String Type() {
        return "SteeringWheel";
    }

    @Override
    public DetailType Info() {
        return new SteeringWheelType(getValue());
    }
}
