package ru.matvey.dealership.domain.details.drivelayout;

import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.Detail;
import ru.matvey.dealership.domain.details.DetailType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class DriveLayout implements Detail {
    private final DriveLayoutType type;
    private final Cost cost;
    private final Set<String> compatibleModels;
    private UUID id = UUID.randomUUID();
    public DriveLayout(DriveLayoutType type, Cost cost, Set<String> compatibleModels) {
        this.type = type;
        this.cost = cost;
        this.compatibleModels = compatibleModels;
    }

    public DriveLayout(UUID id, DriveLayoutType type, Cost cost, Set<String> compatibleModels) {
        this.id = id;
        this.type = type;
        this.cost = cost;
        this.compatibleModels = compatibleModels;
    }

    @Override
    public String Type() {
        return "DriveLayout";
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