package me.cchao.mqhandler;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 配置项
 *
 * @author cchao
 * @version 2019-06-21.
 */
@Data
@Component
@ConfigurationProperties(prefix = "configs")
@Configuration
public class GlobalConfig {
    public String pushMasterSecret;
    public String pushAppKey;
}
