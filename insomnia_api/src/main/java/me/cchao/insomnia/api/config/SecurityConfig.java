package me.cchao.insomnia.api.config;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : cchao
 * @version 2019-02-13
 */

@Data
@ConfigurationProperties(prefix = "security")
@Component
public class SecurityConfig {

    /**
     * 过期时间
     */
    public String expire_time;
}