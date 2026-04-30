package dealership.domain.details.Transmission;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import dealership.domain.details.Cost;
import dealership.domain.details.Detail;
import dealership.domain.details.DetailInfo;

import java.util.Set;
import java.util.UUID;

@Entity
@DiscriminatorValue("TRANSMISSION")
public class Transmission extends Detail {

    protected Transmission() {}

    public Transmission(
        TransmissionInfo type,
        Cost cost,
        Set<String> compatibleModels
    ) {
        super(UUID.randomUUID(), type.Value(), cost.value(), compatibleModels);
    }

    public Transmission(
        UUID id,
        TransmissionInfo type,
        Cost cost,
        Set<String> compatibleModels
    ) {
        super(id, type.Value(), cost.value(), compatibleModels);
    }

    @Override
    public String Type() {
        return "Transmission";
    }

    @Override
    public DetailInfo Info() {
        return new TransmissionInfo(getInfo());
    }
}
