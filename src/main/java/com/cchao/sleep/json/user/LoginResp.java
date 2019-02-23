package com.cchao.sleep.json.user;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 登录、注册成功的响应
 * @author : cchao
 * @version 2019-02-18
 */
@Data
@Accessors(chain = true)
public class LoginResp {
    String token;
    String email;
    String nikeName;
    String avatar;
    int age;
}
