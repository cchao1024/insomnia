package com.cchao.sleep.json.user;

import lombok.Data;

/**
 * 登录、注册成功的响应
 * @author : cchao
 * @version 2019-02-18
 */
@Data
public class LoginResp {
    String token;
    String email;
    String nikeName;
    String avatar;
    int age;
}
