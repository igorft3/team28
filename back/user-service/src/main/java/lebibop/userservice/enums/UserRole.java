package lebibop.userservice.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_MANAGER("ROLE_MANAGER"),
    ROLE_USER("ROLE_USER");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }
}
