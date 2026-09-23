package ru.matvey.dealership.domain.details;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;

@Entity
@Table(name = "details")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name = "type",
    discriminatorType = DiscriminatorType.STRING
)
@Getter
public abstract class Detail {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "cost", nullable = false)
    private double cost;

    @Column(name = "type", insertable = false, updatable = false)
    private String type;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "removed")
    private final boolean removed = false;

    protected Detail() {}

    protected Detail(
        UUID id,
        String value,
        double cost
    ) {
        this.id = id;
        this.value = value;
        this.cost = cost;
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

    public abstract DetailType Info();

    public abstract String Type();

    public double Cost() {
        return cost;
    }
}
