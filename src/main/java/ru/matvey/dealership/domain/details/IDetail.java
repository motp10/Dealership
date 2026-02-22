package ru.matvey.dealership.domain.details;

public interface IDetail {
    IDetailType Type();
    double Cost();
    boolean isCompatibleWith(String model);
}
