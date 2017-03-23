package com.eme.waterdelivery.model.bean;

/**
 * Created by dijiaoliang on 17/3/21.
 */

public class Result<T> {

    public boolean success;

    public Business<T> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Business<T> getData() {
        return data;
    }

    public void setData(Business<T> business) {
        this.data = business;
    }
}
