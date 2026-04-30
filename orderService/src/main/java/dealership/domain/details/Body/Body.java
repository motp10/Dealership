package dealership.domain.details.Body;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import dealership.domain.details.Cost;
import dealership.domain.details.Detail;
import dealership.domain.details.DetailInfo;

import java.util.Set;
import java.util.UUID;

@Entity
@DiscriminatorValue("BODY")
public class Body extends Detail {

    protected Body() {}

    public Body(BodyInfo type, Cost cost, Set<String> compatibleModels) {
        super(UUID.randomUUID(), type.Value(), cost.value(), compatibleModels);
    }

    public Body(
        UUID id,
        BodyInfo type,
        Cost cost,
        Set<String> compatibleModels
    ) {
        super(id, type.Value(), cost.value(), compatibleModels);
    }

    @Override
    public String Type() {
        return "Body";
    }

    @Override
    public DetailInfo Info() {
        return new BodyInfo(getInfo());
    }
}
