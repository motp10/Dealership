package dealership.infrastracture.adapters;

import dealership.abstraction.MessageRepository;
import dealership.domain.message.MessageStatus;
import dealership.domain.message.OutboxMessage;
import dealership.infrastracture.jparepositories.JpaMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OutboxMessageRepositoryAdapter implements MessageRepository {
    private final JpaMessageRepository jpaMessageRepository;

    @Override
    public List<OutboxMessage> getAll() {
        return jpaMessageRepository.findAll();
    }

    @Override
    public List<OutboxMessage> findByStatus(MessageStatus status) {
        return jpaMessageRepository.findAll().stream().filter(message -> {return message.getStatus().equals(status);}).toList();
    }

    @Override
    public void save(OutboxMessage message) {
        jpaMessageRepository.save(message);
    }
}
