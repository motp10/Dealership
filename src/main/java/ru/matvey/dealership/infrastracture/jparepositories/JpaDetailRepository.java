package ru.matvey.dealership.infrastracture.jparepositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.matvey.dealership.domain.details.Detail;

@Repository
public interface JpaDetailRepository extends JpaRepository<Detail, UUID> {
    @Query(
        value = "SELECT * FROM details WHERE type = :type",
        nativeQuery = true
    )
    List<Detail> findByType(@Param("type") String type);
}
