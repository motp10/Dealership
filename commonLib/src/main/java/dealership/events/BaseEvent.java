package dealership.events;

import java.time.Instant;
import java.util.UUID;

public interface BaseEvent {
    UUID getOrderId();
    String getTraceId();
    UUID getDetailId();
    Instant occurredAt();
}
