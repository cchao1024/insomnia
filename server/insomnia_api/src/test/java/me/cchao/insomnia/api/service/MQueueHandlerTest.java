package me.cchao.insomnia.api.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import me.cchao.insomnia.api.business.MQueueHandler;
import me.cchao.insomnia.common.constant.Constant;

/**
 * @author cchao
 * @version 2019-06-20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MQueueHandlerTest {

    @Autowired
    MQueueHandler mMQueueHandler;

    @Test
    public void pushLikeEvent() {
        for (int i = 0; i < 3; i++) {
            mMQueueHandler.pushLikeEvent(Constant.POST_TYPE.Post, 0, 32);
        }
    }
}