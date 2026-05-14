package dealership.infrastracture.jparepositories;

import dealership.domain.details.Detail;
import dealership.domain.message.OutboxMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaOutboxMessageRepository extends JpaRepository<OutboxMessage, UUID> {
}
