package dealership.infrastracture.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import dealership.contracts.services.DetailService;
import dealership.events.OrderSentForApprovalEvent;
import dealership.topics.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Listener {
    private final ObjectMapper objectMapper;
    private final DetailService detailService;

    @KafkaListener(topics = KafkaTopics.ORDER_APPROVAL, groupId = "storage-group")
    public void handleNewOrder(String jsonPayload) {
        try {
            log.info("RECEIVED RAW PAYLOAD: {}", jsonPayload);
            var event = objectMapper.readValue(jsonPayload, OrderSentForApprovalEvent.class);

            detailService.processAssemblyOrder(event);

        } catch (Exception e) {
            log.error("Error processing order from Kafka", e);
        }
    }
}