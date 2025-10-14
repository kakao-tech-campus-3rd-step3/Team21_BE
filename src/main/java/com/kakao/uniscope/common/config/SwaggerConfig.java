package com.kakao.uniscope.common.config;

import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        String jwtSchemeName = "jwtAuth";

        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"));

        // 배포 환경 URL 설정
        Server deployServer = new Server();
        deployServer.setUrl("http://13.125.1.77:8080");
        deployServer.setDescription("실제 배포 환경 서버");

        // 로컬 환경 URL 설정
        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080");
        localServer.setDescription("로컬 개발 환경 서버");

        return new OpenAPI()
                .info(apiInfo())
                .components(components);
    }

    private Info apiInfo() {
        return new Info()
                .title("Uniscope API")
                .description("Uniscope API 명세서")
                .version("1.0.0");
    }
}
