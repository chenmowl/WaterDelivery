package com.eme.waterdelivery.model.bean;

import com.eme.waterdelivery.model.bean.entity.OrderSumBo;
import com.eme.waterdelivery.model.bean.entity.WaitingOrderVo;

/**
 *  Rxjava zip
 *
 * Created by dijiaoliang on 17/3/22.
 */
public class WaitingBeanZip {
    private Result<WaitingOrderVo> waitingOrderVoResult;

    private Result<OrderSumBo>  orderSumBoResult;

    public WaitingBeanZip() {
    }

    public WaitingBeanZip(Result<WaitingOrderVo> waitingOrderVoResult, Result<OrderSumBo> orderSumBoResult) {
        this.waitingOrderVoResult = waitingOrderVoResult;
        this.orderSumBoResult = orderSumBoResult;
    }

    public Result<WaitingOrderVo> getWaitingOrderVoResult() {
        return waitingOrderVoResult;
    }

    public void setWaitingOrderVoResult(Result<WaitingOrderVo> waitingOrderVoResult) {
        this.waitingOrderVoResult = waitingOrderVoResult;
    }

    public Result<OrderSumBo> getOrderSumBoResult() {
        return orderSumBoResult;
    }

    public void setOrderSumBoResult(Result<OrderSumBo> orderSumBoResult) {
        this.orderSumBoResult = orderSumBoResult;
    }
}
