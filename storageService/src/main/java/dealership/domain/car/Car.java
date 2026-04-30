package dealership.domain.car;

import dealership.exceptions.DomainValidationException;
import jakarta.persistence.*;
import lombok.Getter;
import dealership.domain.details.Body.Body;
import dealership.domain.details.Cost;
import dealership.domain.details.Transmission.Transmission;
import dealership.domain.details.color.CarPaint;
import dealership.domain.details.drivelayout.DriveLayout;
import dealership.domain.details.fuel.Fuel;

import java.time.Instant;
import java.util.UUID;

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
        if (!body.isCompatibleWith(model)) throw new DomainValidationException(
            "body is not compatible with model"
        );
        if (
            !carPaint.isCompatibleWith(model)
        ) throw new DomainValidationException(
            "color is not compatible with model"
        );
        if (!fuel.isCompatibleWith(model)) throw new DomainValidationException(
            "fuel is not compatible with model"
        );
        if (
            !transmission.isCompatibleWith(model)
        ) throw new DomainValidationException(
            "transmission is not compatible with model"
        );
        if (
            !drivelayout.isCompatibleWith(model)
        ) throw new DomainValidationException(
            "drivelayout is not compatible with model"
        );
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
        if (!body.isCompatibleWith(model)) throw new DomainValidationException(
            "body is not compatible with model"
        );
        if (
            !carPaint.isCompatibleWith(model)
        ) throw new DomainValidationException(
            "color is not compatible with model"
        );
        if (!fuel.isCompatibleWith(model)) throw new DomainValidationException(
            "fuel is not compatible with model"
        );
        if (
            !transmission.isCompatibleWith(model)
        ) throw new DomainValidationException(
            "transmission is not compatible with model"
        );
        if (
            !drivelayout.isCompatibleWith(model)
        ) throw new DomainValidationException(
            "drivelayout is not compatible with model"
        );
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
            drivelayout.Cost()
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
