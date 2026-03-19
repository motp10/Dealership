package ru.matvey.dealership.domain.details.fuel;

import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.Detail;
import ru.matvey.dealership.domain.details.DetailType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Fuel implements Detail {
    private final FuelType type;
    private final Cost cost;
    private final Set<String> compatibleModels;
    private UUID id = UUID.randomUUID();

    public Fuel(FuelType type, Cost cost, Set<String> compatibleModels) {
        this.type = type;
        this.cost = cost;
        this.compatibleModels = compatibleModels;
    }

    public Fuel(UUID id, FuelType type, Cost cost, Set<String> compatibleModels) {
        this.id = id;
        this.type = type;
        this.cost = cost;
        this.compatibleModels = compatibleModels;
    }


    @Override
    public String Type() {
        return "Fuel";
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
