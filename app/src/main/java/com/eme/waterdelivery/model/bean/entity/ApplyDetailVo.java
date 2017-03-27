package com.eme.waterdelivery.model.bean.entity;

import java.util.List;

/**
 * Created by dijiaoliang on 17/3/27.
 */

public class ApplyDetailVo {




    private List<PurchaseGoodBo> purchaseGoods;
    /**
     * confirmTime : null
     * createMemo : 我是备注2
     * createTime : 2017-03-22 12:28:21
     * sendTime : 2017-03-23 15:56:41
     * stationName : 乌鲁木齐第一水站
     * stationPhone : 1580018974
     * status : 1
     * trafficNo : a20170323002
     */

    private String confirmTime;
    private String createMemo;
    private String createTime;
    private String sendTime;
    private String stationName;
    private String stationPhone;
    private String status;
    private String trafficNo;


    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PurchaseGoodBo> getPurchaseGoods() {
        return purchaseGoods;
    }

    public void setPurchaseGoods(List<PurchaseGoodBo> purchaseGoods) {
        this.purchaseGoods = purchaseGoods;
    }

    public String getCreateMemo() {
        return createMemo;
    }

    public void setCreateMemo(String createMemo) {
        this.createMemo = createMemo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationPhone() {
        return stationPhone;
    }

    public void setStationPhone(String stationPhone) {
        this.stationPhone = stationPhone;
    }

    public String getTrafficNo() {
        return trafficNo;
    }

    public void setTrafficNo(String trafficNo) {
        this.trafficNo = trafficNo;
    }
}
