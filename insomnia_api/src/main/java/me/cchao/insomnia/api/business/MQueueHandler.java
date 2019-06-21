package me.cchao.insomnia.api.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import me.cchao.insomnia.api.bean.msgqueue.PushEvent;
import me.cchao.insomnia.api.security.SecurityHelper;
import me.cchao.insomnia.api.util.Logs;
import me.cchao.insomnia.common.constant.Constant;

/**
 * 极光推送的业务逻辑
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
    public static String mTemplateLikeTitle = "你的%1$s被%2$s点赞了";

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
        try {
            mRabbitTemplate.convertAndSend(mPushQueue, mapper.writeValueAsString(event));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
