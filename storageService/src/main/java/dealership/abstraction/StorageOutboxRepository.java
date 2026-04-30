package dealership.abstraction;

import dealership.domain.message.MessageStatus;
import dealership.domain.message.OutboxMessage;

import java.util.List;

public interface StorageOutboxRepository {
    List<OutboxMessage> getAll();

    List<OutboxMessage> findByStatus(MessageStatus status);

    void save(OutboxMessage message);
}
