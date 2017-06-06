package com.eme.waterdelivery.model.bean;

/**
 *
 * Created by dijiaoliang on 17/6/5.
 */
public class AStatusResult<T> {

    public boolean success;

    public int data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
