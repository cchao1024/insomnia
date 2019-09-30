package me.cchao.mqhandler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cchao
 * @version 2019-06-21.
 */
@RestController
@RequestMapping("/")
public class IndexController {

    @RequestMapping("index")
    public String index() {
        return "index";
    }
}
