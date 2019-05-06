package me.cchao.insomnia.service;

import me.cchao.insomnia.dao.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;


/**
 * @author : cchao
 * @version 2019-02-13
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void saveUserInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("age", "2343");
        map.put("nickname", "kangkang");
        User user = userService.saveUserInfo(1, map);
        Assert.assertEquals(user.getNickName(), "kangkang");
    }
}