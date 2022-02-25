package com.xiaogang.xxljobadminsdk.dto;

import java.io.Serializable;

public class ReturnT<T> implements Serializable {
    public static final long serialVersionUID = 42L;
    public static final int SUCCESS_CODE = 200;
    public static final int FAIL_CODE = 500;
    public static final ReturnT<String> SUCCESS = new ReturnT((Object)null);
    public static final ReturnT<String> FAIL = new ReturnT(500, (String)null);
    private int code;
    private String msg;
    private T content;

    public ReturnT() {
    }

    public ReturnT(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ReturnT(T content) {
        this.code = 200;
        this.content = content;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getContent() {
        return this.content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String toString() {
        return "ReturnT [code=" + this.code + ", msg=" + this.msg + ", content=" + this.content + "]";
    }
}

