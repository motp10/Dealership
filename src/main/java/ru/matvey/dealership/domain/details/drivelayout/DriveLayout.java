package ru.matvey.dealership.domain.details.drivelayout;

import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.IDetail;
import ru.matvey.dealership.domain.details.IDetailType;
import java.util.Set;

public class DriveLayout implements IDetail {
    private final DriveLayoutType type;
    private final Cost cost;
    private final Set<String> compatibleModels;

    public DriveLayout(DriveLayoutType type, Cost cost, Set<String> compatibleModels) {
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