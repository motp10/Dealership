package ru.matvey.dealership.domain.details.color;

import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.IDetail;
import ru.matvey.dealership.domain.details.IDetailType;
import java.util.Set;

public class CarPaint implements IDetail {
    private final PaintType type;
    private final Cost cost;
    private final Set<String> compatibleModels;

    public CarPaint(String type, double cost, Set<String> compatibleModels) {
        this.type = new PaintType(type);
        this.cost = new Cost(cost);
        this.compatibleModels = compatibleModels;
    }

    public CarPaint(PaintType type, Cost cost, Set<String> compatibleModels) {
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
