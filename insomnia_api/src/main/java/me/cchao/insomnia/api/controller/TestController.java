package me.cchao.insomnia.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.cchao.insomnia.api.business.MQueueHandler;
import me.cchao.insomnia.common.RespBean;

/**
 * 测试 控制器
 *
 * @author cchao
 * @version 2019-06-20.
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    MQueueHandler mMQueueHandler;

    @RequestMapping("/rabbit")
    public RespBean testRabbit() {
        return RespBean.suc();
    }
}
