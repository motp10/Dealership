package dealership.events;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderSentForApprovalEvent(
        UUID orderId,
        UUID traceId,
        Instant occurredAt,
        UUID assemblyOrderId,
        List<DetailItem> details
) implements BaseEvent {

    public record DetailItem(
            String detailType,
            String detailInfo
    ) {}
}