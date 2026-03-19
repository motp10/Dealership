package ru.matvey.dealership.infrastracture.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "details")
@Getter @Setter
public class DetailEntity extends BaseEntity {
    private String type;
    private String value;
    private Double cost;

    @ElementCollection
    @CollectionTable(name = "detail_compatibility", joinColumns = @JoinColumn(name = "detail_id"))
    @Column(name = "model_name")
    private Set<String> compatibleModels = new HashSet<>();
}