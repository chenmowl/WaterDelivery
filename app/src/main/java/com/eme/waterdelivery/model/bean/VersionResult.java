package com.eme.waterdelivery.model.bean;

/**
 * 校验版本的实体
 *
 * Created by dijiaoliang on 17/3/23.
 */
public class VersionResult {

    public boolean success;

    public String data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
