package ru.matvey.dealership.presentation.advice;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import ru.matvey.dealership.domain.exceptions.DomainValidationException;
import ru.matvey.dealership.domain.exceptions.EntityNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MeterRegistry meterRegistry;

    /**
     * Бизнес-валидация и некорректные аргументы → 400
     */
    @ExceptionHandler({DomainValidationException.class, IllegalArgumentException.class})
    public ProblemDetail handleValidation(RuntimeException exception,
                                          HttpServletRequest request,
                                          HandlerMethod handlerMethod) {
        recordError("validation", HttpStatus.BAD_REQUEST, exception, request, handlerMethod);
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    /**
     * Сущность не найдена → 404
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleNotFound(EntityNotFoundException exception,
                                        HttpServletRequest request,
                                        HandlerMethod handlerMethod) {
        recordError("not_found", HttpStatus.NOT_FOUND, exception, request, handlerMethod);
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    /**
     * Всё остальное → 500
     */
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleAll(Exception exception,
                                   HttpServletRequest request,
                                   HandlerMethod handlerMethod) {
        recordError("internal", HttpStatus.INTERNAL_SERVER_ERROR, exception, request, handlerMethod);
        log.error("Unhandled exception",
                kv("component", "exception-handler"),
                kv("controller_name", controllerName(handlerMethod)),
                kv("operation", operationName(handlerMethod)),
                kv("http_method", request.getMethod()),
                kv("path", request.getRequestURI()),
                kv("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR.value()),
                kv("errorType", exception.getClass().getSimpleName()),
                exception);
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal server error");
    }

    // ===== helpers =====

    private void recordError(String reason, HttpStatus status, Exception exception,
                             HttpServletRequest request, HandlerMethod handlerMethod) {
        String controller = controllerName(handlerMethod);
        String operation = operationName(handlerMethod);

        meterRegistry.counter("dealership_http_errors_total",
                "controller", controller,
                "operation", operation,
                "reason", reason,
                "errorType", exception.getClass().getSimpleName(),
                "status", String.valueOf(status.value())).increment();

        log.warn("API request rejected",
                kv("component", "exception-handler"),
                kv("controller_name", controller),
                kv("operation", operation),
                kv("http_method", request.getMethod()),
                kv("path", request.getRequestURI()),
                kv("httpStatus", status.value()),
                kv("reason", reason),
                kv("errorType", exception.getClass().getSimpleName()));
    }

    private String controllerName(HandlerMethod handlerMethod) {
        return handlerMethod != null
                ? handlerMethod.getBeanType().getSimpleName()
                : "unknown";
    }

    private String operationName(HandlerMethod handlerMethod) {
        return handlerMethod != null
                ? handlerMethod.getMethod().getName()
                : "unknown";
    }
}