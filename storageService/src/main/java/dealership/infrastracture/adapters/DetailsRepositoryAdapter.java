package dealership.infrastracture.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import dealership.abstraction.DetailsRepository;
import dealership.domain.details.Detail;
import dealership.infrastracture.jparepositories.JpaDetailRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public List<Detail> getDetails(String type) {
        return jpaDetailRepository.findByType(type.toUpperCase());
    }

    @Override
    public Optional<Detail> findById(UUID id) {
        return jpaDetailRepository.findById(id);
    }
}