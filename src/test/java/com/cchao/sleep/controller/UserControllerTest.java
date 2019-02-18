package com.cchao.sleep.controller;

import com.cchao.sleep.constant.enums.Results;
import com.cchao.sleep.json.RespBean;
import com.cchao.sleep.util.Printer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : cchao
 * @version 2019-02-13
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class UserControllerTest {

    @Autowired
    UserController userController;

    @Test
    public void login() {
        String name = "test1";
        String password = "test1_pwd";

        RespBean respBean = userController.login(name, password);
        Printer.print("UserControllerTest#login", respBean.getMsg());
        Printer.print("UserControllerTest#login", respBean.getData());
        Assert.assertEquals(respBean.getCode(), (int) Results.SUC.getCode());
    }

    @Test
    public void signup() {
        String name = "test2";
        String password = "test2_pwd";

        RespBean respBean = userController.signUp(name, password);
        Printer.print("UserControllerTest#signUp", respBean.getMsg());
        Printer.print("UserControllerTest#signUp", respBean.getData());
        Assert.assertEquals(respBean.getCode(), (int) Results.SUC.getCode());
    }
}