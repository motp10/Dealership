package ru.matvey.dealership.domain.details.color;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.UUID;
import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.Detail;
import ru.matvey.dealership.domain.details.DetailType;

@Entity
@DiscriminatorValue("COLOR")
public class CarPaint extends Detail {

    protected CarPaint() {}

    public CarPaint(PaintType type, Cost cost) {
        super(UUID.randomUUID(), type.Value(), cost.value());
    }

    public CarPaint(
        UUID id,
        PaintType type,
        Cost cost
    ) {
        super(id, type.Value(), cost.value());
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
