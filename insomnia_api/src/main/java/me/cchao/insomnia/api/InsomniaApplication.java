package me.cchao.insomnia.api;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EnableCaching
public class InsomniaApplication {

    public static void main(String[] args) {
        SpringApplication.run(InsomniaApplication.class, args);
    }

    @Bean
    public Queue emailVerifyQueue() {
        return new Queue("email_verify");
    }

    @Bean
    public Queue pushQueue() {
        return new Queue("push");
    }
}
