package com.eme.waterdelivery.model.bean.entity;

/**
 * 订单（待接单，派送中订单）数量接口
 * Created by dijiaoliang on 17/3/22.
 */
public class OrderSumBo {

    /**
     * distributingOrderSum : 2
     * waitingOrderSum : 1
     */

    private int distributingOrderSum;
    private int waitingOrderSum;

    public int getDistributingOrderSum() {
        return distributingOrderSum;
    }

    public void setDistributingOrderSum(int distributingOrderSum) {
        this.distributingOrderSum = distributingOrderSum;
    }

    public int getWaitingOrderSum() {
        return waitingOrderSum;
    }

    public void setWaitingOrderSum(int waitingOrderSum) {
        this.waitingOrderSum = waitingOrderSum;
    }
}
