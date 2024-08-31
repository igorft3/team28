package lebibop.catalogservice.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "products")
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private String productDescription;
    private Double productPrice;
    private String imageUrl;
    private String productCategory;
    private LocalDateTime createdAt;
    private String productStatus;
    @Version
    private Long version;
}
