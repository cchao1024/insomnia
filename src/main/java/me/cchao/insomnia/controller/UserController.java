package me.cchao.insomnia.controller;

import me.cchao.insomnia.bean.req.user.UserLoginDTO;
import me.cchao.insomnia.bean.req.user.UserSignUpDTO;
import me.cchao.insomnia.bean.resp.RespBean;
import me.cchao.insomnia.dao.User;
import me.cchao.insomnia.security.JWTUtil;
import me.cchao.insomnia.service.UserService;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * The type User controller.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService mUserService;

    /**
     * login
     */
    @RequestMapping(value = "/login")
    public RespBean login(@Valid UserLoginDTO params) {
        return RespBean.suc(mUserService.login(params)).setMsg("登录成功");
    }

    /**
     * signUp
     */
    @RequestMapping(value = "/signup")
    public RespBean signUp(@Valid UserSignUpDTO params) {
        return RespBean.suc(mUserService.signup(params)).setMsg("注册成功");
    }

    /**
     * updateUserInfo
     *
     * @param map         the map
     * @param httpRequest the http request
     * @return the resp bean
     */
    @RequestMapping(value = "/update")
    @RequiresAuthentication
    public RespBean update(@RequestParam Map<String, String> map, HttpServletRequest httpRequest) {

        long id = JWTUtil.getUserId(httpRequest);
        User user = mUserService.saveUserInfo(id, map);
        return RespBean.suc(user);
    }

    /**
     * Require auth resp bean.
     *
     * @return the resp bean
     */
    @GetMapping("/require_auth")
    @RequiresAuthentication
    public RespBean requireAuth() {
        return new RespBean(200, "You are authenticated");
    }

    /**
     * Require role resp bean.
     *
     * @return the resp bean
     */
    @GetMapping("/require_role")
    @RequiresRoles("admin")
    public RespBean requireRole() {
        return new RespBean(200, "You are visiting require_role");
    }

    /**
     * Require permission resp bean.
     *
     * @return the resp bean
     */
    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    public RespBean requirePermission() {
        return new RespBean(200, "You are visiting permission require edit,view");
    }

    /**
     * Unauthorized resp bean.
     *
     * @return the resp bean
     */
    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public RespBean unauthorized() {
        return new RespBean(401, "Unauthorized");
    }
}
