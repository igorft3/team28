package lebibop.userservice.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lebibop.userservice.enums.UserRole;
import lebibop.userservice.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "Сущность пользователя для регистрации")
public class RegisterDTO extends RegisterBaseDTO {
    @Override
    public User toUser() {
        User user = super.toUser();
        user.setUserRole(UserRole.ROLE_USER.toString());
        return user;
    }
}
