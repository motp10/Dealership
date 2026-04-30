package dealership.events;

import java.time.Instant;
import java.util.UUID;

public record OrderSentForApprovalEvent(
        UUID getOrderId,
        String getTraceId,
        Instant occurredAt,
        UUID detailID
) implements BaseEvent {}