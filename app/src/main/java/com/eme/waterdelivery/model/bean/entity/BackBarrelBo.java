package com.eme.waterdelivery.model.bean.entity;

/**
 * 应缴空桶
 * Created by dijiaoliang on 17/6/7.
 */
public class BackBarrelBo {


    /**
     * goodsImage : http://xbzwater.com:8000/upload/img/goods/fa9e45b4e827977361be12b509f31b6a.jpg
     * goodsName : 5升空桶
     * relationGoodsId : 736389dc5889972e07c6b8f8662d7067
     * specName : 5升
     * storeId : 1002
     * totalRecoveryNum : 1
     */

    private String goodsImage;
    private String goodsName;
    private String relationGoodsId;
    private String specName;
    private String storeId;
    private int totalRecoveryNum;

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getRelationGoodsId() {
        return relationGoodsId;
    }

    public void setRelationGoodsId(String relationGoodsId) {
        this.relationGoodsId = relationGoodsId;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public int getTotalRecoveryNum() {
        return totalRecoveryNum;
    }

    public void setTotalRecoveryNum(int totalRecoveryNum) {
        this.totalRecoveryNum = totalRecoveryNum;
    }
}
