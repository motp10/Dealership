package ru.matvey.dealership.domain.car;

import lombok.NonNull;
import ru.matvey.dealership.domain.exceptions.DomainValidationException;
import ru.matvey.dealership.domain.exceptions.IncompatibleComponentException;
import ru.matvey.dealership.domain.details.Body.Body;
import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.Detail;
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
    private UUID id;
    private String brand;
    private String model;
    @NonNull private Body body;
    @NonNull private CarPaint carPaint;
    @NonNull private Fuel fuel;
    @NonNull private Transmission transmission;
    @NonNull private DriveLayout drivelayout;
    @NonNull private Cost defaultCost;
    public Car(@NonNull String brand,
               @NonNull String model,
               @NonNull Body bodyType,
               @NonNull CarPaint color,
               @NonNull Fuel fuelType,
               @NonNull Transmission transmissionType,
               @NonNull DriveLayout driveType,
               @NonNull Cost defaultCost) {
        if (!bodyType.isCompatibleWith(model)) throw new DomainValidationException("body is not compatible with model");
        if (!color.isCompatibleWith(model)) throw new DomainValidationException("color is not compatible with model");
        if (!fuelType.isCompatibleWith(model)) throw new DomainValidationException("fuel is not compatible with model");
        if (!transmissionType.isCompatibleWith(model)) throw new DomainValidationException("transmission is not compatible with model");
        if (!driveType.isCompatibleWith(model)) throw new DomainValidationException("drivelayout is not compatible with model");

        this.id = UUID.randomUUID();
        this.brand = brand;
        this.model = model;
        this.body = bodyType;
        this.carPaint = color;
        this.fuel = fuelType;
        this.transmission = transmissionType;
        this.drivelayout = driveType;
        this.defaultCost = defaultCost;
    }

    public Car(UUID id,
               @NonNull String brand,
               @NonNull String model,
               @NonNull Body bodyType,
               @NonNull CarPaint color,
               @NonNull Fuel fuelType,
               @NonNull Transmission transmissionType,
               @NonNull DriveLayout driveType,
               @NonNull Cost defaultCost) {

        if (!bodyType.isCompatibleWith(model)) throw new DomainValidationException("body is not compatible with model");
        if (!color.isCompatibleWith(model)) throw new DomainValidationException("color is not compatible with model");
        if (!fuelType.isCompatibleWith(model)) throw new DomainValidationException("fuel is not compatible with model");
        if (!transmissionType.isCompatibleWith(model)) throw new DomainValidationException("transmission is not compatible with model");
        if (!driveType.isCompatibleWith(model)) throw new DomainValidationException("drivelayout is not compatible with model");

        this.id = id;
        this.brand = brand;
        this.model = model;
        this.body = bodyType;
        this.carPaint = color;
        this.fuel = fuelType;
        this.transmission = transmissionType;
        this.drivelayout = driveType;
        this.defaultCost = defaultCost;
    }

    public double getTotalPrice() {
        double totalSum = 0;
        totalSum += body.Cost();
        totalSum += carPaint.Cost();
        totalSum += fuel.Cost();
        totalSum += transmission.Cost();
        totalSum += drivelayout.Cost();
        totalSum += defaultCost.value();
        return totalSum;
    }
}