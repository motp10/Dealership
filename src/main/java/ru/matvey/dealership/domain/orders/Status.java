package ru.matvey.dealership.domain.orders;

public interface Status {
    Status next();
    Status cancel();
    String getName();
}
