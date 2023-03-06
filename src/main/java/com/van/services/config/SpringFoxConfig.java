package com.van.services.config;

import springfox.documentation.service.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.van.services.controller"))
                .paths(PathSelectors.ant("/*"))
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, List.of(
                        new ResponseBuilder().code("500")
                                .description("500 message").build(),
                        new ResponseBuilder().code("403")
                                .description("Forbidden!!!!!").build(),
                        new ResponseBuilder().code("401")
                                .description("Unauthorized!!!!!").build()
                ));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("Shopping Cart REST API", "", "API v1.0.0", "Terms of service",
                new Contact("Vladimir Nalitkin", "www.example.com", "vladimir.nalitkin@gmail.com"),
                "License of API", "API license URL", List.of());
    }
}