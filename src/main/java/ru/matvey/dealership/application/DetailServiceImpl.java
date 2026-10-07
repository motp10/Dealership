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

@Slf4j
@Service
@RequiredArgsConstructor
public class DetailServiceImpl implements DetailService {
    private final DetailsRepository detailsRepository;

    @Override
    public UUID addDetail(DetailOperation.Request request) {
        Detail detail;
        try {
            DetailsFactory detailsFactory = new DetailsFactory();
            detail = detailsFactory.createDetail(request.detailType(), request.detailInfo(), request.price());
        } catch (RuntimeException exception) {
            log.error("Failed to create detail of type {}", request.detailType(), exception);
            throw exception;
        }
        detailsRepository.add(detail);
        log.info("Detail {} of type {} created successfully", detail.getId(), request.detailType());
        return detail.getId();
    }

    @Override
    public void updateDetail(UUID id, DetailOperation.Request updatedDetail) {
        Detail detail;
        try {
            DetailsFactory factory = new DetailsFactory();
            detail = factory.createDetail(updatedDetail.detailType(), updatedDetail.detailInfo(), updatedDetail.price());
        } catch (RuntimeException exception) {
            log.error("Failed to create replacement detail {} of type {}", id, updatedDetail.detailType(), exception);
            throw exception;
        }
        detailsRepository.delete(id);
        detailsRepository.add(detail);
        log.info("Detail {} replaced successfully with {}", id, detail.getId());
    }

    @Override
    public void deleteDetail(UUID id) {
        detailsRepository.delete(id);
        log.info("Detail {} deleted successfully", id);
    }

    @Override
    public DetailDto getDetail(UUID id) {
        var detail = detailsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Detail with id " + id + " not found"));

        DetailDto result = DetailDtoMapper.toDto(detail);
        log.info("Detail {} retrieved successfully", id);
        return result;
    }

    @Override
    public List<DetailDto> getAllDetails() {
        List<DetailDto> details = detailsRepository.getDetails().stream()
                .map(DetailDto::new)
                .toList();
        log.info("Retrieved all details: {} found", details.size());
        return details;
    }
}
