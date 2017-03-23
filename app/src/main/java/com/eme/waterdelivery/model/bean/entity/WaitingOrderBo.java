package com.eme.waterdelivery.model.bean.entity;

import java.util.List;

/**
 * Created by dijiaoliang on 17/3/22.
 */
public class WaitingOrderBo {


    /**
     * createTime : 2017-03-20 13:55:19
     * finnshedTime : null
     * goods : [{"goodsCommonName":"康帅傅纯净水","goodsImage":"http://192.168.50.218/upload/img/goods/4d4c822159c3ff35f85f3eac20e45476.png","goodsName":"测试商品三","goodsNum":1}]
     * memberAddress : 新疆巴依区老爷大院3#3#3
     * memberName : 小磊
     * memberPhone : 18614014250
     * orderId : 201703200002
     * orderShipperTime : 2017-03-22 12:05:09
     * payType : 1
     * shippingTime : null
     * storeId : 5
     */

    private String createTime;
    private String finnshedTime;
    private String memberAddress;
    private String memberName;
    private String memberPhone;
    private String orderId;
    private String orderShipperTime;
    private String payType;
    private String shippingTime;
    private String storeId;
    private List<GoodsBean> goods;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMemberAddress() {
        return memberAddress;
    }

    public void setMemberAddress(String memberAddress) {
        this.memberAddress = memberAddress;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public String getFinnshedTime() {
        return finnshedTime;
    }

    public void setFinnshedTime(String finnshedTime) {
        this.finnshedTime = finnshedTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getShippingTime() {
        return shippingTime;
    }

    public void setShippingTime(String shippingTime) {
        this.shippingTime = shippingTime;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public String getOrderShipperTime() {
        return orderShipperTime;
    }

    public void setOrderShipperTime(String orderShipperTime) {
        this.orderShipperTime = orderShipperTime;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public static class GoodsBean {
        /**
         * goodsCommonName : 康帅傅纯净水
         * goodsImage : http://192.168.50.218/upload/img/goods/4d4c822159c3ff35f85f3eac20e45476.png
         * goodsName : 测试商品三
         * goodsNum : 1
         */

        private String goodsCommonName;
        private String goodsImage;
        private String goodsName;
        private int goodsNum;
        private String unitName;

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        public String getGoodsCommonName() {
            return goodsCommonName;
        }

        public void setGoodsCommonName(String goodsCommonName) {
            this.goodsCommonName = goodsCommonName;
        }

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

        public int getGoodsNum() {
            return goodsNum;
        }

        public void setGoodsNum(int goodsNum) {
            this.goodsNum = goodsNum;
        }
    }
}
