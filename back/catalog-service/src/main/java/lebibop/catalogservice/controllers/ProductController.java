package lebibop.catalogservice.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lebibop.catalogservice.DTO.ProductDTO;
import lebibop.catalogservice.models.Product;
import lebibop.catalogservice.services.ProductService;
import lebibop.catalogservice.swagger.CatalogAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController implements CatalogAPI {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    public ResponseEntity<?> searchProductsByName(@RequestParam String name) {
        List<Product> products = productService.searchProductsByName(name);
        return products.isEmpty() ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).body("Ничего не найдено по запросу: " + name) :
                ResponseEntity.status(HttpStatus.OK).body(products);
    }

    public ResponseEntity<?> searchProductsByPriceRange(@RequestParam Double minPrice, @RequestParam Double maxPrice) {
        List<Product> products = productService.searchProductsByPriceRange(minPrice, maxPrice);
        return products.isEmpty() ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).body("Ничего не найдено в диапазоне цен: " + minPrice + " - " + maxPrice) :
                ResponseEntity.status(HttpStatus.OK).body(products);
    }

    public ResponseEntity<?> updateProductPrice(@PathVariable Long productId,
                                                @RequestParam Double newPrice) {
        productService.updateProductPriceAndAdjustCarts(productId, newPrice);
        return ResponseEntity.status(HttpStatus.OK).body("Цена товара успешно обновлена");
    }

    public ResponseEntity<?> addProduct(@RequestBody ProductDTO productDTO) {
        productService.addProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Товар успешно добавлен");
    }

    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        productService.updateProduct(id, productDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Товар успешно обновлен");
    }

    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body("Товар успешно удален");
    }

    public ResponseEntity<?> getProductsByCategory(@RequestParam String category) {
        List<Product> products = productService.getProductsByCategory(category);
        return products.isEmpty() ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).body("Ничего не найдено по запросу: " + category) :
                ResponseEntity.status(HttpStatus.OK).body(products);
    }

    public ResponseEntity<?> getProductsByStatus(@RequestParam String status) {
        List<Product> products = productService.getProductsByStatus(status);
        return products.isEmpty() ?
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ничего не найдено по запросу: " + status) :
                ResponseEntity.status(HttpStatus.OK).body(products);
    }

    public ResponseEntity<?> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    public ResponseEntity<String> changeStatus(@PathVariable Long id, @RequestParam String newStatus) {
        productService.changeStatus(id, newStatus);
        return ResponseEntity.status(HttpStatus.OK).body("Статус успешно изменен");
    }

    public ResponseEntity<?> getCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getCategories());
    }

    public ResponseEntity<?> getStatuses() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getStatuses());
    }

    public ResponseEntity<?> addProductToCart(@PathVariable Long productId,
                                              @RequestParam Integer quantity,
                                              HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        productService.addProductToCart(userId, productId, quantity);
        return ResponseEntity.status(HttpStatus.OK).body("Товар успешно добавлен в корзину в количестве: " + quantity + "шт.");
    }

    public ResponseEntity<?> removeProductFromCart(@PathVariable Long productId,
                                                   @RequestParam Integer quantity,
                                                   HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        productService.removeProductFromCart(userId, productId, quantity);
        return ResponseEntity.status(HttpStatus.OK).body("Количество товара изменено");

    }

    public ResponseEntity<?> removeAllProductFromCart(@PathVariable Long productId,
                                                      HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        productService.removeAllProductFromCart(userId, productId);
        return ResponseEntity.status(HttpStatus.OK).body("Весь товар успешно удален");

    }

    public ResponseEntity<?> clearCart(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        productService.clearCart(userId);
        return ResponseEntity.status(HttpStatus.OK).body("Корзина очищена");

    }

    public ResponseEntity<?> sendCart(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        productService.sendCart(userId, request.getHeader("Authorization").substring(7));
        return ResponseEntity.status(HttpStatus.OK).body("Заказ успешно оформлен");
    }

    public ResponseEntity<?> getCart(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productService.getCart(userId));
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());
        }
    }
}
