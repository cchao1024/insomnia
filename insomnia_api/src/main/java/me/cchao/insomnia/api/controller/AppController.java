package me.cchao.insomnia.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import me.cchao.insomnia.api.bean.resp.app.AppLaunch;
import me.cchao.insomnia.api.bean.resp.user.UpdateUser;
import me.cchao.insomnia.common.RespBean;
import me.cchao.insomnia.common.constant.Constant;
import me.cchao.insomnia.api.security.JWTUtil;
import me.cchao.insomnia.api.service.AppService;
import me.cchao.insomnia.api.service.UserService;
import me.cchao.insomnia.api.util.Logs;

/**
 * 移动端app controller
 *
 * @author : cchao
 * @version 2019-04-13
 */
@Controller
@RequestMapping("/app")
public class AppController {

    @Autowired
    UserService userService;
    @Autowired
    AppService mAppService;

    /**
     * 获取启动初始化
     *
     * @param request
     * @return
     */
    @RequestMapping("/getLaunch")
    @ResponseBody
    public RespBean<AppLaunch> getLaunch(HttpServletRequest request) {
        String token = JWTUtil.getToken(request);
        UpdateUser resp = null;
        Logs.println(token);
        // 没有token 就给他游客身份
        if (JWTUtil.haveToken(request)) {
            resp = userService.updateToken(token);
        } else {
            resp = userService.visitorSignup();
        }
        // token 过期
//        if(JWTUtil.verify())

        AppLaunch appLaunch = new AppLaunch();
        appLaunch.setLastAndroidVersion(Constant.ANDROID_LAST_VERSION)
            .setVersionUpdateMsg("empty")
            .setUserInfo(resp);
        return RespBean.suc(appLaunch);
    }

    /**
     * feedBack
     */
    @RequestMapping("/feed_back")
    @ResponseBody
    public RespBean getLaunch(String content, String email) {
        mAppService.feedBack(content, email);
        return RespBean.suc();
    }

    /**
     * about us
     */
    @RequestMapping("/about_us")
    public String getAboutUs() {
        return "about_us";
    }
}
