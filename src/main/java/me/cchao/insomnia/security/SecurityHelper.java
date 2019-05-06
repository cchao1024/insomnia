package me.cchao.insomnia.security;

import me.cchao.insomnia.dao.User;

import org.apache.shiro.SecurityUtils;


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
