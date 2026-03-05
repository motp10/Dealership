package ru.matvey.dealership.domain.Exceptions;

public class DomainValidationException extends RuntimeException {
    public DomainValidationException(String message) {
        super(message);
    }
}