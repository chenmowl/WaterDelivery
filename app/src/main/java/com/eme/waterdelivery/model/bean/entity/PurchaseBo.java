package com.eme.waterdelivery.model.bean.entity;

import java.util.List;

/**
 * Created by dijiaoliang on 17/3/24.
 */

public class PurchaseBo {

    /**
     * applicantName : 乌鲁木齐市政府派送点
     * createTime : 2017-03-24 14:45:44
     * stationName :
     * stationPhone :
     * status : 0
     * trafficNo : Y000_1490337944164
     */

    private String applicantName;
    private String createTime;
    private String stationName;
    private String stationPhone;
    private String status;
    private String trafficNo;
    private List<PurchaseGoodBo> purchaseGoods;


    public List<PurchaseGoodBo> getPurchaseGoods() {
        return purchaseGoods;
    }

    public void setPurchaseGoods(List<PurchaseGoodBo> purchaseGoods) {
        this.purchaseGoods = purchaseGoods;
    }


    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTrafficNo() {
        return trafficNo;
    }

    public void setTrafficNo(String trafficNo) {
        this.trafficNo = trafficNo;
    }
}
