package lebibop.warehouseservice.repository;

import lebibop.warehouseservice.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    Optional<Warehouse>  findByProductId(Long productId);
    List<Warehouse> findByProductStatus(String status);
    List<Warehouse> findByQuantityGreaterThan(Integer quantity);
    List<Warehouse> findByQuantity(Integer quantity);
}
