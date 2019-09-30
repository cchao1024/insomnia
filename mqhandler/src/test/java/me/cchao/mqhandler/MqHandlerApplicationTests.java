package me.cchao.mqhandler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MqHandlerApplicationTests {

    @Autowired
    GlobalConfig mConfig;

    @Test
    public void contextLoads() {
        System.out.println("_____" + mConfig.pushAppKey);
        System.out.println("_____" + mConfig.pushMasterSecret);
    }

}
