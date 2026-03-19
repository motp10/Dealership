package ru.matvey.dealership.infrastracture.jpa;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cars")
@Getter @Setter
public class CarEntity extends BaseEntity {
    private String brand;
    private String model;

    @Column(name = "base_cost")
    private Double baseCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "body_id")
    private DetailEntity body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id")
    private DetailEntity color;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fuel_id")
    private DetailEntity fuel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transmission_id")
    private DetailEntity transmission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drive_layout_id")
    private DetailEntity driveLayout;
}