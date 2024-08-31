package lebibop.orderservice.DTO;

import lombok.Data;

import java.util.List;

@Data
public class CartDTO {
    private Long userId;
    private Integer totalItems;
    private Double totalPrice;
    private List<CartItemDTO> cartItems;

    @Data
    public static class CartItemDTO {
        private Long productId;
        private Integer quantity;
        private Double price;
    }
}
