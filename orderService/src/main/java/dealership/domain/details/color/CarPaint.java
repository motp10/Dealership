package dealership.domain.details.color;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import dealership.domain.details.Cost;
import dealership.domain.details.Detail;
import dealership.domain.details.DetailInfo;

import java.util.Set;
import java.util.UUID;

@Entity
@DiscriminatorValue("COLOR")
public class CarPaint extends Detail {

    protected CarPaint() {}

    public CarPaint(PaintInfo type, Cost cost, Set<String> compatibleModels) {
        super(UUID.randomUUID(), type.Value(), cost.value(), compatibleModels);
    }

    public CarPaint(
        UUID id,
        PaintInfo type,
        Cost cost,
        Set<String> compatibleModels
    ) {
        super(id, type.Value(), cost.value(), compatibleModels);
    }

    @Override
    public String Type() {
        return "Paint";
    }

    @Override
    public DetailInfo Info() {
        return new PaintInfo(getInfo());
    }
}
