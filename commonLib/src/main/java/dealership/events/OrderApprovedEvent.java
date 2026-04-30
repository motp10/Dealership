package dealership.events;

import java.time.Instant;
import java.util.UUID;

public record OrderApprovedEvent(
        UUID getOrderId,
        String getTraceId,
        Instant occurredAt,
        UUID assemblyOrderId
) implements BaseEvent {}