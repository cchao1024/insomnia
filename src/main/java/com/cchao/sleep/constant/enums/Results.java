package com.cchao.sleep.constant.enums;

import lombok.Getter;

/**
 * @author : cchao
 * @version 2019-01-30
 */
@Getter
public enum Results {

    // 0+ 通用异常
    SUC(0, "请求成功"),
    FAIL(1, "请求失败"),
    PARAM_ERROR(2, "参数不正确"),
    PARAM_EMPTY(3, "参数为空"),
    PRODUCT_NOT_EXIST(4, "请求ID不存在"),

    // 1000+ 用户
    UN_EXIST_USER(1001, "用户不存在"),
    LOGIN_FAIL(1002, "登录失败,账号或密码不正确");


    private Integer code;

    private String message;

    Results(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Results msg(String msg) {
        this.message = msg;
        return this;
    }
}
