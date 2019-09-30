package me.cchao.insomnia.api.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import me.cchao.insomnia.api.bean.mq.EmailEvent;
import me.cchao.insomnia.api.bean.mq.PushEvent;
import me.cchao.insomnia.api.security.SecurityHelper;
import me.cchao.insomnia.api.util.Logs;
import me.cchao.insomnia.common.constant.Constant;

/**
 * 消息队列的业务
 *
 * @author cchao
 * @version 2019-06-20.
 */
@Component
public class MQueueHandler {

    @Autowired
    AmqpTemplate mRabbitTemplate;
    public static ObjectMapper mapper = new ObjectMapper();


    public static String mPushQueue = "push";
    public static String mEmailQueue = "email";

    public static String mTemplateLikeTitle = "你的%1$s被%2$s点赞了";
    public static String mTemplateEmailSubject = "insomnia %1$s，10分钟内有效";

    public void pushLikeEvent(int type, long focusId, long toUserId) {
        String typeStr = "说说";
        switch (type) {
            case Constant.POST_TYPE.Comment:
                typeStr = "评论";
                break;
            case Constant.POST_TYPE.Reply:
                typeStr = "回复";
                break;
        }

        String content = String.format(mTemplateLikeTitle, typeStr, SecurityHelper.getUserName());
        Logs.println(content);
        PushEvent event = new PushEvent();
        event.setTitle("获得小伙伴点赞")
                .setContent(content)
                .setToUserId(toUserId)
                .setFromUserId(SecurityHelper.getUserId())
                .setType(type)
                .setFocusId(focusId)
                .setUrl("");

        sendToQueue(mPushQueue, event);
    }

    public void pushVerifyEmailEvent(int type, String email) {
        String typeStr = "未定义";
        switch (type) {
            case Constant.Email_TYPE.Verify_Email:
                typeStr = "验证邮箱";
                break;
        }

        EmailEvent emailEvent = new EmailEvent();
        emailEvent.setSubject(String.format(mTemplateEmailSubject, typeStr))
                .setToEmail(email)
                .setType(type);

        sendToQueue(mEmailQueue, emailEvent);
    }

    /**
     * 放入相应的队列
     *
     * @param queue  name
     * @param object json对象
     */
    private void sendToQueue(String queue, Object object) {
        try {
            mRabbitTemplate.convertAndSend(queue, mapper.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
