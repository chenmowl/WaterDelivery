package com.eme.waterdelivery.model.bean.entity;

import java.util.List;

/**
 * Created by dijiaoliang on 17/3/23.
 */

public class OrderDetailBo {


    /**
     * createTime : 2017-03-21 09:17:18
     * finnshedTime : 2017-03-22 11:23:02
     * goods : [{"goodsAmount":4,"goodsCommonName":"康帅傅纯净水","goodsImage":"http://xbzwater.com:8000/upload/img/goods/w4.jpg","goodsName":"测试商品三","goodsNum":1,"specName":"5升/桶","unitName":"桶"}]
     * memberAddress : 新疆巴依区老爷大院3#3#3
     * memberName : 小磊
     * memberPhone : 18614014250
     * orderAmount : 12
     * orderId : 201703200004
     * orderMessage : 你好，我是留言
     * orderShipperTime : 2017-03-21 11:00:00
     * orderState : 40
     * payType : 1
     * servicePhone : 18614099999
     * shippingTime : 2017-03-21 10:00:00
     * storeId : 5
     */

    private String createTime;
    private String finnshedTime;
    private String memberAddress;
    private String memberName;
    private String memberPhone;
    private String orderAmount;
    private String orderId;
    private String orderMessage;
    private String orderShipperTime;
    private String orderState;
    private String payType;
    private String servicePhone;
    private String shippingTime;
    private String storeId;
    private List<GoodsBean> goods;

    public String getCreateTime() {
        return createTime;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFinnshedTime() {
        return finnshedTime;
    }

    public void setFinnshedTime(String finnshedTime) {
        this.finnshedTime = finnshedTime;
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

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public String getOrderMessage() {
        return orderMessage;
    }

    public void setOrderMessage(String orderMessage) {
        this.orderMessage = orderMessage;
    }

    public String getOrderShipperTime() {
        return orderShipperTime;
    }

    public void setOrderShipperTime(String orderShipperTime) {
        this.orderShipperTime = orderShipperTime;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    public String getShippingTime() {
        return shippingTime;
    }

    public void setShippingTime(String shippingTime) {
        this.shippingTime = shippingTime;
    }


    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public static class GoodsBean {
        /**
         * goodsAmount : 4
         * goodsCommonName : 康帅傅纯净水
         * goodsImage : http://xbzwater.com:8000/upload/img/goods/w4.jpg
         * goodsName : 测试商品三
         * goodsNum : 1
         * specName : 5升/桶
         * unitName : 桶
         */

        private String goodsAmount;
        private String goodsCommonName;
        private String goodsImage;
        private String goodsName;
        private int goodsNum;
        private String specName;
        private String unitName;

        public String getGoodsAmount() {
            return goodsAmount;
        }

        public void setGoodsAmount(String goodsAmount) {
            this.goodsAmount = goodsAmount;
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
}
