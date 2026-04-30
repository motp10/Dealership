package dealership.domain.details.Interior;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import dealership.domain.details.Cost;
import dealership.domain.details.Detail;
import dealership.domain.details.DetailInfo;

import java.util.Set;
import java.util.UUID;

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
