package ru.matvey.dealership.domain.details.color;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.Set;
import java.util.UUID;
import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.Detail;
import ru.matvey.dealership.domain.details.DetailType;

@Entity
@DiscriminatorValue("COLOR")
public class CarPaint extends Detail {

    protected CarPaint() {}

    public CarPaint(PaintType type, Cost cost, Set<String> compatibleModels) {
        super(UUID.randomUUID(), type.Value(), cost.value(), compatibleModels);
    }

    public CarPaint(
        UUID id,
        PaintType type,
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
    public DetailType Info() {
        return new PaintType(getValue());
    }
}
