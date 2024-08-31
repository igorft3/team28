package lebibop.catalogservice.DTO;

import lebibop.catalogservice.models.Cart;

import java.util.stream.Collectors;

public class CartMapper {

    public static CartItemRequest toCartItemRequest(Cart cart) {
        CartItemRequest cartDTO = new CartItemRequest();
        cartDTO.setPrice(cart.getTotalPrice());
        cartDTO.setItems(cart.getCartItems().stream()
                .map(item -> {
                    CartItemDTO itemDTO = new CartItemDTO();
                    itemDTO.setProductId(item.getProductId());
                    itemDTO.setQuantity(item.getQuantity());
                    return itemDTO;
                })
                .collect(Collectors.toList()));
        return cartDTO;
    }
}