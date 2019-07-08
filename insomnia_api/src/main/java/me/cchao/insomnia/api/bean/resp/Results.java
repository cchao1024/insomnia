package me.cchao.insomnia.api.bean.resp;

import lombok.Getter;
import me.cchao.insomnia.api.util.I18nHelper;
import me.cchao.insomnia.common.ErrorMessage;

/**
 * @author : cchao
 * @version 2019-01-30
 */
@Getter
public enum Results implements ErrorMessage {

    // 0+ 通用异常
    SUC(0, i18n("RESULT.SUC")),
    ERROR(-1, i18n("RESULT.ERROR")),

    FAIL(1, i18n("RESULT.FAIL")),
    PARAM_ERROR(2, i18n("RESULT.PARAM_ERROR")),
    PARAM_EMPTY(3, i18n("RESULT.PARAM_EMPTY")),
    PRODUCT_NOT_EXIST(4, i18n("RESULT.PRODUCT_NOT_EXIST")),
    FILE_EMPTY(5, i18n("RESULT.FILE_EMPTY")),
    PARAM_MISSING(6, i18n("RESULT.PARAM_MISSING")),

    // 1000+ 用户
    UN_EXIST_USER(1001, i18n("RESULT.UN_EXIST_USER")),
    LOGIN_FAIL(1002, i18n("RESULT.LOGIN_FAIL")),
    EMAIL_EXIST(1003, i18n("RESULT.EMAIL_EXIST")),
    WISH_EXIST(1004, i18n("RESULT.WISH_EXIST")),
    WISH_SUC(1005, i18n("RESULT.WISH_SUC")),
    TOKEN_EXPIRED(1201, i18n("RESULT.TOKEN_EXPIRED")),

    // 4000+ 帖子
    UN_EXIST_POST(4010, i18n("RESULT.UN_EXIST_POST")),
    UN_EXIST_COMMENT(4020, i18n("RESULT.UN_EXIST_COMMENT")),
    UN_EXIST_REPLY(4030, i18n("RESULT.UN_EXIST_REPLY")),
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

    private static String i18n(String zh) {
        return I18nHelper.getMessage(zh);
    }
}
