package ru.matvey.dealership.presentation.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.matvey.dealership.domain.exceptions.DomainValidationException;
import ru.matvey.dealership.domain.exceptions.EntityNotFoundException;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@RestControllerAdvice(assignableTypes = CarsController.class)
public class CarExceptionHandler {
    @ExceptionHandler({DomainValidationException.class, IllegalArgumentException.class})
    public ProblemDetail invalidConfiguration(RuntimeException exception) {
        log.warn("Car API request rejected",
            kv("component", "presentation-controller"),
            kv("controller_name", "CarsController"),
            kv("operation", "request"),
            kv("httpStatus", HttpStatus.BAD_REQUEST.value()),
            kv("errorType", exception.getClass().getSimpleName()));
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail notFound(EntityNotFoundException exception) {
        log.warn("Car API resource not found",
            kv("component", "presentation-controller"),
            kv("controller_name", "CarsController"),
            kv("operation", "request"),
            kv("httpStatus", HttpStatus.NOT_FOUND.value()),
            kv("errorType", exception.getClass().getSimpleName()));
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }
}
