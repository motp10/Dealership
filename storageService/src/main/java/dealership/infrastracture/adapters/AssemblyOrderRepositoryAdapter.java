package dealership.infrastracture.adapters;

import dealership.abstraction.AssemblyOrderRepository;
import dealership.domain.assemblyOrder.AssemblyOrder;
import dealership.infrastracture.jparepositories.JpaAssemblyOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AssemblyOrderRepositoryAdapter implements AssemblyOrderRepository {
    private final JpaAssemblyOrderRepository orderRepository;

    @Override
    public void save(AssemblyOrder order) {
        orderRepository.save(order);
    }

    @Override
    public Optional<AssemblyOrder> findById(UUID id) {
        return orderRepository.findById(id);
    }

    @Override
    public boolean existsBySourceOrder(UUID sourceOrderId) {
        // Делегируем вызов сгенерированному методу Spring Data
        return orderRepository.existsBySourceOrderId(sourceOrderId);
    }
}
