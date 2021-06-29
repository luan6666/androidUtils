package com.smz.lexunuser.net;

/**
 * @Author ldx
 * @CreateDate 2020/8/25 14:49
 * @Description 统一的返回类
 */
public class BaseRes<T> {
    public String msg;
    public int code;
    public T result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return result;
    }

    public void setData(T data) {
        this.result = data;
    }

    public boolean isCodeInvalid() {
        //Constant.RES_OK 后台协议的网络请求正常状态值
        return code != 200;
    }
}
