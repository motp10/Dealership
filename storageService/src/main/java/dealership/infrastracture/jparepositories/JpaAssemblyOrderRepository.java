package dealership.infrastracture.jparepositories;

import dealership.domain.assemblyOrder.AssemblyOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaAssemblyOrderRepository extends JpaRepository<AssemblyOrder, UUID> {
    boolean existsBySourceOrderId(UUID sourceOrderId);
}
