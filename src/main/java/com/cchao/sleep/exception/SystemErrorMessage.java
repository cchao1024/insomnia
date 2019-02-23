package com.cchao.sleep.exception;

import lombok.Getter;

/**
 * 系统错误
 *
 * @author Jcxcc
 * @date 2019-02-23
 * @since 1.0
 */
@Getter
public enum SystemErrorMessage implements ErrorMessage {

    /**
     * 100000 用户相关
     */
    USER_NOT_EXISTED(100000, "用户不存在"),
    ;

    SystemErrorMessage(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;
    private String message;
}
