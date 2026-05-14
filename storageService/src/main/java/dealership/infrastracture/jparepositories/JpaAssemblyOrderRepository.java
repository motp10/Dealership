package dealership.infrastracture.jparepositories;

import dealership.domain.assemblyOrder.AssemblyOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaAssemblyOrderRepository extends JpaRepository<AssemblyOrder, UUID> {
    boolean existsBySourceOrderId(UUID sourceOrderId);
}
