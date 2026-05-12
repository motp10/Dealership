package dealership.infrastracture.schedullers;

import dealership.abstraction.OutboxMessageRepository;
import dealership.domain.message.MessageStatus;
import dealership.domain.message.OutboxMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduller {

    private final OutboxMessageRepository messageRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void processOutboxMessages() {
        List<OutboxMessage> messages = messageRepository.findByStatus(MessageStatus.PENDING);

        if (messages.isEmpty()) {
            return;
        }

        for (OutboxMessage message : messages) {
            try {
                String topic = determineTopic(message.getEventType());

                kafkaTemplate.send(topic, message.getId().toString(), message.getPayload())
                        .get(3, TimeUnit.SECONDS);

                message.setStatus(MessageStatus.SENT);
                messageRepository.save(message);

                log.info("Message {} successfully published to Kafka topic: {}", message.getId(), topic);

            } catch (Exception e) {
                log.error("Failed to publish message {} to Kafka: {}", message.getId(), e.getMessage());
            }
        }
    }
    private String determineTopic(String eventType) {
        return switch (eventType) {
            case "OrderSentForApproval" -> "orders.approval";
            default -> "orders.events";
        };
    }
}