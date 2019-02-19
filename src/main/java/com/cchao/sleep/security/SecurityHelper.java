package com.cchao.sleep.security;

import com.cchao.sleep.constant.Constant;
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
