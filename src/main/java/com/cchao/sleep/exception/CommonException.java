package com.cchao.sleep.exception;

import com.cchao.sleep.constant.enums.Results;
import lombok.Getter;

/**
 * @author : cchao
 * @version 1/30/19
 */
@Getter
public class CommonException extends RuntimeException{

    private Integer code;

    public CommonException(Results results) {
        super(results.getMessage());

        this.code = results.getCode();
    }

    public CommonException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public static CommonException of(Results result) {
        return new CommonException(result);
    }
}
