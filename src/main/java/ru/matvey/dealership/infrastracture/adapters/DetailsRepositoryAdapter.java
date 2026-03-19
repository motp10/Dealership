package ru.matvey.dealership.infrastracture.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.matvey.dealership.abstraction.DetailsRepository;
import ru.matvey.dealership.domain.details.Detail;
import ru.matvey.dealership.domain.details.DetailType;
import ru.matvey.dealership.infrastracture.jpa.DetailEntity;
import ru.matvey.dealership.infrastracture.jparepositories.JpaDetailRepository;
import ru.matvey.dealership.infrastracture.mappers.carMapper.DetailMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DetailsRepositoryAdapter implements DetailsRepository {

    private final JpaDetailRepository jpaDetailRepository;
    private final DetailMapper detailMapper;

    @Override
    public void add(Detail detail) {
        DetailEntity entity = detailMapper.toEntity(detail);
        jpaDetailRepository.save(entity);
    }

    @Override
    public void delete(UUID id) {
        jpaDetailRepository.deleteById(id);
    }

    @Override
    public List<Detail> getDetails() {
        return jpaDetailRepository.findAll().stream()
                .map(detailMapper::toDomain)
                .toList();
    }

    @Override
    public List<Detail> getDetails(DetailType type) {
        return jpaDetailRepository.findByType(type.Value().toUpperCase()).stream()
                .map(detailMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Detail> findById(UUID id) {
        return jpaDetailRepository.findById(id)
                .map(detailMapper::toDomain);
    }
}