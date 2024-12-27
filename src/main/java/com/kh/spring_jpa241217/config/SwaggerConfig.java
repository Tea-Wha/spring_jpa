package com.kh.spring_jpa241217.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        // 1. SecurityScheme 설정 (JWT 토큰 방식)
        SecurityScheme bearerAuth = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)    // HTTP 인증 방식
                .scheme("bearer")                 // Bearer 토큰
                .bearerFormat("JWT")              // JWT 형식
                .in(SecurityScheme.In.HEADER)      // 헤더에 인증 정보를 포함
                .name("Authorization");           // 헤더 키: "Authorization"

        // 2. 등록한 SecurityScheme를 Components에 추가
        Components components = new Components()
                .addSecuritySchemes("bearerAuth", bearerAuth);

        // 3. 실제 요청에 SecurityRequirement로 적용
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("bearerAuth");

        // 4. OpenAPI 객체를 구성할 때 Security 정보와 함께 추가
        return new OpenAPI()
                .components(components)
                .addSecurityItem(securityRequirement)
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("OOO API 목록")
                .description("종합 프로젝트 API Swagger 문서입니다.")
                .version("0.1.0");
    }
}
