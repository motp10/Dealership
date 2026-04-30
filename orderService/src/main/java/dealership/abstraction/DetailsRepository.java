package dealership.abstraction;


import dealership.domain.details.Detail;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DetailsRepository {
    List<Detail> getDetails();
    void add(Detail detail);
    void delete(UUID id);
    List<Detail> getDetails(String type);
    Optional<Detail> findById(UUID id);
}
