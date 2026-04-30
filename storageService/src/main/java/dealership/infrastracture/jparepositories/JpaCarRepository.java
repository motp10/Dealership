package dealership.infrastracture.jparepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import dealership.domain.car.Car;

import java.util.UUID;

@Repository
public interface JpaCarRepository
    extends JpaRepository<Car, UUID>, JpaSpecificationExecutor<Car> {}
