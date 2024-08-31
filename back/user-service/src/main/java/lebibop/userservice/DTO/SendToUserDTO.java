package lebibop.userservice.DTO;

import lombok.Data;

@Data
public class SendToUserDTO {
    private Long userId;
    private Double price;

    public SendToUserDTO(Long userId, Double price) {
        this.userId = userId;
        this.price = price;
    }
}
