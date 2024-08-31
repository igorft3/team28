package lebibop.userservice.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lebibop.userservice.model.User;
import lombok.Data;

import java.util.Objects;

@Data
@Schema(description = "Базовая сущность пользователя для регистрации")
public class RegisterBaseDTO {
    @Schema(description = "Никнейм", example = "user2000")
    private String username;
    @Schema(description = "Пароль", example = "password123")
    private String password;
    @Schema(description = "Почта", example = "pochta@pochta.ru")
    private String email;
    @Schema(description = "Имя", example = "Никита")
    private String firstName;
    @Schema(description = "Фамилия", example = "Иванов")
    private String lastName;

    public User toUser() {
        User user = new User();
        user.setUsername(this.username.toLowerCase());
        user.setPassword(this.password);
        user.setEmail(this.email);
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setBalance(0.0);

        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterBaseDTO that = (RegisterBaseDTO) o;
        return Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(email, that.email) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, email, firstName, lastName);
    }
}
