package com.example.myrestfulservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition( //Info 객체 생성
        info = @Info(title = "My RESTful Service API 명세서",
                description = "Spring Boot로 개발하는 RESTful API 명세서 입니다",
                version = "v1.0,0"))

@Configuration
@RequiredArgsConstructor
public class NewSwaggerConfig {

    @Bean
    public GroupedOpenApi custiomTestOpenApi() {
        String[] paths = {"/users/**", "/user/**","/admin/**"}; //공개할 API 지정 따라서 HEllouser Controller 는 사라짐

        return GroupedOpenApi
                .builder()
                .group("일반 사용자와 관리자를 위란 User 도메인에 대한 API")
                .pathsToMatch(paths).build();
    }
}
