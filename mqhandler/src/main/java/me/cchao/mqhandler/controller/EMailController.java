package me.cchao.mqhandler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.cchao.mqhandler.bean.RespBean;
import me.cchao.mqhandler.service.EmailService;

/**
 * @author cchao
 * @version 2019-06-22.
 */
@RestController
@RequestMapping("/email")
public class EMailController {

    @Autowired
    EmailService mEmailService;

    @RequestMapping("/verify")
    public RespBean verify(@RequestParam String email, @RequestParam String code) {
        return mEmailService.verifyEmail(email, code);
    }

    @RequestMapping("/generate_code")
    public void generateCode(@RequestParam String email) {
        mEmailService.generateVerifyCode(email);
    }
}
