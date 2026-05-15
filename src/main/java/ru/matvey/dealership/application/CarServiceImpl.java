package ru.matvey.dealership.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.matvey.dealership.abstraction.CarsRepository;
import ru.matvey.dealership.abstraction.DetailsRepository;
import ru.matvey.dealership.contracts.mappers.CarDtoMapper;
import ru.matvey.dealership.contracts.models.CarDto;
import ru.matvey.dealership.contracts.operations.CarComplictation;
import ru.matvey.dealership.contracts.services.CarService;
import ru.matvey.dealership.domain.car.Car;
import ru.matvey.dealership.domain.details.Body.Body;
import ru.matvey.dealership.domain.details.Body.BodyInfo;
import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.Transmission.Transmission;
import ru.matvey.dealership.domain.details.Transmission.TransmissionInfo;
import ru.matvey.dealership.domain.details.Wheel.WheelInfo;
import ru.matvey.dealership.domain.details.color.CarPaint;
import ru.matvey.dealership.domain.details.color.PaintInfo;
import ru.matvey.dealership.domain.details.drivelayout.DriveLayout;
import ru.matvey.dealership.domain.details.drivelayout.DriveLayoutInfo;
import ru.matvey.dealership.domain.details.fuel.Fuel;
import ru.matvey.dealership.domain.details.fuel.FuelInfo;
import ru.matvey.dealership.domain.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final DetailsRepository detailsRepository;
    private final CarsRepository carsRepository;
    @Override
    @PreAuthorize("hasAnyRole('WAREHOUSEADMIN', 'ADMIN')")
    public UUID addCar(CarComplictation complictation) {
        Body body = (Body) detailsRepository.getDetails("BODY").stream()
                .filter(d -> d.getInfo().equals(complictation.bodyType()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Body not found: " + complictation.bodyType()));

        CarPaint color = (CarPaint) detailsRepository.getDetails("COLOR").stream()
                .filter(d -> d.getInfo().equals(complictation.color()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Body not found: " + complictation.color()));

        Fuel fuel = (Fuel) detailsRepository.getDetails("FUEL").stream()
                .filter(d -> d.getInfo().equals(complictation.fuelType()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Body not found: " + complictation.fuelType()));

        Transmission transmission = (Transmission) detailsRepository.getDetails("TRANSMISSION").stream()
                .filter(d -> d.getInfo().equals(complictation.transmissionType()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Body not found: " + complictation.transmissionType()));

        DriveLayout driveLayout = (DriveLayout) detailsRepository.getDetails("DRIVELAYOUT").stream()
                .filter(d -> d.getInfo().equals(complictation.drivelayoutType()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Body not found: " + complictation.drivelayoutType()));

        Car car = new Car(
                complictation.brand(),
                complictation.model(),
                body,
                color,
                fuel,
                transmission,
                driveLayout,
                new Cost(complictation.maxPrice())
        );
        carsRepository.add(car);
        return car.getId();
    }

    @Override
    @PreAuthorize("hasAnyRole('WAREHOUSEADMIN', 'ADMIN')")
    public void updateCar(UUID id, CarComplictation complictation) {
        carsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("no such car id: " + id));

        Body body = (Body) detailsRepository.getDetails("BODY").stream()
                .filter(d -> d.getInfo().equals(complictation.bodyType()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Body not found: " + complictation.bodyType()));

        CarPaint color = (CarPaint) detailsRepository.getDetails("COLOR").stream()
                .filter(d -> d.getInfo().equals(complictation.color()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Body not found: " + complictation.color()));

        Fuel fuel = (Fuel) detailsRepository.getDetails("FUEL").stream()
                .filter(d -> d.getInfo().equals(complictation.fuelType()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Body not found: " + complictation.fuelType()));

        Transmission transmission = (Transmission) detailsRepository.getDetails("TRANSMISSION").stream()
                .filter(d -> d.getInfo().equals(complictation.transmissionType()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Body not found: " + complictation.transmissionType()));

        DriveLayout driveLayout = (DriveLayout) detailsRepository.getDetails("DRIVELAYOUT").stream()
                .filter(d -> d.getInfo().equals(complictation.drivelayoutType()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Body not found: " + complictation.drivelayoutType()));

        Car updated = new Car(
                id,
                complictation.brand(),
                complictation.model(),
                body,
                color,
                fuel,
                transmission,
                driveLayout,
                new Cost(complictation.maxPrice())
        );
        carsRepository.delete(id);
        carsRepository.add(updated);
    }

    @Override
    @PreAuthorize("hasAnyRole('WAREHOUSEADMIN', 'ADMIN')")
    public void deleteCar(UUID id) {
        carsRepository.delete(id);
    }

    @Override
    @PreAuthorize("hasAnyRole('WAREHOUSEADMIN', 'ADMIN')")
    public CarDto getCar(UUID id, UUID clientId) {
        return carsRepository.findById(id).map(CarDtoMapper::toDto).orElseThrow(() -> new EntityNotFoundException("no such car id: " + id));
    }

    @Override
    @PreAuthorize("hasAnyRole('WAREHOUSEADMIN', 'ADMIN')")
    public List<CarDto> getCars(CarComplictation filter) {
        List<Car> cars = carsRepository.findByCriteria(
                new Cost(filter.maxPrice()),
                filter.brand().isEmpty() ? null : filter.brand(),
                filter.model().isEmpty() ? null : filter.model(),
                filter.bodyType().isEmpty() ? null : new BodyInfo(filter.bodyType()),
                filter.fuelType().isEmpty() ? null : new FuelInfo(filter.fuelType()),
                filter.transmissionType().isEmpty() ? null : new TransmissionInfo(filter.transmissionType()),
                filter.drivelayoutType().isEmpty() ? null : new DriveLayoutInfo(filter.drivelayoutType()),
                filter.color().isEmpty() ? null : new PaintInfo(filter.color()),
                filter.wheel().isEmpty() ? null : new WheelInfo(filter.wheel())
        );
        return cars.stream().map(CarDtoMapper::toDto).toList();
    }

    @Override
    @PreAuthorize("hasAnyRole('WAREHOUSEADMIN', 'ADMIN')")
    public List<CarDto> getAllCars() {
        return carsRepository.getAll().stream().map(CarDtoMapper::toDto).toList();
    }
}
