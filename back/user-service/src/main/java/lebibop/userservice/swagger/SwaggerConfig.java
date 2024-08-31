package lebibop.userservice.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "user-service API",
                description = "Микросервис работы с пользователями",
                version = "1.0.0"
        )
)
public class SwaggerConfig {}
