package dealership.domain.OrderMessages;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "assembly_orders")
@Getter
@Setter
@NoArgsConstructor
public class OrderMessage {
    @Id
    private UUID id = UUID.randomUUID();
    @Column(unique = true)
    private UUID sourceOrderId;
    private String status;
    private String traceId;
}