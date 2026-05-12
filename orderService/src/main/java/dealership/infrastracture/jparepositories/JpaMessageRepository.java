package dealership.infrastracture.jparepositories;

import dealership.domain.message.OutboxMessage;
import dealership.domain.orders.configurationOrder.ConfigurationOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaMessageRepository
        extends JpaRepository<OutboxMessage, UUID> {}
