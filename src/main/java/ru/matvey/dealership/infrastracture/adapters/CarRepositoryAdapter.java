package ru.matvey.dealership.infrastracture.adapters;

import lombok.extern.slf4j.Slf4j;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import ru.matvey.dealership.abstraction.CarsRepository;
import ru.matvey.dealership.domain.car.Car;
import ru.matvey.dealership.domain.details.Body.BodyType;
import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.Transmission.TransmissionType;
import ru.matvey.dealership.domain.details.Wheel.WheelType;
import ru.matvey.dealership.domain.details.color.PaintType;
import ru.matvey.dealership.domain.details.drivelayout.DriveLayoutType;
import ru.matvey.dealership.domain.details.fuel.FuelType;
import ru.matvey.dealership.infrastracture.jparepositories.JpaCarRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CarRepositoryAdapter implements CarsRepository {
    private final JpaCarRepository jpaCarRepository;

    @Override
    public void add(Car car) {
        try {
            jpaCarRepository.save(car);
        } catch (RuntimeException exception) {
            log.error("Failed to save car",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "CarRepositoryAdapter"),
                    kv("operation", "save"),
                    kv("carId", car.getId()),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception);
            throw exception;
        }
        log.info("Car saved",
                kv("component", "repository-adapter"),
                kv("adapter_name", "CarRepositoryAdapter"),
                kv("operation", "save"),
                kv("carId", car.getId()),
                kv("brand", car.getBrand()),
                kv("model", car.getModel()));
    }

    @Override
    public void delete(UUID id) {
        try {
            jpaCarRepository.deleteById(id);
        } catch (RuntimeException exception) {
            log.error("Failed to delete car",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "CarRepositoryAdapter"),
                    kv("operation", "delete"),
                    kv("carId", id),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception);
            throw exception;
        }
        log.info("Car deleted",
                kv("component", "repository-adapter"),
                kv("adapter_name", "CarRepositoryAdapter"),
                kv("operation", "delete"),
                kv("carId", id));
    }

    @Override
    public Optional<Car> findById(UUID id) {
        try {
            Optional<Car> car = jpaCarRepository.findById(id);
            log.debug("Car lookup completed",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "CarRepositoryAdapter"),
                    kv("operation", "findById"),
                    kv("carId", id),
                    kv("found", car.isPresent()));
            return car;
        } catch (RuntimeException exception) {
            log.error("Failed to find car",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "CarRepositoryAdapter"),
                    kv("operation", "findById"),
                    kv("carId", id),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception);
            throw exception;
        }
    }

    @Override
    public List<Car> getAll() {
        try {
            List<Car> cars = jpaCarRepository.findAll();
            log.debug("Cars retrieved",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "CarRepositoryAdapter"),
                    kv("operation", "findAll"),
                    kv("count", cars.size()));
            return cars;
        } catch (RuntimeException exception) {
            log.error("Failed to retrieve cars",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "CarRepositoryAdapter"),
                    kv("operation", "findAll"),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception);
            throw exception;
        }
    }

    @Override
    public List<Car> findByCriteria(Cost maxPrice, String brand, String model, BodyType bodyType,
                                    FuelType fuelType, TransmissionType transmissionType,
                                    DriveLayoutType drivelayoutType, PaintType color, WheelType wheel) {

        Specification<Car> spec = Specification.where((Specification<Car>) null);

        if (maxPrice != null) spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("baseCost"), maxPrice.value()));
        if (brand != null) spec = spec.and((root, query, cb) -> cb.equal(root.get("brand"), brand));
        if (model != null) spec = spec.and((root, query, cb) -> cb.equal(root.get("model"), model));
        if (bodyType != null) spec = spec.and((root, query, cb) -> cb.equal(root.join("body").get("value"), bodyType.Value()));
        if (color != null) spec = spec.and((root, query, cb) -> cb.equal(root.join("color").get("value"), color.Value()));
        if (fuelType != null) spec = spec.and((root, query, cb) -> cb.equal(root.get("fuel"), fuelType.value()));
        if (transmissionType != null) spec = spec.and((root, query, cb) -> cb.equal(root.get("fuel"), transmissionType.value()));

        try {
            List<Car> cars = jpaCarRepository.findAll(spec);
            log.debug("Cars retrieved by criteria",
                kv("component", "repository-adapter"),
                kv("adapter_name", "CarRepositoryAdapter"),
                kv("operation", "findByCriteria"),
                kv("brand", brand),
                kv("model", model),
                kv("count", cars.size()));
            return cars;
        } catch (RuntimeException exception) {
            log.error("Failed to retrieve cars by criteria",
                kv("component", "repository-adapter"),
                kv("adapter_name", "CarRepositoryAdapter"),
                kv("operation", "findByCriteria"),
                kv("brand", brand),
                kv("model", model),
                kv("errorType", exception.getClass().getSimpleName()),
                exception);
            throw exception;
        }
    }
}