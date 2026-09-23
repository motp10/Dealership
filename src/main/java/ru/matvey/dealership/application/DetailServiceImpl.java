package ru.matvey.dealership.application;

import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
public class DetailServiceImpl implements DetailService {
    private final DetailsRepository detailsRepository;

    @Override
    public UUID addDetail(DetailOperation.Request request) {
        DetailsFactory detailsFactory = new DetailsFactory();
        Detail detail = detailsFactory.createDetail(request.detailType(), request.detailInfo(), request.price());
        detailsRepository.add(detail);
        return detail.getId();
    }

    @Override
    public void updateDetail(UUID id, DetailOperation.Request updatedDetail) {
        DetailsFactory factory = new DetailsFactory();
        Detail detail = factory.createDetail(updatedDetail.detailType(), updatedDetail.detailInfo(), updatedDetail.price());
        detailsRepository.delete(id);
        detailsRepository.add(detail);
    }

    @Override
    public void deleteDetail(UUID id) {
        detailsRepository.delete(id);
    }

    @Override
    public DetailDto getDetail(UUID id) {
        var detail = detailsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Detail with id " + id + " not found"));

        return DetailDtoMapper.toDto(detail);
    }

    @Override
    public List<DetailDto> getAllDetails() {
        return detailsRepository.getDetails().stream()
                .map(DetailDto::new)
                .toList();
    }
}
