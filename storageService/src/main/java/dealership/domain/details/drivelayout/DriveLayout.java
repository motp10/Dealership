package dealership.domain.details.drivelayout;

import dealership.domain.details.drivelayout.DriveLayoutInfo;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import dealership.domain.details.Cost;
import dealership.domain.details.Detail;
import dealership.domain.details.DetailInfo;

import java.util.Set;
import java.util.UUID;

@Entity
@DiscriminatorValue("DRIVELAYOUT")
public class DriveLayout extends Detail {

    protected DriveLayout() {}

    public DriveLayout(
        DriveLayoutInfo type,
        Cost cost,
        Set<String> compatibleModels
    ) {
        super(UUID.randomUUID(), type.Value(), cost.value(), compatibleModels);
    }

    public DriveLayout(
        UUID id,
        DriveLayoutInfo type,
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
    public DetailInfo Info() {
        return new DriveLayoutInfo(getInfo());
    }
}
