package com.cchao.insomnia.exception;

import lombok.Getter;

/**
 * @author : cchao
 * @version 1/30/19
 */
@Getter
public class CommonException extends RuntimeException{

    private Integer code;

    public CommonException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.code = errorMessage.getCode();
    }

    public CommonException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public static CommonException of(ErrorMessage errorMessage) {
        return new CommonException(errorMessage);
    }
}
