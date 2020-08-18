package com.wsl.networkrequestmvp.base.mvp;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * File descripition:   mode基类
 */
public class BaseModel<T> implements Serializable {
    //兼容 msg、message 字段
    @SerializedName(value = "message", alternate = {"msg"})
    private String message;
    private int code;
    private T data;

    public BaseModel(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "code=" + code +
                ", msg='" + message + '\'' +
                ", result=" + data +
                '}';
    }
}
