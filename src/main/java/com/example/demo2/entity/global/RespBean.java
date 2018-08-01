package com.example.demo2.entity.global;

import com.example.demo2.Constant;

public class RespBean<T> {
    String code;
    String msg;
    T data;

    public static RespBean of(String code, String msg) {
        return new RespBean<>(code, msg);
    }

    public static <T> RespBean<T> of(String code, String msg, T data) {
        return new RespBean<>(code, msg, data);
    }

    public RespBean(String code, String msg) {
        this.code = code;
        this.msg = msg;

    }

    public RespBean() {
        this(Constant.RespCode.Suc, "");
    }

    public RespBean(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
