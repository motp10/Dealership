package ru.matvey.dealership.domain.details;

import java.util.UUID;

public interface IDetail {
    IDetailType Type();
    double Cost();
    boolean isCompatibleWith(String model);
    UUID getId();
}
