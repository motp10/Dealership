package ru.matvey.dealership.domain.details.Transmission;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
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
        Cost cost
    ) {
        super(UUID.randomUUID(), type.Value(), cost.value());
    }

    public Transmission(
        UUID id,
        TransmissionType type,
        Cost cost
    ) {
        super(id, type.Value(), cost.value());
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
