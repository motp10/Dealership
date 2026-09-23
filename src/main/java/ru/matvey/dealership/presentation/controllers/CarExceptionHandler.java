package ru.matvey.dealership.presentation.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.matvey.dealership.domain.exceptions.DomainValidationException;
import ru.matvey.dealership.domain.exceptions.EntityNotFoundException;

@RestControllerAdvice(assignableTypes = CarsController.class)
public class CarExceptionHandler {
    @ExceptionHandler({DomainValidationException.class, IllegalArgumentException.class})
    public ProblemDetail invalidConfiguration(RuntimeException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail notFound(EntityNotFoundException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }
}
