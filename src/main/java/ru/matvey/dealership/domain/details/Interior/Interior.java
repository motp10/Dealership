package ru.matvey.dealership.domain.details.Interior;

import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.IDetail;
import ru.matvey.dealership.domain.details.IDetailType;
import ru.matvey.dealership.domain.details.fuel.FuelType;

import java.util.Set;

public class Interior implements IDetail {
    private final InteriorType type;
    private final Cost cost;
    private final Set<String> compatibleModels;

    public Interior(InteriorType type, Cost cost, Set<String> compatibleModels) {
        this.type = type;
        this.cost = cost;
        this.compatibleModels = compatibleModels;
    }

    @Override
    public IDetailType Type() {
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
}