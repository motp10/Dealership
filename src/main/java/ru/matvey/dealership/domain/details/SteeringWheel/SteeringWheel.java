package ru.matvey.dealership.domain.details.SteeringWheel;

import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.IDetail;
import ru.matvey.dealership.domain.details.IDetailType;
import ru.matvey.dealership.domain.details.Interior.InteriorType;

import java.util.Set;

public class SteeringWheel implements IDetail {
    private final SteeringWheelType type;
    private final Cost cost;
    private final Set<String> compatibleModels;

    public SteeringWheel(SteeringWheelType type, Cost cost, Set<String> compatibleModels) {
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
