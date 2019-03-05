package com.cchao.insomnia.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * @author Jcxcc
 * @date 18-5-2
 * @since 1.0
 */
@Configuration
@EnableSwagger2
//FIXME 生产环境隐藏 swagger
//@Profile({"dev", "test"})
public class SwaggerConfig {

    @Autowired
    private SwaggerProperty swaggerProperty;

    @Bean
    public Docket api() {
        return
                new Docket(DocumentationType.SWAGGER_2)
                        .select()
                        .apis(RequestHandlerSelectors.basePackage(swaggerProperty.getBasePackage()))
                        .paths(regex("^.*(?<!error)$"))
                        .build()
                        .securitySchemes(securityScheme())
                        .apiInfo(apiInfo());
    }

    private List<SecurityScheme> securityScheme() {
        SecurityScheme securityScheme = new ApiKey(swaggerProperty.getApiKey().getName(), swaggerProperty.getApiKey().getKeyName(), ApiKeyVehicle.HEADER.getValue());
        return
                Collections.singletonList(securityScheme);
    }

    private ApiInfo apiInfo() {
        return
                new ApiInfo(
                        swaggerProperty.getApiInfo().getTitle(),
                        swaggerProperty.getApiInfo().getDescription(),
                        swaggerProperty.getApiInfo().getVersion(),
                        swaggerProperty.getApiInfo().getTermsOfServiceUrl(),
                        new Contact(swaggerProperty.getApiInfo().getDeveloper(), swaggerProperty.getApiInfo().getDeveloperUrl(), swaggerProperty.getApiInfo().getDeveloperEmail()),
                        swaggerProperty.getApiInfo().getLicense(),
                        swaggerProperty.getApiInfo().getLicenseUrl(),
                        Collections.emptyList());
    }
}
