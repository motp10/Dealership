package dealership.domain.orders.configurationOrder;

import jakarta.persistence.*;
import lombok.Getter;
import dealership.domain.orders.Order;
import dealership.domain.orders.Status;
import dealership.domain.orders.stockCarOrder.status.StockCarOrderCreated;
import lombok.Setter;


import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "complication_orders")
@Getter
public class ConfigurationOrder implements Order {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(name = "client_id")
    private UUID clientId;

    @Column(name = "manager_id")
    private UUID managerId;

    @Column(name = "car_id")
    @Setter
    private UUID carId;

    @Column(name = "status")
    private String statusName;

    @Transient
    private Status status;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "removed")
    private boolean removed = false;

    protected ConfigurationOrder() {}

    public ConfigurationOrder(UUID clientId, UUID carId, UUID managerId) {
        this.id = UUID.randomUUID();
        this.clientId = clientId;
        this.carId = carId;
        this.managerId = managerId;
        this.status = new StockCarOrderCreated();
        this.statusName = status.getName();
    }

    @PostLoad
    protected void onLoad() {
        this.status = new ConfigurationOrderStatusFactory().Create(statusName);
    }

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
        if (status != null) statusName = status.getName();
    }

    @Override
    public void nextStep() {
        status = status.next();
        statusName = status.getName();
    }

    @Override
    public void cancel() {
        status = status.cancel();
        statusName = status.getName();
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public UUID getClientId() {
        return clientId;
    }

    @Override
    public UUID getManagerId() {
        return managerId;
    }

    @Override
    public UUID getCarId() {
        return carId;
    }

    @Override
    public String getStatus() {
        return statusName;
    }

    @Override
    public void setManagerId(UUID newId) {
        managerId = newId;
    }
}
