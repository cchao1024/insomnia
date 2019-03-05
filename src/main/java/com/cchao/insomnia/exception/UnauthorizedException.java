package com.cchao.insomnia.exception;

/**
 * @author : cchao
 * @version 2019-01-31
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String msg) {
        super(msg);
    }

    public UnauthorizedException() {
        super();
    }
}