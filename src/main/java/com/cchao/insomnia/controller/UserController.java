package com.cchao.insomnia.controller;

import com.cchao.insomnia.bean.req.UserLoginDTO;
import com.cchao.insomnia.dao.User;
import com.cchao.insomnia.json.RespBean;
import com.cchao.insomnia.json.user.LoginResp;
import com.cchao.insomnia.security.JWTUtil;
import com.cchao.insomnia.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * The type User controller.
 */
@Api(tags = "用户接口", description = "用户相关接口")
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService mUserService;

    /**
     * login
     *
     * @param params the params
     * @return suc resp bean
     */
    @ApiOperation(value = "用户登录", notes = "用户登录接口")
    @PostMapping("/login")
    public RespBean login(@Valid @RequestBody UserLoginDTO params) {
        return RespBean.suc(mUserService.login(params)).setMsg("登录成功");
    }

    /**
     * signUp
     *
     * @param email    name
     * @param password password
     * @return the resp bean
     */
    @RequestMapping(value = "/signup")
    public RespBean signUp(@RequestParam String email, @RequestParam String password) {
        //FIXME 业务下沉到service层
        User user = mUserService.signUp(email, password);
        String token = JWTUtil.sign(email, user.getId(), password);

        LoginResp loginResp = new LoginResp();
        loginResp.setAge(user.getAge());
        loginResp.setAvatar(user.getNickname());
        loginResp.setEmail(user.getEmail());
        loginResp.setToken(token);

        return RespBean.suc(loginResp).setMsg("注册成功");
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

        String name = JWTUtil.getUsername(httpRequest);
        User user = mUserService.saveUserInfo(name, map);
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
        return new RespBean(200, "You are authenticated", null);
    }

    /**
     * Require role resp bean.
     *
     * @return the resp bean
     */
    @GetMapping("/require_role")
    @RequiresRoles("admin")
    public RespBean requireRole() {
        return new RespBean(200, "You are visiting require_role", null);
    }

    /**
     * Require permission resp bean.
     *
     * @return the resp bean
     */
    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    public RespBean requirePermission() {
        return new RespBean(200, "You are visiting permission require edit,view", null);
    }

    /**
     * Unauthorized resp bean.
     *
     * @return the resp bean
     */
    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public RespBean unauthorized() {
        return new RespBean(401, "Unauthorized", null);
    }
}
