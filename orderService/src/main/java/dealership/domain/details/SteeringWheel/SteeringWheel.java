package dealership.domain.details.SteeringWheel;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import dealership.domain.details.Cost;
import dealership.domain.details.DetailInfo;
import dealership.domain.details.Detail;

import java.util.Set;
import java.util.UUID;

@Entity
@DiscriminatorValue("STEERINGWHEEL")
public class SteeringWheel extends Detail {

    protected SteeringWheel() {}

    public SteeringWheel(
        SteeringWheelInfo type,
        Cost cost,
        Set<String> compatibleModels
    ) {
        super(UUID.randomUUID(), type.Value(), cost.value(), compatibleModels);
    }

    public SteeringWheel(
        UUID id,
        SteeringWheelInfo type,
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
    public DetailInfo Info() {
        return new SteeringWheelInfo(getInfo());
    }
}
