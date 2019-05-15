package me.cchao.insomnia.api.security;

import org.apache.shiro.SecurityUtils;

import me.cchao.insomnia.api.domain.User;


/**
 * @author : cchao
 * @version 2019-02-19
 */
public class SecurityHelper {

    public static User getUser() {
        return (User) SecurityUtils.getSubject().getPrincipal();
    }

    public static Long getUserId() {
        return getUser().getId();
    }

    public static String getUserName() {
        return getUser().getNickName();
    }
}
