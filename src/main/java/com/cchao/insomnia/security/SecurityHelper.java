package com.cchao.insomnia.security;

import com.cchao.insomnia.constant.Constant;
import org.apache.shiro.SecurityUtils;

/**
 * @author : cchao
 * @version 2019-02-19
 */
public class SecurityHelper {

    public static Long getUserId() {
        return (Long) SecurityUtils.getSubject().getSession().getAttribute(Constant.USER_ID);
    }
}
