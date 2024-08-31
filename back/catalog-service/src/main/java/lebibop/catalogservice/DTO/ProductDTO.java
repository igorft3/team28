package lebibop.catalogservice.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lebibop.catalogservice.models.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность для создания продукта")
public class ProductDTO {
    @Schema(description = "Название", example = "Кассета VHS с фильмом")
    private String productName;
    @Schema(description = "Описание", example = "Оригинальная кассета VHS с культовым фильмом 1980-х годов")
    private String productDescription;
    @Schema(description = "Цена", example = "1500")
    private Double productPrice;
    @Schema(description = "Ссылка на фото", example = "vhs.png")
    private String imageUrl;
    @Schema(description = "Категория", example = "АНТИКВАРНЫЕ ИТ-АРТЕФАКТЫ")
    private String productCategory;
    @Schema(description = "Статус", example = "ДОСТУПЕН")
    private String productStatus;

    public Product toProduct(){
        Product product = new Product();
        product.setProductName(productName);
        product.setProductDescription(productDescription);
        product.setProductPrice(productPrice);
        product.setImageUrl(imageUrl);
        product.setProductCategory(productCategory);
        product.setProductStatus(productStatus);

        return product;
    }
}
