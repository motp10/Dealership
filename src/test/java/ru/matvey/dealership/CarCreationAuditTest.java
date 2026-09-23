package ru.matvey.dealership;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.matvey.dealership.abstraction.CarsRepository;
import ru.matvey.dealership.abstraction.DetailsRepository;
import ru.matvey.dealership.application.CarServiceImpl;
import ru.matvey.dealership.contracts.operations.CarComplictation;
import ru.matvey.dealership.contracts.services.CarService;
import ru.matvey.dealership.domain.car.Car;
import ru.matvey.dealership.domain.details.Body.Body;
import ru.matvey.dealership.domain.details.Body.BodyType;
import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.Transmission.Transmission;
import ru.matvey.dealership.domain.details.Transmission.TransmissionType;
import ru.matvey.dealership.domain.details.color.CarPaint;
import ru.matvey.dealership.domain.details.color.PaintType;
import ru.matvey.dealership.domain.details.drivelayout.DriveLayout;
import ru.matvey.dealership.domain.details.drivelayout.DriveLayoutType;
import ru.matvey.dealership.domain.details.fuel.Fuel;
import ru.matvey.dealership.domain.details.fuel.FuelType;
import ru.matvey.dealership.presentation.controllers.CarsController;
import ru.matvey.dealership.presentation.models.CarConfigurationRequest;

import java.util.List;
import ru.matvey.dealership.domain.exceptions.DomainValidationException;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/** Regression tests for car creation. */
class CarCreationAuditTest {
    @Test
    void controllerPassesRequestedBasePrice() {
        var service = mock(CarService.class);
        new CarsController(service).addCar(new CarConfigurationRequest(
                "BMW", "M5", "Sedan", "Black", "Petrol", "Automatic", "AWD", "Alloy", null, 123456.0));
        var argument = ArgumentCaptor.forClass(CarComplictation.class);
        verify(service).addCar(argument.capture());
        assertThat(argument.getValue().maxPrice()).isEqualTo(123456.0);
        assertThat(argument.getValue().color()).isEqualTo("Black");
        assertThat(argument.getValue().fuelType()).isEqualTo("Petrol");
    }

    @Test
    void missingDetailRejectsConfigurationBeforeInsert() {
        var details = mock(DetailsRepository.class);
        var cars = mock(CarsRepository.class);
        var service = new CarServiceImpl(details, cars);
        assertThatThrownBy(() -> service.addCar(configuration("BMW")))
                .isInstanceOf(DomainValidationException.class);
        verifyNoInteractions(cars);
    }

    @Test
    void serviceSelectsFirstMatchingDetail() {
        var details = mock(DetailsRepository.class);
        var cars = mock(CarsRepository.class);
        when(details.getDetails(new BodyType("Sedan"))).thenReturn(List.of(new Body(new BodyType("Sedan"), new Cost(50)), new Body(new BodyType("Sedan"), new Cost(50))));
        when(details.getDetails(new PaintType("Black"))).thenReturn(List.of(new CarPaint(new PaintType("Black"), new Cost(15))));
        when(details.getDetails(new FuelType("Petrol"))).thenReturn(List.of(new Fuel(new FuelType("Petrol"), new Cost(30))));
        when(details.getDetails(new TransmissionType("Automatic"))).thenReturn(List.of(new Transmission(new TransmissionType("Automatic"), new Cost(40))));
        when(details.getDetails(new DriveLayoutType("AWD"))).thenReturn(List.of(new DriveLayout(new DriveLayoutType("AWD"), new Cost(60))));

        var id = new CarServiceImpl(details, cars).addCar(configuration("BMW"));
        var argument = ArgumentCaptor.forClass(Car.class);
        verify(cars).add(argument.capture());
        assertThat(argument.getValue().getId()).isEqualTo(id);
        assertThat(argument.getValue().getBrand()).isEqualTo("BMW");
        assertThat(argument.getValue().getTotalPrice()).isEqualTo(195);
        verify(details, times(5)).getDetails(any());
        verifyNoMoreInteractions(details);
    }

    private CarComplictation configuration(String brand) {
        return new CarComplictation(0, brand, "M5", "Sedan", "Petrol", "Automatic", "AWD", "Black", null);
    }
}
