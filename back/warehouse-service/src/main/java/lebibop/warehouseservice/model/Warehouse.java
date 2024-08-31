package lebibop.warehouseservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "warehouse")
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long warehouseId;
    private Long productId;
    private Integer quantity;
    private String productStatus;
    @Version
    private Long version;
}
