package com.cchao.insomnia.model.javabean;

import com.cchao.insomnia.global.Constants;

/**
 * description 服务器响应的数据
 * author  cchao
 * date  2017/2/24
 **/
public class RespBean<T> {

    private String code;
    private T data;
    private String extend;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isCodeSuc() {
        return Integer.parseInt(code) == Integer.parseInt(Constants.ApiResp.CODE_SUC);
    }

    public boolean isCodeFail() {
        return !isCodeSuc();
    }
}
