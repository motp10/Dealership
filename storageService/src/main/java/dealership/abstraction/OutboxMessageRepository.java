package dealership.abstraction;

import dealership.domain.message.MessageStatus;
import dealership.domain.message.OutboxMessage;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface OutboxMessageRepository {
    List<OutboxMessage> getAll();

    List<OutboxMessage> findByStatus(MessageStatus status);

    void save(OutboxMessage message);
}
