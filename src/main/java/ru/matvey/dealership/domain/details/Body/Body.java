package ru.matvey.dealership.domain.details.Body;

import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.IDetail;
import ru.matvey.dealership.domain.details.IDetailType;

import java.math.BigDecimal;
import java.util.Set;

public class Body implements IDetail {
    private final BodyType type;
    private final Cost cost;
    private final Set<String> compatibleModels;


    public Body(String type, double cost, Set<String> compatibleModels) {
        this.type = new BodyType(type);
        this.cost = new Cost(cost);
        this.compatibleModels = compatibleModels;
    }

    public Body(BodyType type, Cost cost, Set<String> compatibleModels) {
        this.type = type;
        this.cost = cost;
        this.compatibleModels = compatibleModels;
    }

    @Override
    public IDetailType Type() {
        return null;
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