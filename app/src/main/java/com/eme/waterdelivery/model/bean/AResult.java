package com.eme.waterdelivery.model.bean;

/**
 *
 * Created by dijiaoliang on 17/6/5.
 */
public class AResult<T> {

    public boolean success;

    public T data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
