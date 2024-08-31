package lebibop.catalogservice.DTO;

import lombok.Data;

import java.util.List;

@Data
public class CartItemRequest {
    private Double price;
    private List<CartItemDTO> items;
}
