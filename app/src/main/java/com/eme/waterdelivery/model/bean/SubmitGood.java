package com.eme.waterdelivery.model.bean;

/**
 *  提交订购单,用来组合json字符串
 * Created by dijiaoliang on 17/3/27.
 */
public class SubmitGood {

    private String goodsId;
    private int preChangeAmount;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public int getPreChangeAmount() {
        return preChangeAmount;
    }

    public void setPreChangeAmount(int preChangeAmount) {
        this.preChangeAmount = preChangeAmount;
    }
}
