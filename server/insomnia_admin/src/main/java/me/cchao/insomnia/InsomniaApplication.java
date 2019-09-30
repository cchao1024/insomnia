package me.cchao.insomnia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import javax.servlet.MultipartConfigElement;

@EnableJpaAuditing
@SpringBootApplication
public class InsomniaApplication {

    public static void main(String[] args) {
        SpringApplication.run(InsomniaApplication.class, args);
    }

    /**
     * 文件上传配置
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize(DataSize.of(30, DataUnit.MEGABYTES));
        /// 设置总上传数据总大小
        factory.setMaxFileSize(DataSize.of(50, DataUnit.MEGABYTES));
        return factory.createMultipartConfig();
    }
}
