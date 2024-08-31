package lebibop.catalogservice.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter @RequiredArgsConstructor
public enum ProductCategory {
    MERCH("МЕРЧ"),
    IT_ARTIFACTS("АНТИКВАРНЫЕ ИТ-АРТЕФАКТЫ"),
    GIFTS("ПОДАРКИ");

    private final String displayName;
}
