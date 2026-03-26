package ru.matvey.dealership.domain.details.SteeringWheel;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.Set;
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
        Cost cost,
        Set<String> compatibleModels
    ) {
        super(UUID.randomUUID(), type.Value(), cost.value(), compatibleModels);
    }

    public SteeringWheel(
        UUID id,
        SteeringWheelType type,
        Cost cost,
        Set<String> compatibleModels
    ) {
        super(id, type.Value(), cost.value(), compatibleModels);
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
