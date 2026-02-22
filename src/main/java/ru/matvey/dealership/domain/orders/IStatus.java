package ru.matvey.dealership.domain.orders;

public interface IStatus {
    IStatus next();

    IStatus cancel();
    String getName();
}
