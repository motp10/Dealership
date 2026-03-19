package ru.matvey.dealership.domain.details.Body;

import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.Detail;
import ru.matvey.dealership.domain.details.DetailType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Body implements Detail {
    private BodyType type;
    private Cost cost;
    private Set<String> compatibleModels;

    private UUID id = UUID.randomUUID();

    public Body(BodyType type, Cost cost, Set<String> compatibleModels) {
        this.type = type;
        this.cost = cost;
        this.compatibleModels = compatibleModels;
    }

    public Body(UUID id, BodyType type, Cost cost, Set<String> compatibleModels) {
        this.id = id;
        this.type = type;
        this.cost = cost;
        this.compatibleModels = compatibleModels;
    }

    @Override
    public String Type() {
        return "Body";
    }

    @Override
    public DetailType Info() {
        return type;
    }


    @Override
    public double Cost() {
        return cost.value();
    }

    @Override
    public boolean isCompatibleWith(String model) {
        return compatibleModels.contains(model);
    }

    @Override
    public Set<String> CompatibleModels() {
        return new HashSet<>(compatibleModels);
    }

    @Override
    public UUID getId() {
        return id;
    }
}