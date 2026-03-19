package ru.matvey.dealership.domain.details;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface Detail {
    String Type();
    DetailType Info();
    double Cost();
    boolean isCompatibleWith(String model);
    Set<String> CompatibleModels();
    UUID getId();
}
