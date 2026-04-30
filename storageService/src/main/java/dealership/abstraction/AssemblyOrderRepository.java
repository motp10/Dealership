package dealership.abstraction;

import dealership.domain.assemblyOrder.AssemblyOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AssemblyOrderRepository extends JpaRepository<AssemblyOrder, UUID> {

    boolean existsBySourceOrderId(UUID sourceOrderId);

    void save(AssemblyOrder order);

    boolean existsBySourceOrder(UUID sourceOrderId);
}