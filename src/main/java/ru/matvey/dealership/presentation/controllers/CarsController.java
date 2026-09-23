package ru.matvey.dealership.presentation.controllers;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.matvey.dealership.contracts.models.CarDto;
import ru.matvey.dealership.contracts.operations.CarComplictation;
import ru.matvey.dealership.contracts.services.CarService;
import ru.matvey.dealership.presentation.models.CarConfigurationRequest;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarsController {
    private final CarService adminService;

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
        return adminService.addCar(complictation);
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
    }
    @DeleteMapping
    public void deleteCar(@PathVariable UUID id) {
        adminService.deleteCar(id);
    }
    @GetMapping("/{id}")
    public CarDto getCar(@PathVariable UUID id) {
        return adminService.getCar(id);
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
        if (complictation == CarComplictation.empty()) return adminService.getAllCars();
        return adminService.getCars(complictation);
    }
}