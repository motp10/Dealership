package ru.matvey.dealership.domain.details.Transmission;

import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.IDetail;
import ru.matvey.dealership.domain.details.IDetailType;
import ru.matvey.dealership.domain.details.SteeringWheel.SteeringWheelType;

import java.util.Set;

public class Transmission implements IDetail {
    private final TransmissionType type;
    private final Cost cost;
    private final Set<String> compatibleModels;

    public Transmission(TransmissionType type, Cost cost, Set<String> compatibleModels) {
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