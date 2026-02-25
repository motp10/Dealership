package ru.matvey.dealership.domain.orders;

import java.util.UUID;

public interface Order {
    void nextStep();
    void cancel();
    UUID getId();
}
