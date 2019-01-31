package com.cchao.sleep.controller;

import com.cchao.sleep.authority.JWTUtil;
import com.cchao.sleep.dao.User;
import com.cchao.sleep.exception.UnauthorizedException;
import com.cchao.sleep.json.RespBean;
import com.cchao.sleep.service.UserService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService mUserService;


    /**
     * login
     *
     * @param name     name
     * @param password password
     * @return suc
     */
    @RequestMapping(value = "/login")
    public RespBean login(@RequestParam String name, @RequestParam String password) {
        User user = mUserService.findUserByName(name);

        if (user.getPassword().equals(password)) {
            return RespBean.suc(JWTUtil.sign(name, user.getId(), password));
        } else {
            throw new UnauthorizedException();
        }
    }

    @GetMapping("/require_auth")
    @RequiresAuthentication
    public RespBean requireAuth() {
        return new RespBean(200, "You are authenticated", null);
    }

    @GetMapping("/require_role")
    @RequiresRoles("admin")
    public RespBean requireRole() {
        return new RespBean(200, "You are visiting require_role", null);
    }

    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    public RespBean requirePermission() {
        return new RespBean(200, "You are visiting permission require edit,view", null);
    }

    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public RespBean unauthorized() {
        return new RespBean(401, "Unauthorized", null);
    }
}
