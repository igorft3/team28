package lebibop.userservice.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Сущность пользователя для авторизации")
public class LoginDTO {
    @Schema(description = "Никнейм", example = "user2000")
    private String username;
    @Schema(description = "Пароль", example = "password123")
    private String password;
}
