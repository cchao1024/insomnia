package com.cchao.sleep.json;

import com.cchao.sleep.constant.enums.Results;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RespBean<T> {
    int code;
    String msg;
    T data;

    public static RespBean suc() {
        return new RespBean<>(Results.SUC);
    }

    public static <T> RespBean<T> suc(T data) {
        return new RespBean<T>(Results.SUC).setData(data);
    }

    public static RespBean fail(Results results) {
        return new RespBean(results.getCode(),results.getMessage());
    }

    public static RespBean of(int code, String msg) {
        return new RespBean(code, msg);
    }

    public RespBean(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public RespBean(Results results) {
        this(results.getCode(), results.getMessage());
    }

    public RespBean<T> setData(T data) {
        this.data = data;
        return this;
    }
}
