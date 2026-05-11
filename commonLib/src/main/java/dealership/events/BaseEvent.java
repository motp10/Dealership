package dealership.events;

import java.time.Instant;
import java.util.UUID;

public interface BaseEvent {
    UUID orderId();
    UUID traceId();
    Instant occurredAt();
}