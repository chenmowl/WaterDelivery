package com.eme.waterdelivery.model.bean;

import com.eme.waterdelivery.model.bean.entity.HistoryOrderSumBo;
import com.eme.waterdelivery.model.bean.entity.HistoryOrderVo;

/**
 *  Rxjava zip
 *
 * Created by dijiaoliang on 17/3/22.
 */
public class HistoryOrderZip {
    private Result<HistoryOrderVo> waitingOrderVoResult;

    private Result<HistoryOrderSumBo>  orderSumBoResult;

    public HistoryOrderZip() {
    }

    public HistoryOrderZip(Result<HistoryOrderVo> waitingOrderVoResult, Result<HistoryOrderSumBo> orderSumBoResult) {
        this.waitingOrderVoResult = waitingOrderVoResult;
        this.orderSumBoResult = orderSumBoResult;
    }

    public Result<HistoryOrderSumBo> getOrderSumBoResult() {
        return orderSumBoResult;
    }

    public void setOrderSumBoResult(Result<HistoryOrderSumBo> orderSumBoResult) {
        this.orderSumBoResult = orderSumBoResult;
    }

    public Result<HistoryOrderVo> getWaitingOrderVoResult() {
        return waitingOrderVoResult;
    }

    public void setWaitingOrderVoResult(Result<HistoryOrderVo> waitingOrderVoResult) {
        this.waitingOrderVoResult = waitingOrderVoResult;
    }
}
