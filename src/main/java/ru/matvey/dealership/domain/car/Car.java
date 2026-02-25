package ru.matvey.dealership.domain.car;

import ru.matvey.dealership.domain.Exceptions.IncompatibleComponentException;
import ru.matvey.dealership.domain.details.Body.Body;
import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.IDetail;
import ru.matvey.dealership.domain.details.Transmission.Transmission;
import lombok.Builder;
import lombok.Getter;
import ru.matvey.dealership.domain.details.color.CarPaint;
import ru.matvey.dealership.domain.details.drivelayout.DriveLayout;
import ru.matvey.dealership.domain.details.fuel.Fuel;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.UUID;

@Builder
@Getter
public class Car {
    private final UUID id = UUID.randomUUID();
    private final String brand;
    private final String model;
    private final Body body;
    private final CarPaint carPaint;
    private final Fuel fuel;
    private final Transmission transmission;
    private final DriveLayout drivelayout;
    private final Map<Class<? extends IDetail>, IDetail> selectedComponents = new HashMap<>();
    private final Cost defaultCost;
    public Car(String brand, 
               String model, 
               Body bodyType,
               CarPaint color,
               Fuel fuelType,
               Transmission transmissionType,
               DriveLayout driveType,
               Cost defaultCost) {
        boolean allCompatible = true;
        this.brand = brand;
        this.model = model;
        this.body = bodyType;
        this.carPaint = color;
        this.fuel = fuelType;
        this.transmission = transmissionType;
        this.drivelayout = driveType;
        this.defaultCost = defaultCost;
    }

    public void addDetail(IDetail newDetail) {
        if (!newDetail.isCompatibleWith(this.model)) {
            throw new IncompatibleComponentException(
                    "Detail " + newDetail.getClass().getSimpleName() + " is incompatible with model " + this.model
            );
        }
        selectedComponents.put(newDetail.getClass(), newDetail);
    }

    public double getTotalPrice() {
        double totalSum = 0;
        for (var detail : selectedComponents.values()) {
            totalSum += detail.Cost();
        }
        return totalSum;
    }

    public Map<Class<? extends IDetail>, IDetail> getSelectedComponents() {
        return Collections.unmodifiableMap(selectedComponents);
    }
}