package lebibop.warehouseservice.enums;

import lombok.Getter;

@Getter
public enum ProductStatus {
    STATUS_A("В ПРОДАЖЕ"),
    STATUS_B("СНЯТ С ПРОДАЖИ");

    private final String displayName;

    ProductStatus(String displayName) {
        this.displayName = displayName;
    }
}
