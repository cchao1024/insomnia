package com.cchao.sleep.json;

import com.cchao.sleep.constant.Constant;

public class RespBean<T> {
    String code;
    String msg;
    T data;

    public static RespBean suc() {
        return new RespBean<>(Constant.Code.Suc, Constant.Msg.Success);
    }

    public static <T> RespBean<T> suc(T data) {
        return new RespBean<T>(Constant.Code.Suc, Constant.Msg.Success).setData(data);
    }

    public static RespBean of(String code, String msg) {
        return new RespBean<>(code, msg);
    }

    public static RespBean of(int code, String msg) {
        return new RespBean<>(String.valueOf(code), msg);
    }

    public static RespBean ofFail(String msg) {
        return new RespBean<>(Constant.Code.Fail, msg);
    }

    public static RespBean ofError(String msg) {
        return new RespBean<>(Constant.Code.Error, msg);
    }

    public static <T> RespBean<T> of(String code, String msg, T data) {
        return new RespBean<>(code, msg, data);
    }

    public RespBean(String code, String msg) {
        this.code = code;
        this.msg = msg;

    }

    public RespBean() {
        this(Constant.Code.Suc, "");
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

    public RespBean<T> setData(T data) {
        this.data = data;
        return this;
    }
}
