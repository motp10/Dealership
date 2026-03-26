package ru.matvey.dealership.domain.details.drivelayout;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.Set;
import java.util.UUID;
import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.Detail;
import ru.matvey.dealership.domain.details.DetailType;

@Entity
@DiscriminatorValue("DRIVELAYOUT")
public class DriveLayout extends Detail {

    protected DriveLayout() {}

    public DriveLayout(
        DriveLayoutType type,
        Cost cost,
        Set<String> compatibleModels
    ) {
        super(UUID.randomUUID(), type.Value(), cost.value(), compatibleModels);
    }

    public DriveLayout(
        UUID id,
        DriveLayoutType type,
        Cost cost,
        Set<String> compatibleModels
    ) {
        super(id, type.Value(), cost.value(), compatibleModels);
    }

    @Override
    public String Type() {
        return "DriveLayout";
    }

    @Override
    public DetailType Info() {
        return new DriveLayoutType(getValue());
    }
}
