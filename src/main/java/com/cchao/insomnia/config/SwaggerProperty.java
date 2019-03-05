package com.cchao.insomnia.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Jcxcc
 * @date 18-5-11
 * @since 1.0
 */
@Data
@Component
@NoArgsConstructor
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperty {

    private String basePackage = "com.cchao.insomnia";

    private Key apiKey = new Key();

    private Info apiInfo = new Info();

    /**
     * 这里假定是头部参数
     */
    @Data
    @NoArgsConstructor
    public static class Key {

        private String name = "Authorization";

        private String keyName = "Authorization";
    }

    @Data
    @NoArgsConstructor
    public static class Info {
        private String title = "insomnia REST API";

        private String description = "insomnia";

        private String version = "v1";

        private String termsOfServiceUrl = "";

        private String developer = "cchao";

        private String developerUrl = "https://github.com/cchao1024";

        private String developerEmail = "xcc880@gmail.com";

        private String license = "";

        private String licenseUrl = "";
    }
}
