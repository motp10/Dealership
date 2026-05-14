package dealership.abstraction;

import dealership.domain.assemblyOrder.AssemblyOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AssemblyOrderRepository {

    boolean existsBySourceOrderId(UUID sourceOrderId);

    void save(AssemblyOrder order);
}