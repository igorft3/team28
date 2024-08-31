package lebibop.warehouseservice.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "warehouse-service API",
                description = "Микросервис работы со складом",
                version = "2.0.0"
        )
)
public class SwaggerConfig {}
