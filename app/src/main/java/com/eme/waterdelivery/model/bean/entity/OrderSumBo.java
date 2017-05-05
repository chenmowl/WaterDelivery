package com.eme.waterdelivery.model.bean.entity;

/**
 * 订单（待接单，派送中订单）数量接口
 * Created by dijiaoliang on 17/3/22.
 */
public class OrderSumBo {


    /**
     * distributingOrderSum : 1
     * fixedOrderSum : 1
     * waitingOrderSum : 5
     */

    private int distributingOrderSum;
    private int fixedOrderSum;
    private int waitingOrderSum;

    public int getDistributingOrderSum() {
        return distributingOrderSum;
    }

    public void setDistributingOrderSum(int distributingOrderSum) {
        this.distributingOrderSum = distributingOrderSum;
    }

    public int getFixedOrderSum() {
        return fixedOrderSum;
    }

    public void setFixedOrderSum(int fixedOrderSum) {
        this.fixedOrderSum = fixedOrderSum;
    }

    public int getWaitingOrderSum() {
        return waitingOrderSum;
    }

    public void setWaitingOrderSum(int waitingOrderSum) {
        this.waitingOrderSum = waitingOrderSum;
    }
}
