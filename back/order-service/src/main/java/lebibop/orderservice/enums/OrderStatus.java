package lebibop.orderservice.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    IN_PROGRESS("В РАБОТЕ"),
    COLLECTED("СОБРАН"),
    ISSUED("ВЫДАН"),
    REJECTED("ОТКЛОНЕН");

    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }
}
