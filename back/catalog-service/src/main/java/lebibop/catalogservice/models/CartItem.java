package lebibop.catalogservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Entity
@Data
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
    private Long productId;
    private Integer quantity;
    private Double price;
    @Version
    private Long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(cartItemId, cartItem.cartItemId) && Objects.equals(cart, cartItem.cart) && Objects.equals(productId, cartItem.productId) && Objects.equals(quantity, cartItem.quantity) && Objects.equals(price, cartItem.price) && Objects.equals(version, cartItem.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartItemId, cart, productId, quantity, price, version);
    }
}
