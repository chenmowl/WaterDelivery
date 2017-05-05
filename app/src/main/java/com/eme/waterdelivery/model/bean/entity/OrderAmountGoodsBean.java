package com.eme.waterdelivery.model.bean.entity;

/**
 * 计算价格（calculationPayAmount）
 *
 * Created by dijiaoliang on 17/5/4.
 */
public class OrderAmountGoodsBean {

    private String goodsId;//商品id
    private int goodsNumber;//商品数量
    private int ticketUsedCount;//水票数量
    private int mortgageBucketCount;//押桶数
    private int sellBucketCount;//售桶数

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public int getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(int goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public int getTicketUsedCount() {
        return ticketUsedCount;
    }

    public void setTicketUsedCount(int ticketUsedCount) {
        this.ticketUsedCount = ticketUsedCount;
    }

    public int getMortgageBucketCount() {
        return mortgageBucketCount;
    }

    public void setMortgageBucketCount(int mortgageBucketCount) {
        this.mortgageBucketCount = mortgageBucketCount;
    }

    public int getSellBucketCount() {
        return sellBucketCount;
    }

    public void setSellBucketCount(int sellBucketCount) {
        this.sellBucketCount = sellBucketCount;
    }
}
