package ru.matvey.dealership.presentation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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
    private final CarService carService;

    @PostMapping
    public UUID addCar(@RequestBody CarConfigurationRequest request) {
        CarComplictation complictation = new CarComplictation(
                0,
                request.brand(),
                request.model(),
                request.bodyType(),
                request.fuel(),
                request.transmission(),
                request.driveLayout(),
                request.paint(),
                request.wheel());
        return carService.addCar(complictation);
    }
    @PatchMapping
    public void updateCar(@RequestBody CarConfigurationRequest request) {
        CarComplictation complictation = new CarComplictation(
                0,
                request.brand(),
                request.model(),
                request.bodyType(),
                request.fuel(),
                request.transmission(),
                request.driveLayout(),
                request.paint(),
                request.wheel());
        carService.updateCar(request.carID(), complictation);
    }
    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable UUID id) {
        carService.deleteCar(id);
    }
    @GetMapping("/{id}")
    public CarDto getCar(@PathVariable UUID id, JwtAuthenticationToken auth) {
        UUID clientId = UUID.fromString(auth.getToken().getSubject());
        return carService.getCar(id, clientId);
    }

    @PostMapping("/search")
    public List<CarDto> getCars(@RequestBody(required = false) CarConfigurationRequest request) {
        if (request == null) {
            return carService.getAllCars();
        }
        CarComplictation complictation = new CarComplictation(
                0,
                request.brand(),
                request.model(),
                request.bodyType(),
                request.fuel(),
                request.transmission(),
                request.driveLayout(),
                request.paint(),
                request.wheel());
        return carService.getCars(complictation);
    }
}