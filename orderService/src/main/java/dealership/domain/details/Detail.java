package dealership.domain.details;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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

    @Column(name = "detail_info", nullable = false)
    private String info;

    @Column(name = "cost", nullable = false)
    private double cost;

    @ElementCollection
    @CollectionTable(
        name = "detail_compatibility",
        joinColumns = @JoinColumn(name = "detail_id")
    )
    @Column(name = "model_name")
    private Set<String> compatibleModels = new HashSet<>();

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
        double cost,
        Set<String> compatibleModels
    ) {
        this.id = id;
        this.info = value;
        this.cost = cost;
        this.compatibleModels = new HashSet<>(compatibleModels);
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

    public abstract DetailInfo Info();

    public abstract String Type();

    public double Cost() {
        return cost;
    }

    public boolean isCompatibleWith(String model) {
        return compatibleModels.contains(model);
    }

    public Set<String> CompatibleModels() {
        return new HashSet<>(compatibleModels);
    }
}
