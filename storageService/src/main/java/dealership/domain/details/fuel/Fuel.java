package dealership.domain.details.fuel;

import dealership.domain.details.fuel.FuelInfo;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import dealership.domain.details.Cost;
import dealership.domain.details.Detail;
import dealership.domain.details.DetailInfo;

import java.util.Set;
import java.util.UUID;

@Entity
@DiscriminatorValue("FUEL")
public class Fuel extends Detail {

    protected Fuel() {}

    public Fuel(FuelInfo type, Cost cost, Set<String> compatibleModels) {
        super(UUID.randomUUID(), type.Value(), cost.value(), compatibleModels);
    }

    public Fuel(
        UUID id,
        FuelInfo type,
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
    public DetailInfo Info() {
        return new FuelInfo(getInfo());
    }
}
