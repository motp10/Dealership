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

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DetailsRepositoryAdapter implements DetailsRepository {

    private final JpaDetailRepository jpaDetailRepository;

    @Override
    public void add(Detail detail) {
        try {
            jpaDetailRepository.save(detail);
        } catch (RuntimeException exception) {
            log.error("Failed to save detail",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "DetailsRepositoryAdapter"),
                    kv("operation", "save"),
                    kv("detailId", detail.getId()),
                    kv("detailType", detail.getClass().getSimpleName()),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception);
            throw exception;
        }
        log.info("Detail saved",
                kv("component", "repository-adapter"),
                kv("adapter_name", "DetailsRepositoryAdapter"),
                kv("operation", "save"),
                kv("detailId", detail.getId()),
                kv("detailType", detail.getClass().getSimpleName()));
    }

    @Override
    public void delete(UUID id) {
        try {
            jpaDetailRepository.deleteById(id);
        } catch (RuntimeException exception) {
            log.error("Failed to delete detail",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "DetailsRepositoryAdapter"),
                    kv("operation", "delete"),
                    kv("detailId", id),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception);
            throw exception;
        }
        log.info("Detail deleted",
                kv("component", "repository-adapter"),
                kv("adapter_name", "DetailsRepositoryAdapter"),
                kv("operation", "delete"),
                kv("detailId", id));
    }

    @Override
    public List<Detail> getDetails() {
        try {
            List<Detail> details = jpaDetailRepository.findAll();
            log.debug("Details retrieved",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "DetailsRepositoryAdapter"),
                    kv("operation", "findAll"),
                    kv("count", details.size()));
            return details;
        } catch (RuntimeException exception) {
            log.error("Failed to retrieve details",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "DetailsRepositoryAdapter"),
                    kv("operation", "findAll"),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception);
            throw exception;
        }
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
        try {
            List<Detail> details = jpaDetailRepository.findByTypeAndValue(
                discriminator,
                type.Value().trim()
            );
            log.debug("Details retrieved by type and value",
                kv("component", "repository-adapter"),
                kv("adapter_name", "DetailsRepositoryAdapter"),
                kv("operation", "findByTypeAndValue"),
                kv("detailType", discriminator),
                kv("count", details.size()));
            return details;
        } catch (RuntimeException exception) {
            log.error("Failed to retrieve details by type and value",
                kv("component", "repository-adapter"),
                kv("adapter_name", "DetailsRepositoryAdapter"),
                kv("operation", "findByTypeAndValue"),
                kv("detailType", discriminator),
                kv("errorType", exception.getClass().getSimpleName()),
                exception);
            throw exception;
        }
    }

    @Override
    public Optional<Detail> findById(UUID id) {
        try {
            Optional<Detail> detail = jpaDetailRepository.findById(id);
            log.debug("Detail lookup completed",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "DetailsRepositoryAdapter"),
                    kv("operation", "findById"),
                    kv("detailId", id),
                    kv("found", detail.isPresent()));
            return detail;
        } catch (RuntimeException exception) {
            log.error("Failed to find detail",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "DetailsRepositoryAdapter"),
                    kv("operation", "findById"),
                    kv("detailId", id),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception);
            throw exception;
        }
    }
}