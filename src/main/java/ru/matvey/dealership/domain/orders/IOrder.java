package ru.matvey.dealership.domain.orders;

import java.util.UUID;

public interface IOrder {
    void nextStep();
    void cancel();
    UUID getId();
}
