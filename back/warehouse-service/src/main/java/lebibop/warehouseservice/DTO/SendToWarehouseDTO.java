package lebibop.warehouseservice.DTO;

import lombok.Data;

import java.util.List;

@Data
public class SendToWarehouseDTO {
    private List<ProductQuantityDTO> items;

    @Data
    public static class ProductQuantityDTO {
        private Long productId;
        private Integer quantity;
    }
}
