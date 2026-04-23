package ru.matvey.dealership.infrastracture.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import ru.matvey.dealership.abstraction.CarsRepository;
import ru.matvey.dealership.domain.car.Car;
import ru.matvey.dealership.domain.details.Body.BodyInfo;
import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.Transmission.TransmissionInfo;
import ru.matvey.dealership.domain.details.Wheel.WheelInfo;
import ru.matvey.dealership.domain.details.color.PaintInfo;
import ru.matvey.dealership.domain.details.drivelayout.DriveLayoutInfo;
import ru.matvey.dealership.domain.details.fuel.FuelInfo;
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
    public List<Car> findByCriteria(Cost maxPrice, String brand, String model, BodyInfo bodyType,
                                    FuelInfo fuelType, TransmissionInfo transmissionType,
                                    DriveLayoutInfo drivelayoutType, PaintInfo color, WheelInfo wheel) {

        Specification<Car> spec = Specification.where(null);

        if (maxPrice != null) spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("baseCost"), maxPrice.value()));
        if (brand != null) spec = spec.and((root, query, cb) -> cb.equal(root.get("brand"), brand));
        if (model != null) spec = spec.and((root, query, cb) -> cb.equal(root.get("model"), model));

        if (bodyType != null) spec = spec.and((root, query, cb) -> cb.equal(root.get("body").get("info"), bodyType.Value()));

        if (color != null) spec = spec.and((root, query, cb) -> cb.equal(root.get("carPaint").get("info"), color.Value()));

        if (fuelType != null) spec = spec.and((root, query, cb) -> cb.equal(root.get("fuel").get("info"), fuelType.Value()));

        if (transmissionType != null) spec = spec.and((root, query, cb) -> cb.equal(root.get("transmission").get("info"), transmissionType.Value()));

        if (drivelayoutType != null) spec = spec.and((root, query, cb) -> cb.equal(root.get("drivelayout").get("info"), drivelayoutType.Value()));

        return jpaCarRepository.findAll(spec);
    }
}