package me.cchao.mqhandler.handler;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import me.cchao.mqhandler.GlobalConfig;
import me.cchao.mqhandler.bean.EmailEvent;
import me.cchao.mqhandler.bean.PushEvent;
import me.cchao.mqhandler.service.EmailService;

/**
 * 邮箱分发
 *
 * @author cchao
 * @version 2019-06-21.
 */
@Component
@RabbitListener(queues = "email")
@Slf4j
public class MailHandler {
    @Autowired
    MailSender mailSender;
    @Autowired
    EmailService mEmailService;

    public static ObjectMapper mapper = new ObjectMapper();

    @Bean
    public Queue getQueue() {
        return new Queue("email");
    }

    @RabbitHandler
    public void process(@Payload String message) {
        log.info("收到email mq消息:" + message);
        try {
            EmailEvent event = mapper.readValue(message, EmailEvent.class);
            sendVerifyEMail(event);
        } catch (IOException e) {
            log.error("json转化异常 PushEvent：" + message + e.getMessage());
        }
    }

    /**
     * 发送验证码
     *
     * @param event emailEvent
     */
    public void sendVerifyEMail(EmailEvent event) {
        SimpleMailMessage helper = new SimpleMailMessage();
        helper.setFrom("insomnia163@163.com");
        helper.setTo(event.getToEmail());
        helper.setSubject(event.getSubject());
        helper.setText("验证码： " + mEmailService.generateVerifyCode(event.getToEmail()));
        mailSender.send(helper);
    }
}
