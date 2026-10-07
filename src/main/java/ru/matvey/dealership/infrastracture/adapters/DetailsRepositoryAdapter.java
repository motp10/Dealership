package ru.matvey.dealership.infrastracture.adapters;

import lombok.extern.slf4j.Slf4j;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.matvey.dealership.abstraction.DetailsRepository;
import ru.matvey.dealership.domain.details.Detail;
import ru.matvey.dealership.domain.details.DetailType;
import ru.matvey.dealership.infrastracture.jparepositories.JpaDetailRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DetailsRepositoryAdapter implements DetailsRepository {

    private final JpaDetailRepository jpaDetailRepository;

    @Override
    public void add(Detail detail) {
        jpaDetailRepository.save(detail);
    }

    @Override
    public void delete(UUID id) {
        jpaDetailRepository.deleteById(id);
    }

    @Override
    public List<Detail> getDetails() {
        return jpaDetailRepository.findAll();
    }

    @Override
    public List<Detail> getDetails(DetailType type) {
        String discriminator = switch (type) {
            case ru.matvey.dealership.domain.details.Body.BodyType ignored -> "BODY";
            case ru.matvey.dealership.domain.details.color.PaintType ignored -> "COLOR";
            case ru.matvey.dealership.domain.details.fuel.FuelType ignored -> "FUEL";
            case ru.matvey.dealership.domain.details.Transmission.TransmissionType ignored -> "TRANSMISSION";
            case ru.matvey.dealership.domain.details.drivelayout.DriveLayoutType ignored -> "DRIVELAYOUT";
            case ru.matvey.dealership.domain.details.Wheel.WheelType ignored -> "WHEEL";
            case ru.matvey.dealership.domain.details.Interior.InteriorType ignored -> "INTERIOR";
            case ru.matvey.dealership.domain.details.SteeringWheel.SteeringWheelType ignored -> "STEERINGWHEEL";
            default -> throw new IllegalArgumentException("Unsupported detail type");
        };
        return jpaDetailRepository.findByTypeAndValue(discriminator, type.Value().trim());
    }

    @Override
    public Optional<Detail> findById(UUID id) {
        return jpaDetailRepository.findById(id);
    }
}