package com.eme.waterdelivery.model.bean.entity;

/**
 * Created by dijiaoliang on 17/3/24.
 */

public class PurchaseGoodBo {


    /**
     * categoryName : 冰川水
     * changeAmount : 0
     * goodsCommonName : 农夫山泉冰川水
     * goodsName : test0
     * preChangeAmount : 10
     * specName : 5升/桶
     * unitName : 桶
     */

    private String categoryName;
    private int changeAmount;
    private String goodsCommonName;
    private String goodsName;
    private int preChangeAmount;
    private String specName;
    private String unitName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(int changeAmount) {
        this.changeAmount = changeAmount;
    }

    public String getGoodsCommonName() {
        return goodsCommonName;
    }

    public void setGoodsCommonName(String goodsCommonName) {
        this.goodsCommonName = goodsCommonName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getPreChangeAmount() {
        return preChangeAmount;
    }

    public void setPreChangeAmount(int preChangeAmount) {
        this.preChangeAmount = preChangeAmount;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
