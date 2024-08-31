package lebibop.catalogservice.repositories;

import lebibop.catalogservice.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<List<CartItem>> findByProductId(Long productId);
}
