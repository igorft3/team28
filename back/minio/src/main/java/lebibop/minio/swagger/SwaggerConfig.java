package lebibop.minio.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "minio-services API",
                description = "Микросервис фото API",
                version = "2.0.0"
        )
)
public class SwaggerConfig {}
