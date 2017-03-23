package com.eme.waterdelivery.model.bean;

/**
 * Created by dijiaoliang on 17/3/21.
 */

public class Business<T> {

    private String code;
    private String message;
    private T info;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }
}
