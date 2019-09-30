package me.cchao.mqhandler.bean;

import lombok.Getter;

/**
 * @author : cchao
 * @version 2019-01-30
 */
@Getter
public enum Results{

    SUC(0, "请求成功"),

    ERROR(-1, "系统异常"),
    // 0+ 通用异常
    FAIL(1, "请求失败"),
    PARAM_ERROR(2, "参数不正确"),
    PARAM_EMPTY(3, "参数为空"),
    PRODUCT_NOT_EXIST(4, "请求ID不存在"),
    FILE_EMPTY(5, "file is empty"),

    // 1000+ 用户
    UN_EXIST_USER(1001, "用户不存在"),
    LOGIN_FAIL(1002, "登录失败,账号或密码不正确"),
    EMAIL_EXIST(1003, "邮箱已注册"),
    CODE_NOT_EXIST(1004, "验证码不存在"),
    TOKEN_EXPIRED(1201, "token失效"),
    VERIFY_FAIL(1009, "邮箱验证失败,账号或密码不正确"),

    // 4000+ 帖子
    UN_EXIST_POST(4010, "该帖子不存在"),
    UN_EXIST_COMMENT(4020, "该评论不存在"),
    UN_EXIST_REPLY(4030, "该回复不存在"),
    ;


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
