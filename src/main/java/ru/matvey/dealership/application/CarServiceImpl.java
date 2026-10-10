package ru.matvey.dealership.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.matvey.dealership.domain.details.Detail;
import ru.matvey.dealership.domain.details.DetailType;
import ru.matvey.dealership.domain.details.Wheel.Wheel;
import ru.matvey.dealership.domain.exceptions.DomainValidationException;
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

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final DetailsRepository detailsRepository;
    private final CarsRepository carsRepository;

    @Override
    public UUID addCar(CarComplictation complictation) {
        Car car;

        try {
            Body body = selectDetail(
                    new BodyType(complictation.bodyType()),
                    complictation.model(),
                    Body.class
            );

            CarPaint color = selectDetail(
                    new PaintType(complictation.color()),
                    complictation.model(),
                    CarPaint.class
            );

            Fuel fuel = selectDetail(
                    new FuelType(complictation.fuelType()),
                    complictation.model(),
                    Fuel.class
            );

            Transmission transmission = selectDetail(
                    new TransmissionType(complictation.transmissionType()),
                    complictation.model(),
                    Transmission.class
            );

            DriveLayout driveLayout = selectDetail(
                    new DriveLayoutType(complictation.drivelayoutType()),
                    complictation.model(),
                    DriveLayout.class
            );

            car = new Car(
                    complictation.brand(),
                    complictation.model(),
                    body,
                    color,
                    fuel,
                    transmission,
                    driveLayout,
                    new Cost(complictation.maxPrice())
            );

            if (complictation.wheel() != null) {
                car.setWheel(selectDetail(
                        new WheelType(complictation.wheel()),
                        complictation.model(),
                        Wheel.class
                ));
            }

        } catch (DomainValidationException exception) {
            log.warn(
                    "Car configuration validation failed",
                    kv("model", complictation.model()),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception
            );
            throw exception;
        } catch (RuntimeException exception) {
            log.error(
                    "Failed to build car",
                    kv("model", complictation.model()),
                    kv("brand", complictation.brand()),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception
            );
            throw exception;
        }

        try {
            carsRepository.add(car);
        } catch (RuntimeException exception) {
            log.error(
                    "Failed to save car",
                    kv("carId", car.getId()),
                    kv("model", car.getModel()),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception
            );
            throw exception;
        }

        log.info(
                "Car created successfully",
                kv("carId", car.getId()),
                kv("brand", car.getBrand()),
                kv("model", car.getModel())
        );

        return car.getId();
    }

    private <T extends Detail> T selectDetail(
            DetailType type,
            String model,
            Class<T> detailClass
    ) {
        return detailsRepository.getDetails(type).stream()
                .filter(detailClass::isInstance)
                .map(detailClass::cast)
                .findFirst()
                .orElseThrow(() -> new DomainValidationException(
                        "No " + detailClass.getSimpleName()
                                + " '" + type.Value() + "' found"
                ));
    }

    @Override
    public void updateCar(UUID id, CarComplictation complictation) {
        carsRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn(
                            "Car not found for update",
                            kv("carId", id)
                    );
                    return new EntityNotFoundException("no such car id: " + id);
                });

        Car updated;

        try {
            Body body = selectDetail(
                    new BodyType(complictation.bodyType()),
                    complictation.model(),
                    Body.class
            );

            CarPaint color = selectDetail(
                    new PaintType(complictation.color()),
                    complictation.model(),
                    CarPaint.class
            );

            Fuel fuel = selectDetail(
                    new FuelType(complictation.fuelType()),
                    complictation.model(),
                    Fuel.class
            );

            Transmission transmission = selectDetail(
                    new TransmissionType(complictation.transmissionType()),
                    complictation.model(),
                    Transmission.class
            );

            DriveLayout driveLayout = selectDetail(
                    new DriveLayoutType(complictation.drivelayoutType()),
                    complictation.model(),
                    DriveLayout.class
            );

            updated = new Car(
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

            if (complictation.wheel() != null) {
                updated.setWheel(selectDetail(
                        new WheelType(complictation.wheel()),
                        complictation.model(),
                        Wheel.class
                ));
            }

        } catch (DomainValidationException exception) {
            log.warn(
                    "Car update configuration validation failed",
                    kv("carId", id),
                    kv("model", complictation.model()),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception
            );
            throw exception;
        } catch (RuntimeException exception) {
            log.error(
                    "Failed to build updated car",
                    kv("carId", id),
                    kv("model", complictation.model()),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception
            );
            throw exception;
        }

        try {
            carsRepository.add(updated);
        } catch (RuntimeException exception) {
            log.error(
                    "Failed to save updated car",
                    kv("carId", id),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception
            );
            throw exception;
        }

        log.info(
                "Car updated successfully",
                kv("carId", id),
                kv("brand", updated.getBrand()),
                kv("model", updated.getModel())
        );
    }

    @Override
    public void deleteCar(UUID id) {
        try {
            carsRepository.delete(id);
        } catch (RuntimeException exception) {
            log.error(
                    "Failed to delete car",
                    kv("carId", id),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception
            );
            throw exception;
        }

        log.info(
                "Car deleted successfully",
                kv("carId", id)
        );
    }

    @Override
    public CarDto getCar(UUID id) {
        CarDto car = carsRepository.findById(id)
                .map(CarDtoMapper::toDto)
                .orElseThrow(() -> {
                    log.warn(
                            "Car not found",
                            kv("carId", id)
                    );
                    return new EntityNotFoundException("no such car id: " + id);
                });

        log.info(
                "Car retrieved successfully",
                kv("carId", id)
        );

        return car;
    }

    @Override
    public List<CarDto> getCars(CarComplictation filter) {
        List<Car> cars = carsRepository.findByCriteria(
                new Cost(filter.maxPrice()),
                filter.brand().isEmpty() ? null : filter.brand(),
                filter.model().isEmpty() ? null : filter.model(),
                filter.bodyType().isEmpty()
                        ? null : new BodyType(filter.bodyType()),
                filter.fuelType().isEmpty()
                        ? null : new FuelType(filter.fuelType()),
                filter.transmissionType().isEmpty()
                        ? null : new TransmissionType(filter.transmissionType()),
                filter.drivelayoutType().isEmpty()
                        ? null : new DriveLayoutType(filter.drivelayoutType()),
                filter.color().isEmpty()
                        ? null : new PaintType(filter.color()),
                filter.wheel().isEmpty()
                        ? null : new WheelType(filter.wheel())
        );

        List<CarDto> result = cars.stream()
                .map(CarDtoMapper::toDto)
                .toList();

        log.info(
                "Cars retrieved by criteria",
                kv("count", result.size())
        );

        return result;
    }

    @Override
    public List<CarDto> getAllCars() {
        List<CarDto> cars = carsRepository.getAll().stream()
                .map(CarDtoMapper::toDto)
                .toList();

        log.info(
                "All cars retrieved",
                kv("count", cars.size())
        );

        return cars;
    }
}