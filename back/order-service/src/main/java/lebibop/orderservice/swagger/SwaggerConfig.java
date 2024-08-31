package lebibop.orderservice.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "order-services API",
                description = "Микросервис работы с заказами",
                version = "2.0.0"
        )
)
public class SwaggerConfig {}
