package ru.matvey.dealership.infrastracture.adapters;

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

@Repository
@RequiredArgsConstructor
public class CarRepositoryAdapter implements CarsRepository {
    private final JpaCarRepository jpaCarRepository;

    @Override
    public void add(Car car) {
        jpaCarRepository.save(car);
    }

    @Override
    public void delete(UUID id) {
        jpaCarRepository.deleteById(id);
    }

    @Override
    public Optional<Car> findById(UUID id) {
        return jpaCarRepository.findById(id);
    }

    @Override
    public List<Car> getAll() {
        return jpaCarRepository.findAll();
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

        return jpaCarRepository.findAll(spec);
    }
}