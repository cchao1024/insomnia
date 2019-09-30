package me.cchao.insomnia.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import me.cchao.insomnia.api.bean.req.PageDTO;
import me.cchao.insomnia.api.controller.FileController;
import me.cchao.insomnia.api.domain.User;
import me.cchao.insomnia.api.service.UserService;
import me.cchao.insomnia.api.bean.resp.RespListBean;

/**
 * @author cchao
 * @version 2019-05-16.
 */
@Controller
@RequestMapping("admin/user")
public class AdminUserController {

    @Autowired
    UserService mUserService;
    @Autowired
    FileController mFileController;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size, Map<String, Object> map) {

        RespListBean<User> bean = mUserService.getUserByPage(PageDTO.of(page, size));

        map.put("data", bean.getData());
        map.put("listBean", bean);
        map.put("pageSize", size);
        return new ModelAndView("user/list", map);
    }

    @RequestMapping("/add")
    public ModelAndView add(Map<String, Object> map) {
        return new ModelAndView("image/add", map);
    }
}
