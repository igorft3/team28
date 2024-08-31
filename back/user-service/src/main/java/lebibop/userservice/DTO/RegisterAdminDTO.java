package lebibop.userservice.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lebibop.userservice.model.User;
import lombok.Data;

import java.util.Objects;

@Data
@Schema(description = "Сущность пользователя для регистрации администратором")
public class RegisterAdminDTO extends RegisterBaseDTO {
    @Schema(description = "Роль", example = "ROLE_ADMIN")
    private String role;

    @Override
    public User toUser() {
        User user = super.toUser();
        user.setUserRole(this.role);
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RegisterAdminDTO that = (RegisterAdminDTO) o;
        return Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), role);
    }
}
