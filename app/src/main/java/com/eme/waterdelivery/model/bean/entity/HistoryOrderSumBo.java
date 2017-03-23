package com.eme.waterdelivery.model.bean.entity;

/**
 * Created by dijiaoliang on 17/3/23.
 */

public class HistoryOrderSumBo {


    /**
     * historyOrderAllSum : 1
     * historyOrderDaySum : 0
     * historyOrderMonthSum : 1
     */

    private int historyOrderAllSum;
    private int historyOrderDaySum;
    private int historyOrderMonthSum;

    public int getHistoryOrderAllSum() {
        return historyOrderAllSum;
    }

    public void setHistoryOrderAllSum(int historyOrderAllSum) {
        this.historyOrderAllSum = historyOrderAllSum;
    }

    public int getHistoryOrderDaySum() {
        return historyOrderDaySum;
    }

    public void setHistoryOrderDaySum(int historyOrderDaySum) {
        this.historyOrderDaySum = historyOrderDaySum;
    }

    public int getHistoryOrderMonthSum() {
        return historyOrderMonthSum;
    }

    public void setHistoryOrderMonthSum(int historyOrderMonthSum) {
        this.historyOrderMonthSum = historyOrderMonthSum;
    }
}
