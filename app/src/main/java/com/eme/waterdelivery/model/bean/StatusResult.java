package com.eme.waterdelivery.model.bean;

/**
 * Created by dijiaoliang on 17/3/23.
 */

public class StatusResult {

    public boolean success;

    public StatusBusiness data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public StatusBusiness getData() {
        return data;
    }

    public void setData(StatusBusiness data) {
        this.data = data;
    }
}
