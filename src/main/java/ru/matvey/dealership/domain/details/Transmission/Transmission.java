package ru.matvey.dealership.domain.details.Transmission;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.Set;
import java.util.UUID;
import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.Detail;
import ru.matvey.dealership.domain.details.DetailType;

@Entity
@DiscriminatorValue("TRANSMISSION")
public class Transmission extends Detail {

    protected Transmission() {}

    public Transmission(
        TransmissionType type,
        Cost cost,
        Set<String> compatibleModels
    ) {
        super(UUID.randomUUID(), type.Value(), cost.value(), compatibleModels);
    }

    public Transmission(
        UUID id,
        TransmissionType type,
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
    public DetailType Info() {
        return new TransmissionType(getValue());
    }
}
