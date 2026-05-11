package dealership.events;

import java.time.Instant;
import java.util.UUID;

public record OrderApprovedEvent(
        UUID orderId,
        UUID traceId,
        Instant occurredAt,
        boolean approve,
        UUID managerId
) implements BaseEvent {}