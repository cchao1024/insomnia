package me.cchao.insomnia.api.bean.resp;

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

    public static RespBean suc(String msg) {
        return new RespBean<>(Results.SUC).setMsg(msg);
    }

    public static <T> RespBean<T> suc(T data) {
        return RespBean.suc(data, Results.SUC.getMessage());
    }

    public static <T> RespBean<T> suc(T data, String msg) {
        return new RespBean<T>(Results.SUC).setData(data).setMsg(msg);
    }

    public static RespBean fail(Results results) {
        return new RespBean(results.getCode(), results.getMessage());
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

    public RespBean<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
