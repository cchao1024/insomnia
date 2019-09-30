package me.cchao.mqhandler.handler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import me.cchao.mqhandler.bean.EmailEvent;

import static org.junit.Assert.*;

/**
 * @author cchao
 * @version 2019-06-21.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailHandlerTest {

    @Autowired
    MailHandler mMailHandler;

    @Test
    public void sendVerifyEMail() {

        EmailEvent emailEvent = new EmailEvent();
        emailEvent.setToEmail("1037322351@qq.com")
                .setSubject("this is subject")
                .setContent("this is content")
                .setType(23);

        mMailHandler.sendVerifyEMail(emailEvent);
    }
}