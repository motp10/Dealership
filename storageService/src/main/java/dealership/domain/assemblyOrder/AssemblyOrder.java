package dealership.domain.assemblyOrder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "assembly_orders")
@Getter @Setter @NoArgsConstructor
public class AssemblyOrder {
    @Id
    private UUID id = UUID.randomUUID();
    @Column(unique = true)
    private UUID sourceOrderId;
    private String status;
    private UUID traceId;
}