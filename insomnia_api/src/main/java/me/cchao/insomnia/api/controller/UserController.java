package me.cchao.insomnia.api.controller;

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
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import me.cchao.insomnia.api.bean.req.user.EditUserDTO;
import me.cchao.insomnia.api.bean.req.user.UserLoginDTO;
import me.cchao.insomnia.api.bean.resp.user.UpdateUser;
import me.cchao.insomnia.api.business.MQueueHandler;
import me.cchao.insomnia.api.security.JWTUtil;
import me.cchao.insomnia.api.service.UserService;
import me.cchao.insomnia.common.RespBean;
import me.cchao.insomnia.common.constant.Constant;

/**
 * The type User controller.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService mUserService;

    @Autowired
    private MQueueHandler mQueueHandler;

    /**
     * login
     */
    @RequestMapping(value = "/login")
    public RespBean<UpdateUser> login(@Valid UserLoginDTO params) {
        return RespBean.suc(mUserService.login(params)).setMsg("登录成功");
    }

    /**
     * 验证邮箱
     */
    @RequestMapping(value = "/email/send_verify")
    public RespBean sendVerify(@RequestParam String email) {
        mQueueHandler.pushVerifyEmailEvent(Constant.Email_TYPE.Verify_Email, email);
        return RespBean.suc("验证码已发送至邮箱[" + email + "]，10min内有效，请注意查收");
    }

    /**
     * 验证邮箱
     */
    @RequestMapping(value = "/email/verify")
    public RedirectView verifyEmail(@RequestParam String email, @RequestParam String code) {
        RedirectView red = new RedirectView("localhost:8081/email/verify?email=" + email + "&code=" + code, true);
        return red;
    }

    /**
     * updateUserInfo
     *
     * @param user        the map
     * @param httpRequest the http request
     * @return the resp me.cchao.insomnia.bean
     */
    @RequestMapping(value = "/update")
    @RequiresAuthentication
    public RespBean<UpdateUser> update(EditUserDTO user, HttpServletRequest httpRequest) {

        long id = JWTUtil.getUserId(httpRequest);
        UpdateUser result = mUserService.updateUserInfo(id, user);

        return RespBean.suc(result);
    }

    /**
     * Require auth resp me.cchao.insomnia.bean.
     *
     * @return the resp me.cchao.insomnia.bean
     */
    @GetMapping("/require_auth")
    @RequiresAuthentication
    public RespBean requireAuth() {
        return new RespBean(200, "You are authenticated");
    }

    /**
     * Require role resp me.cchao.insomnia.bean.
     *
     * @return the resp me.cchao.insomnia.bean
     */
    @GetMapping("/require_role")
    @RequiresRoles("admin")
    public RespBean requireRole() {
        return new RespBean(200, "You are visiting require_role");
    }

    /**
     * Require permission resp me.cchao.insomnia.bean.
     *
     * @return the resp me.cchao.insomnia.bean
     */
    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    public RespBean requirePermission() {
        return new RespBean(200, "You are visiting permission require edit,view");
    }

    /**
     * Unauthorized resp me.cchao.insomnia.bean.
     *
     * @return the resp me.cchao.insomnia.bean
     */
    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public RespBean unauthorized() {
        return new RespBean(401, "Unauthorized");
    }
}
