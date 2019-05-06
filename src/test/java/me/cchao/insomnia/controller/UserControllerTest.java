package me.cchao.insomnia.controller;

import me.cchao.insomnia.bean.req.user.UserLoginDTO;
import me.cchao.insomnia.bean.req.user.UserSignUpDTO;
import me.cchao.insomnia.bean.resp.RespBean;
import me.cchao.insomnia.constant.enums.Results;
import me.cchao.insomnia.util.Printer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

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

        UserLoginDTO params = new UserLoginDTO();
        params.setEmail(name);
        params.setPassword(password);
        RespBean respBean = userController.login(params);
        Printer.print("UserControllerTest#login", respBean.getMsg());
        Printer.print("UserControllerTest#login", respBean.getData());
        Assert.assertEquals(respBean.getCode(), (int) Results.SUC.getCode());
    }

    @Test
    public void signup() {
        UserSignUpDTO userSignUpDTO = new UserSignUpDTO();
        userSignUpDTO.setEmail("14234@qq.com")
            .setPassword("14234");

        RespBean respBean = userController.signUp(userSignUpDTO);
        Printer.print("UserControllerTest#signUp", respBean.getMsg());
        Printer.print("UserControllerTest#signUp", respBean.getData());
        Assert.assertEquals(respBean.getCode(), (int) Results.SUC.getCode());
    }
}