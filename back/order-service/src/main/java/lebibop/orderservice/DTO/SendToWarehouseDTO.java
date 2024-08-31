package lebibop.orderservice.DTO;

import lebibop.orderservice.models.Order;
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

    public static SendToWarehouseDTO fromOrder(Order order) {
        List<ProductQuantityDTO> items = order.getOrderItems().stream()
                .map(orderItem -> {
                    ProductQuantityDTO dto = new ProductQuantityDTO();
                    dto.setProductId(orderItem.getProductId());
                    dto.setQuantity(orderItem.getQuantity());
                    return dto;
                })
                .toList();

        SendToWarehouseDTO sendToWarehouseDTO = new SendToWarehouseDTO();
        sendToWarehouseDTO.setItems(items);
        return sendToWarehouseDTO;
    }
}
