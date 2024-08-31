package lebibop.catalogservice.services;

import lebibop.catalogservice.models.Cart;
import lebibop.catalogservice.models.CartItem;
import lebibop.catalogservice.models.Product;
import lebibop.catalogservice.DTO.ProductDTO;
import lebibop.catalogservice.kafka.MessageProducer;
import lebibop.catalogservice.repositories.CartItemRepository;
import lebibop.catalogservice.repositories.CartRepository;
import lebibop.catalogservice.repositories.ProductRepository;
import lebibop.catalogservice.enums.ProductCategory;
import lebibop.catalogservice.enums.ProductStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ExternalService externalService;
    private final CartItemRepository cartItemRepository;
    private final MessageProducer messageProducer;
    private final CartRepository cartRepository;

    @Transactional(readOnly = true)
    public List<Product> searchProductsByName(String productName) {
        return productRepository.findByProductNameContainingIgnoreCase(productName);
    }

    @Transactional(readOnly = true)
    public List<Product> searchProductsByPriceRange(Double minPrice, Double maxPrice) {
        return productRepository.findByProductPriceBetween(minPrice, maxPrice);
    }

    @Transactional
    public void addProduct(ProductDTO productDTO) {
        Product product = productDTO.toProduct();
        product.setCreatedAt(LocalDateTime.now());
        productRepository.save(product);
        messageProducer.sendAddToWarehouse(product.getProductId());
    }

    @Transactional(readOnly = true)
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByProductCategory(category);
    }

    @Transactional(readOnly = true)
    public List<Product> getProductsByStatus(String status) {
        return productRepository.findByProductStatus(status);
    }

    @Transactional
    public void updateProduct(Long id, ProductDTO productDTO) {
        Product product = getProductById(id);

        product.setProductName(productDTO.getProductName());
        product.setProductDescription(productDTO.getProductDescription());
        product.setImageUrl(productDTO.getImageUrl());
        product.setProductCategory(productDTO.getProductCategory());
        product.setProductStatus(productDTO.getProductStatus());

        if (!Objects.equals(product.getProductPrice(), productDTO.getProductPrice()))
            updateProductPriceAndAdjustCarts(product.getProductId(), productDTO.getProductPrice());
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = getProductById(id);

        removeItemsByProductId(id);
        productRepository.delete(product);
        messageProducer.sendDeleteToWarehouse(id);
    }

    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Товар не найден"));
    }

    @Transactional
    public void changeStatus(Long id, String newStatus) {
        Product product = getProductById(id);

        product.setProductStatus(newStatus);
        productRepository.save(product);
    }

    public List<String> getCategories() {
        return Arrays.stream(ProductCategory.values())
                .map(ProductCategory::getDisplayName)
                .toList();
    }

    public List<String> getStatuses() {
        return Arrays.stream(ProductStatus.values())
                .map(ProductStatus::getDisplayName)
                .toList();
    }

    @Transactional
    public void addProductToCart(Long userId, Long productId, Integer quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Количество товара должно быть больше 0");

        Cart cart = getCart(userId);
        Product product = getProductById(productId);
        if (Objects.equals(product.getProductStatus(), ProductStatus.ARCHIVE.getDisplayName()))
            throw new IllegalArgumentException("Этот товар находится в архиве");
        addItemToCart(product, quantity, cart);
    }

    @Transactional
    public void addItemToCart(Product product, Integer quantity, Cart cart) {
        Optional<CartItem> cartItemOptional = cart.getCartItems().stream()
                .filter(item -> item.getProductId().equals(product.getProductId()))
                .findFirst();

        CartItem cartItem;
        if (cartItemOptional.isPresent()) {
            cartItem = cartItemOptional.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setPrice(product.getProductPrice());
        } else {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProductId(product.getProductId());
            cartItem.setQuantity(quantity);
            cartItem.setPrice(product.getProductPrice());
            cart.getCartItems().add(cartItem);
        }

        cart.setTotalItems(cart.getCartItems().stream().mapToInt(CartItem::getQuantity).sum());
        cart.setTotalPrice(cart.getCartItems().stream()
                .mapToDouble(Item -> Item.getPrice() * Item.getQuantity())
                .sum());

        cartRepository.save(cart);
    }


    @Transactional
    public Cart getCart(Long userId) {
        Optional<Cart> cartOptional = cartRepository.findByUserId(userId);
        Cart cart;
        if (cartOptional.isPresent()) {
            cart = cartOptional.get();
        } else {
            cart = new Cart();
            cart.setUserId(userId);
            cart.setTotalItems(0);
            cart.setTotalPrice(0.0);
            cart.setCartItems(new ArrayList<>());
            cart = cartRepository.save(cart);
        }
        return cart;
    }

    public Cart findCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("Корзина не найдена"));

    }


    @Transactional
    public void removeProductFromCart(Long userId, Long productId, int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Количество должно быть больше 0");

        Cart cart = findCartByUserId(userId);
        Product product = getProductById(productId);
        removeItemFromCart(product, quantity, cart);
    }

    @Transactional
    public void removeItemFromCart(Product product, Integer quantity, Cart cart) {
        Optional<CartItem> cartItemOptional = cart.getCartItems().stream()
                .filter(item -> item.getProductId().equals(product.getProductId()))
                .findFirst();

        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            int newQuantity = cartItem.getQuantity() - quantity;
            if (newQuantity < 0)
                throw new IllegalArgumentException("Число больше, чем есть товаров в корзине");

            if (newQuantity > 0) {
                cartItem.setQuantity(newQuantity);
                cartItem.setPrice(cartItem.getPrice());
            } else {
                cart.getCartItems().remove(cartItem);
            }

            cart.setTotalItems(cart.getCartItems().stream().mapToInt(CartItem::getQuantity).sum());
            cart.setTotalPrice(cart.getCartItems().stream()
                    .mapToDouble(item -> item.getPrice() * item.getQuantity())
                    .sum());

            cartRepository.save(cart);
        }
    }


    @Transactional
    public void removeAllProductFromCart(Long userId, Long productId) {
        Cart cart = findCartByUserId(userId);
        Product product = getProductById(productId);
        removeAllItemsOfProductFromCart(product, cart);
    }

    @Transactional
    public void removeAllItemsOfProductFromCart(Product product, Cart cart) {
        Optional<CartItem> cartItemOptional = cart.getCartItems().stream()
                .filter(item -> item.getProductId().equals(product.getProductId()))
                .findFirst();

        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            cart.getCartItems().remove(cartItem);

            cart.setTotalItems(cart.getCartItems().stream().mapToInt(CartItem::getQuantity).sum());
            cart.setTotalPrice(cart.getCartItems().stream()
                    .mapToDouble(item -> item.getPrice() * item.getQuantity())
                    .sum());

            cartRepository.save(cart);
        }
    }

    @Transactional
    public void removeItemsByProductId(Long productId) {
        Optional<List<CartItem>> itemsToRemoveOptional = cartItemRepository.findByProductId(productId);

        if (itemsToRemoveOptional.isPresent()) {
            List<CartItem> itemsToRemove = itemsToRemoveOptional.get();

            for (CartItem item : itemsToRemove) {
                Cart cart = item.getCart();
                cart.setTotalItems(cart.getTotalItems() - item.getQuantity());
                cart.setTotalPrice(cart.getTotalPrice() - item.getPrice() * item.getQuantity());

                cart.getCartItems().remove(item);
            }

            cartRepository.saveAll(
                    itemsToRemove.stream().map(CartItem::getCart).collect(Collectors.toList())
            );

            cartItemRepository.deleteAll(itemsToRemove);
        }
    }


    @Transactional
    public void updateProductPriceAndAdjustCarts(Long productId, Double newPrice) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Продукт не найден"));

        Double oldPrice = product.getProductPrice();
        if (newPrice == null || newPrice <= 0) throw new IllegalArgumentException("Введенная цена некорректна");

        product.setProductPrice(newPrice);

        List<CartItem> cartItems = cartItemRepository.findByProductId(productId)
                .orElseThrow(() -> new IllegalArgumentException("Элементы корзины с продуктом ID " + productId + " не найдены"));

        for (CartItem item : cartItems) {
            Cart cart = item.getCart();
            if (cart == null) continue;

            cart.setTotalPrice(cart.getTotalPrice() - oldPrice * item.getQuantity());
            item.setPrice(newPrice);
            cart.setTotalPrice(cart.getTotalPrice() + newPrice * item.getQuantity());
        }

        cartItemRepository.saveAll(cartItems);
        cartRepository.saveAll(cartItems.stream().map(CartItem::getCart).distinct().collect(Collectors.toList()));
        productRepository.save(product);
    }


    @Transactional
    public void sendCart(Long userId, String jwtToken) {
        Cart cart = findCartByUserId(userId);
        if (cart.getCartItems().isEmpty())
            throw new IllegalArgumentException("Нельзя оформить заказ, т.к. корзина пуста");

        for (CartItem cartItem : cart.getCartItems()) {
            Product product = getProductById(cartItem.getProductId());
            if (product.getProductStatus().equals(ProductStatus.ARCHIVE.getDisplayName())) {
                throw new IllegalArgumentException("Продукт '" + product.getProductName() + "' находится в архиве.");
            }
        }

        externalService.checkAvailabilityToExternalService(cart, jwtToken);
        messageProducer.sendCart(cart);
        clearCart(userId);
    }

    @Transactional
    public void clearCart(Long userId) {
        Cart cart = findCartByUserId(userId);
        cart.getCartItems().clear();
        cart.setTotalItems(0);
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);
    }
}