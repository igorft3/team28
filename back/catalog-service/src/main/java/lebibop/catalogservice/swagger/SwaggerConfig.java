package lebibop.catalogservice.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "catalog-services API",
                description = "Микросервис каталог + корзина API",
                version = "2.0.0"
        )
)
public class SwaggerConfig {}
