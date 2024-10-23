package B2chat.b2chat.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("B2Chat API")
                        .description("Documentación de la API de B2Chat desarrollador por : ROLANDO MAMANI SALAS (CBBA-BOLIVIA)")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Soporte B2Chat")
                                .email("soporte@b2chat.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentación oficial")
                        .url("https://b2chat.com/docs"));
    }
}
