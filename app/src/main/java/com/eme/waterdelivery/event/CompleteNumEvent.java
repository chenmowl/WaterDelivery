package com.eme.waterdelivery.event;

/**
 * Created by dijiaoliang on 17/4/7.
 */

public class CompleteNumEvent {

    private String flag;
    private int sum;

    public CompleteNumEvent() {
    }

    public CompleteNumEvent(String flag, int sum) {
        this.flag = flag;
        this.sum = sum;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
