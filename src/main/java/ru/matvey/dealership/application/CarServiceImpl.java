package ru.matvey.dealership.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.matvey.dealership.abstraction.CarsRepository;
import ru.matvey.dealership.abstraction.DetailsRepository;
import ru.matvey.dealership.contracts.mappers.CarDtoMapper;
import ru.matvey.dealership.contracts.models.CarDto;
import ru.matvey.dealership.contracts.operations.CarComplictation;
import ru.matvey.dealership.contracts.services.CarService;
import ru.matvey.dealership.domain.car.Car;
import ru.matvey.dealership.domain.details.Body.Body;
import ru.matvey.dealership.domain.details.Body.BodyType;
import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.Transmission.Transmission;
import ru.matvey.dealership.domain.details.Transmission.TransmissionType;
import ru.matvey.dealership.domain.details.Wheel.WheelType;
import ru.matvey.dealership.domain.details.color.CarPaint;
import ru.matvey.dealership.domain.details.color.PaintType;
import ru.matvey.dealership.domain.details.drivelayout.DriveLayout;
import ru.matvey.dealership.domain.details.drivelayout.DriveLayoutType;
import ru.matvey.dealership.domain.details.fuel.Fuel;
import ru.matvey.dealership.domain.details.fuel.FuelType;
import ru.matvey.dealership.domain.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final DetailsRepository detailsRepository;
    private final CarsRepository carsRepository;
    @Override
    public UUID addCar(CarComplictation complictation) {
        Body body = (Body) detailsRepository.getDetails(new BodyType(complictation.bodyType())).getFirst();
        CarPaint color = (CarPaint) detailsRepository.getDetails(new PaintType(complictation.color())).getFirst();
        Fuel fuel = (Fuel) detailsRepository.getDetails(new FuelType(complictation.fuelType())).getFirst();
        Transmission transmission = (Transmission) detailsRepository.getDetails(new TransmissionType(complictation.transmissionType())).getFirst();
        DriveLayout driveLayout = (DriveLayout) detailsRepository.getDetails(new DriveLayoutType(complictation.drivelayoutType())).getFirst();

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
    public void updateCar(UUID id, CarComplictation complictation) {
        carsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("no such car id: " + id));

        Body body = (Body) detailsRepository.getDetails(new BodyType(complictation.bodyType())).getFirst();
        CarPaint color = (CarPaint) detailsRepository.getDetails(new PaintType(complictation.color())).getFirst();
        Fuel fuel = (Fuel) detailsRepository.getDetails(new FuelType(complictation.fuelType())).getFirst();
        Transmission transmission = (Transmission) detailsRepository.getDetails(new TransmissionType(complictation.transmissionType())).getFirst();
        DriveLayout driveLayout = (DriveLayout) detailsRepository.getDetails(new DriveLayoutType(complictation.drivelayoutType())).getFirst();

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
    public void deleteCar(UUID id) {
        carsRepository.delete(id);
    }

    @Override
    public CarDto getCar(UUID id) {
        return carsRepository.findById(id).map(CarDtoMapper::toDto).orElseThrow(() -> new EntityNotFoundException("no such car id: " + id));
    }

    @Override
    public List<CarDto> getCars(CarComplictation filter) {
        List<Car> cars = carsRepository.findByCriteria(
                new Cost(filter.maxPrice()),
                filter.brand().isEmpty() ? null : filter.brand(),
                filter.model().isEmpty() ? null : filter.model(),
                filter.bodyType().isEmpty() ? null : new BodyType(filter.bodyType()),
                filter.fuelType().isEmpty() ? null : new FuelType(filter.fuelType()),
                filter.transmissionType().isEmpty() ? null : new TransmissionType(filter.transmissionType()),
                filter.drivelayoutType().isEmpty() ? null : new DriveLayoutType(filter.drivelayoutType()),
                filter.color().isEmpty() ? null : new PaintType(filter.color()),
                filter.wheel().isEmpty() ? null : new WheelType(filter.wheel())
        );
        return cars.stream().map(CarDtoMapper::toDto).toList();
    }

    @Override
    public List<CarDto> getAllCars() {
        return carsRepository.getAll().stream()
                .map(CarDtoMapper::toDto)
                .toList();
    }
}
