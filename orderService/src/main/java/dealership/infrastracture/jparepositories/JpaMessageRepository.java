package dealership.infrastracture.jparepositories;

import dealership.domain.message.OutboxMessage;
import dealership.domain.orders.configurationOrder.ConfigurationOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaMessageRepository
        extends JpaRepository<OutboxMessage, UUID> {}
