
package ru.matvey.dealership.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.matvey.dealership.abstraction.DetailsRepository;
import ru.matvey.dealership.contracts.mappers.DetailDtoMapper;
import ru.matvey.dealership.contracts.models.DetailDto;
import ru.matvey.dealership.contracts.operations.DetailOperation;
import ru.matvey.dealership.contracts.services.DetailService;
import ru.matvey.dealership.domain.details.Detail;
import ru.matvey.dealership.domain.details.DetailsFactory;
import ru.matvey.dealership.domain.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.UUID;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Service
@RequiredArgsConstructor
public class DetailServiceImpl implements DetailService {

    private final DetailsRepository detailsRepository;

    @Override
    public UUID addDetail(DetailOperation.Request request) {
        Detail detail;

        try {
            DetailsFactory factory = new DetailsFactory();
            detail = factory.createDetail(
                    request.detailType(),
                    request.detailInfo(),
                    request.price()
            );
        } catch (RuntimeException exception) {
            log.warn(
                    "Failed to construct detail",
                    kv("detailType", request.detailType()),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception
            );
            throw exception;
        }

        try {
            detailsRepository.add(detail);
        } catch (RuntimeException exception) {
            log.error(
                    "Failed to persist detail",
                    kv("detailType", request.detailType()),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception
            );
            throw exception;
        }

        log.info(
                "Detail created successfully",
                kv("detailId", detail.getId()),
                kv("detailType", request.detailType())
        );

        return detail.getId();
    }

    @Override
    public void updateDetail(UUID id, DetailOperation.Request updatedDetail) {
        Detail replacement;

        try {
            DetailsFactory factory = new DetailsFactory();
            replacement = factory.createDetail(
                    updatedDetail.detailType(),
                    updatedDetail.detailInfo(),
                    updatedDetail.price()
            );
        } catch (RuntimeException exception) {
            log.warn(
                    "Failed to construct replacement detail",
                    kv("detailId", id),
                    kv("detailType", updatedDetail.detailType()),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception
            );
            throw exception;
        }

        try {
            detailsRepository.delete(id);
            detailsRepository.add(replacement);
        } catch (RuntimeException exception) {
            log.error(
                    "Failed to replace detail",
                    kv("detailId", id),
                    kv("replacementDetailId", replacement.getId()),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception
            );
            throw exception;
        }

        log.info(
                "Detail replaced successfully",
                kv("previousDetailId", id),
                kv("replacementDetailId", replacement.getId()),
                kv("detailType", updatedDetail.detailType())
        );
    }

    @Override
    public void deleteDetail(UUID id) {
        try {
            detailsRepository.delete(id);
        } catch (RuntimeException exception) {
            log.error(
                    "Failed to delete detail",
                    kv("detailId", id),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception
            );
            throw exception;
        }

        log.info(
                "Detail deleted successfully",
                kv("detailId", id)
        );
    }

    @Override
    public DetailDto getDetail(UUID id) {
        Detail detail = detailsRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn(
                            "Detail not found",
                            kv("detailId", id)
                    );
                    return new EntityNotFoundException(
                            "Detail with id " + id + " not found"
                    );
                });

        DetailDto result = DetailDtoMapper.toDto(detail);

        log.info(
                "Detail retrieved successfully",
                kv("detailId", id)
        );

        return result;
    }

    @Override
    public List<DetailDto> getAllDetails() {
        List<DetailDto> details = detailsRepository.getDetails()
                .stream()
                .map(DetailDto::new)
                .toList();

        log.info(
                "Retrieved all details",
                kv("count", details.size())
        );

        return details;
    }
}
