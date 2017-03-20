package com.eme.waterdelivery.model.bean.entity;

/**
 * Created by dijiaoliang on 17/3/20.
 */

public class LoginVo {

    private String code;
    private String message;
    private LoginInfo info;

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

    public LoginInfo getInfo() {
        return info;
    }

    public void setInfo(LoginInfo info) {
        this.info = info;
    }
}
