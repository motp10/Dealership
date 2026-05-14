package dealership.infrastracture.adapters;

import dealership.abstraction.OutboxMessageRepository;
import dealership.domain.message.MessageStatus;
import dealership.domain.message.OutboxMessage;
import dealership.infrastracture.jparepositories.JpaOutboxMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OutboxMessageRepositoryAdapter implements OutboxMessageRepository {
    private final JpaOutboxMessageRepository jpaOutboxMessageRepository;
    @Override
    public List<OutboxMessage> getAll() {
        return jpaOutboxMessageRepository.findAll();
    }

    @Override
    public List<OutboxMessage> findByStatus(MessageStatus status) {
        return jpaOutboxMessageRepository.findAll().stream().filter(message -> {return message.getStatus().equals(status);}).toList();
    }

    @Override
    public void save(OutboxMessage message) {
        jpaOutboxMessageRepository.save(message);
    }
}
