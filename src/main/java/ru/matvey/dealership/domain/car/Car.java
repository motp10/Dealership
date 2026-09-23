package ru.matvey.dealership.domain.car;

import jakarta.persistence.*;
import ru.matvey.dealership.domain.details.Wheel.Wheel;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import ru.matvey.dealership.domain.details.Body.Body;
import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.Transmission.Transmission;
import ru.matvey.dealership.domain.details.color.CarPaint;
import ru.matvey.dealership.domain.details.drivelayout.DriveLayout;
import ru.matvey.dealership.domain.details.fuel.Fuel;
import ru.matvey.dealership.domain.exceptions.DomainValidationException;

@Entity
@Table(name = "cars")
@Getter
public class Car {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "body_id")
    private Body body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id")
    private CarPaint carPaint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fuel_id")
    private Fuel fuel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transmission_id")
    private Transmission transmission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drive_layout_id")
    private DriveLayout drivelayout;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wheel_id")
    private Wheel wheel;

    public void setWheel(Wheel wheel) {
        if (wheel == null) {
            throw new DomainValidationException("wheel must not be null");
        }
        this.wheel = wheel;
    }

    @Column(name = "base_cost")
    private double baseCost;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "removed")
    private final boolean removed = false;

    protected Car() {}

    public Car(
        String brand,
        String model,
        Body body,
        CarPaint carPaint,
        Fuel fuel,
        Transmission transmission,
        DriveLayout drivelayout,
        Cost defaultCost
    ) {
        if (brand == null || brand.isBlank() || model == null || model.isBlank()) {
            throw new DomainValidationException("Brand and model must not be blank");
        }
        
        this.id = UUID.randomUUID();
        this.brand = brand;
        this.model = model;
        this.body = body;
        this.carPaint = carPaint;
        this.fuel = fuel;
        this.transmission = transmission;
        this.drivelayout = drivelayout;
        this.baseCost = defaultCost.value();
    }

    public Car(
        UUID id,
        String brand,
        String model,
        Body body,
        CarPaint carPaint,
        Fuel fuel,
        Transmission transmission,
        DriveLayout drivelayout,
        Cost defaultCost
    ) {
        if (brand == null || brand.isBlank() || model == null || model.isBlank()) {
            throw new DomainValidationException("Brand and model must not be blank");
        }

        this.id = id;
        this.brand = brand;
        this.model = model;
        this.body = body;
        this.carPaint = carPaint;
        this.fuel = fuel;
        this.transmission = transmission;
        this.drivelayout = drivelayout;
        this.baseCost = defaultCost.value();
    }

    public double getTotalPrice() {
        return (
            baseCost +
            body.Cost() +
            carPaint.Cost() +
            fuel.Cost() +
            transmission.Cost() +
            drivelayout.Cost() + (wheel == null ? 0 : wheel.Cost())
        );
    }

    public Cost getDefaultCost() {
        return new Cost(baseCost);
    }

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}
