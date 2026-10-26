package ru.matvey.dealership.presentation.controllers;

import lombok.extern.slf4j.Slf4j;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;
import ru.matvey.dealership.contracts.models.CarDto;
import ru.matvey.dealership.contracts.operations.CarComplictation;
import ru.matvey.dealership.contracts.services.CarService;
import ru.matvey.dealership.presentation.models.CarConfigurationRequest;

import java.util.List;
import java.util.UUID;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarsController {
    private final CarService adminService;
    private final MeterRegistry meterRegistry;

    @PostConstruct
    void registerBusinessMetrics() {
        meterRegistry.counter("dealership_cars_created_total");
        meterRegistry.counter("dealership_cars_deleted_total");
    }

    @PostMapping
    public UUID addCar(@Valid @RequestBody CarConfigurationRequest request) {
        CarComplictation complictation = new CarComplictation(
                request.baseCost() == null ? 0 : request.baseCost(),
                request.brand(),
                request.model(),
                request.bodyType(),
                request.fuel(),
                request.transmission(),
                request.driveLayout(),
                request.paint(),
                request.wheel());
        UUID carId = adminService.addCar(complictation);
        meterRegistry.counter("dealership_cars_created_total").increment();
        log.info("Car creation request completed",
            kv("component", "presentation-controller"),
            kv("controller_name", "CarsController"),
            kv("operation", "create"),
            kv("carId", carId),
            kv("brand", request.brand()),
            kv("model", request.model()));
        return carId;
    }
    @PatchMapping
    public void updateCar(@Valid @RequestBody CarConfigurationRequest request) {
        CarComplictation complictation = new CarComplictation(
                request.baseCost() == null ? 0 : request.baseCost(),
                request.brand(),
                request.model(),
                request.bodyType(),
                request.fuel(),
                request.transmission(),
                request.driveLayout(),
                request.paint(),
                request.wheel());
        adminService.updateCar(request.clientId(), complictation);
        log.info("Car update request completed",
            kv("component", "presentation-controller"),
            kv("controller_name", "CarsController"),
            kv("operation", "update"),
            kv("carId", request.clientId()),
            kv("model", request.model()));
    }
    @DeleteMapping
    public void deleteCar(@PathVariable UUID id) {
        adminService.deleteCar(id);
        meterRegistry.counter("dealership_cars_deleted_total").increment();
        log.info("Car deletion request completed",
            kv("component", "presentation-controller"),
            kv("controller_name", "CarsController"),
            kv("operation", "delete"),
            kv("carId", id));
    }
    @GetMapping("/{id}")
    public CarDto getCar(@PathVariable UUID id) {
        CarDto car = adminService.getCar(id);
        log.debug("Car retrieval request completed",
            kv("component", "presentation-controller"),
            kv("controller_name", "CarsController"),
            kv("operation", "getById"),
            kv("carId", id));
        return car;
    }
    @GetMapping
    public List<CarDto> getCars(@RequestBody CarConfigurationRequest request) {
        CarComplictation complictation = new CarComplictation(
                request.baseCost() == null ? 0 : request.baseCost(),
                request.brand(),
                request.model(),
                request.bodyType(),
                request.fuel(),
                request.transmission(),
                request.driveLayout(),
                request.paint(),
                request.wheel());
        boolean allCars = complictation == CarComplictation.empty();
        List<CarDto> cars = allCars
            ? adminService.getAllCars()
            : adminService.getCars(complictation);
        log.debug("Car list request completed",
            kv("component", "presentation-controller"),
            kv("controller_name", "CarsController"),
            kv("operation", allCars ? "getAll" : "getByCriteria"),
            kv("count", cars.size()));
        return cars;
    }
}