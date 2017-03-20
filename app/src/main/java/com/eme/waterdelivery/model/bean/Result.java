package com.eme.waterdelivery.model.bean;

/**
 * Created by dijiaoliang on 17/3/20.
 */

public class Result<T> {

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

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", data=" + data +
                '}';
    }
}
