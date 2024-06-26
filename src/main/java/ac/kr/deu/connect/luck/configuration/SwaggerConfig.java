package ac.kr.deu.connect.luck.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "Connect Luck API", version = "1.0",
                description = "<h1>Connect Luck을 위한 OpenAPI 문서 입니다.</h1><h2>필독 사항</h2><ul><li>단순 텍스트 전송의 경우 application/json 으로 전송해야 합니다.</li><li>사진이 포함된 경우 multipart/form-data 로 전송해야 합니다.</li><li>Swagger에서 테스트를 진행할 때 이미지가 없다면 <b>Send Empty Value를 체크 해제</b> 해야 합니다.</li></ul>",
                contact = @io.swagger.v3.oas.annotations.info.Contact(
                        name = "김남주",
                        email = "cmsong111@naver.com"
                )
        ))
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().
                        addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes
                        ("Bearer Authentication", createAPIKeyScheme()));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }
}
