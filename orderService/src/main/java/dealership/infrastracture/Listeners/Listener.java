package dealership.infrastracture.Listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import dealership.contracts.services.OrderService;
import dealership.events.OrderApprovedEvent;
import dealership.topics.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class Listener {
    private final ObjectMapper objectMapper;
    private final OrderService orderService;
    @Transactional
    @KafkaListener(topics = KafkaTopics.STORAGE_STATUS, groupId = "order-group")
    public void handleStorageStatus(String jsonPayload) {
        try {
            OrderApprovedEvent event = objectMapper.readValue(jsonPayload, OrderApprovedEvent.class);
            log.info("Storage approved order {}. Trace: {}", event.getOrderId(), event.getTraceId());

            orderService.updateOrderStatus(event.getOrderId());

        } catch (Exception e) {
            log.error("Error processing storage response", e);
        }
    }
}