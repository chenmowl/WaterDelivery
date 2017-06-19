package com.eme.waterdelivery.model.bean.entity;

import java.util.List;

/**
 *  运单详情
 * Created by dijiaoliang on 17/6/5.
 */
public class TrafficDetailVo {

    /**
     * driver : 测试
     * goods : [{"goodsId":"007db72b65f7d7d314b3e559b398fdb3","goodsImage":"http://xbzwater.com:8000/upload/img/goods/0a737baadeb76d7751c5477f19fc98c3.jpg","goodsName":"三加仑纯净水","preGoodsCount":136,"specName":"3升/桶"},{"goodsId":"e4724a51e6c08696b2b6b56196168b39","goodsImage":"http://xbzwater.com:8000/upload/img/goods/93f369b0ad3e42b190c31adfbe1729c1.jpg","goodsName":"五加仑纯净水","preGoodsCount":2,"specName":"5升/桶"}]
     * plateNumber : 新A-78890
     * status : 2
     * trafficNo : Y001_1496509500122
     */

    private String driver;
    private String plateNumber;
    private int status;
    private String trafficNo;
    private List<GoodsBean> goods;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTrafficNo() {
        return trafficNo;
    }

    public void setTrafficNo(String trafficNo) {
        this.trafficNo = trafficNo;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public static class GoodsBean {
        /**
         * goodsId : 007db72b65f7d7d314b3e559b398fdb3
         * goodsImage : http://xbzwater.com:8000/upload/img/goods/0a737baadeb76d7751c5477f19fc98c3.jpg
         * goodsName : 三加仑纯净水
         * goodsCount : 136
         * specName : 3升/桶
         */

        private String goodsId;
        private String goodsImage;
        private String goodsName;
        private int goodsCount;
        private String specName;

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
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

        public int getGoodsCount() {
            return goodsCount;
        }

        public void setGoodsCount(int goodsCount) {
            this.goodsCount = goodsCount;
        }

        public String getSpecName() {
            return specName;
        }

        public void setSpecName(String specName) {
            this.specName = specName;
        }
    }
}
