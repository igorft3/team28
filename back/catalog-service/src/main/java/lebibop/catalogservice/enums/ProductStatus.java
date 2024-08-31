package lebibop.catalogservice.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter @RequiredArgsConstructor
public enum ProductStatus {
    AVAILABLE("ДОСТУПЕН"),
    ARCHIVE("АРХИВ");

    private final String displayName;
}
