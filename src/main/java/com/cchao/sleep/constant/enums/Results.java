package com.cchao.sleep.constant.enums;

import lombok.Getter;

/**
 * @author : cchao
 * @version 2019-01-30
 */
@Getter
public enum Results {

    SUC(0, "请求成功"),
    FAIL(1, "请求失败"),
    PARAM_ERROR(2, "参数不正确"),
    PARAM_EMPTY(3, "参数为空"),
    PRODUCT_NOT_EXIST(4, "请求ID不存在"),
    LOGIN_FAIL(30, "登录失败, 登录信息不正确");

    private Integer code;

    private String message;

    Results(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
