package lebibop.catalogservice.repositories;

import lebibop.catalogservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductCategory(String categoryId);

    List<Product> findByProductStatus(String categoryId);

    List<Product> findByProductNameContainingIgnoreCase(String productName);

    List<Product> findByProductPriceBetween(Double minPrice, Double maxPrice);
}
