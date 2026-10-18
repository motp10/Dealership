package dealership.domain.details.Spoiler;

import dealership.domain.details.Cost;
import dealership.domain.details.Detail;
import dealership.domain.details.DetailInfo;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Set;
import java.util.UUID;

@Entity
@DiscriminatorValue("SPOILER")
public class Spoiler extends Detail {
    protected Spoiler() {}

    public Spoiler(SpoilerInfo info, Cost cost, Set<String> compatibleModels) {
        super(UUID.randomUUID(), info.Value(), cost.value(), compatibleModels);
    }

    @Override
    public String Type() {
        return "Spoiler";
    }

    @Override
    public DetailInfo Info() {
        return new SpoilerInfo(getInfo());
    }
}