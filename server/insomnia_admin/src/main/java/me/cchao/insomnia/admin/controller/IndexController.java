package me.cchao.insomnia.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author cchao
 * @version 2019-05-16.
 */
@Controller
@RequestMapping("admin")
public class IndexController {

    @GetMapping("/index")
    public ModelAndView list(Map<String, Object> map) {

        return new ModelAndView("index", map);
    }
}
