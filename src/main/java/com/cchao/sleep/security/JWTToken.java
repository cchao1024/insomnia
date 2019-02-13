package com.cchao.sleep.security;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author : cchao
 * @version 2019-01-31
 */
public class JWTToken implements AuthenticationToken {

    // 密钥
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
