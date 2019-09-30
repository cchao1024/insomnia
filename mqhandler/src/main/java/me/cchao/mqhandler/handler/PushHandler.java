package me.cchao.mqhandler.handler;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import lombok.extern.slf4j.Slf4j;
import me.cchao.mqhandler.GlobalConfig;
import me.cchao.mqhandler.bean.PushEvent;

import static cn.jpush.api.push.model.notification.PlatformNotification.ALERT;

/**
 * 推送的处理
 *
 * @author cchao
 * @version 2019-06-21.
 */
@Component
@RabbitListener(queues = "push")
@Slf4j
public class PushHandler {
    @Autowired
    GlobalConfig mGlobalConfig;

    public static ObjectMapper mapper = new ObjectMapper();

    @Bean
    public Queue teaQueue() {
        return new Queue("push");
    }

    @RabbitHandler
    public void process(@Payload String message) {
        log.info("Listener收到String类型mq消息:" + message);
        try {
            PushEvent event = mapper.readValue(message, PushEvent.class);
            startPush(event);
        } catch (IOException e) {
            log.error("json转化异常 PushEvent：" + message + e.getMessage());
        }
    }

    /**
     * 发送推送
     *
     * @param event startPush
     */
    private void startPush(PushEvent event) {
        log.info("start push event" + event);

        JPushClient jpushClient = new JPushClient(mGlobalConfig.pushMasterSecret, mGlobalConfig.pushAppKey, null, ClientConfig.getInstance());

        // For push, all you need do is to build PushPayload object.
        PushPayload payload = buildPushObjectAndroid(event);

        try {
            PushResult result = jpushClient.sendPush(payload);
            log.info("Got result - " + result);

        } catch (APIConnectionException e) {
            // Connection error, should retry later
            log.error("Connection error, should retry later", e);

        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            log.error("Should review the error, and fix the request", e);
            log.info("HTTP Status: " + e.getStatus());
            log.info("Error Code: " + e.getErrorCode());
            log.info("Error Message: " + e.getErrorMessage());
        }
    }

    /**
     * 构建推送对象
     *
     * @return pushBean
     */
    public static PushPayload buildPushObjectAndroid(PushEvent event) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(String.valueOf(event.getToUserId())))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle(event.getTitle())
                                .setAlert(event.getContent())
                                .addExtra("fromUserId", event.getFromUserId())
                                .addExtra("focusId", event.getFocusId())
                                .addExtra("type", event.getType())
                                .build())
                        .build())
                .build();
    }
}
