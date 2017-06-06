package com.eme.waterdelivery.model.bean.entity;

import java.util.List;

/**
 *  欠款订单
 * Created by dijiaoliang on 17/6/6.
 */
public class ChaseOrderVo {


    /**
     * hasMore : false
     * pageNo : 1
     * pageSize : 10
     * params : {}
     * results : [{"address":"桂花大街250号","businessId":100000186,"createBy":5,"createTime":"2017-05-19 10:24:13","creditAmount":4,"creditType":1,"employeeName":"djl","id":10004,"memberId":1025,"overdueStatus":0,"phone":"15901267518","status":0,"storeId":1002,"storeName":"乌鲁木齐人民医院派送点"},{"address":"中国惠普大厦","businessId":100000192,"createBy":5,"createTime":"2017-05-19 10:23:34","creditAmount":4,"creditType":1,"employeeName":"djl","id":10003,"memberId":1006,"overdueStatus":0,"phone":"15901267518","status":0,"storeId":1002,"storeName":"乌鲁木齐人民医院派送点"},{"address":"中国惠普大厦","businessId":100000194,"createBy":5,"createTime":"2017-05-19 10:02:15","creditAmount":8,"creditType":1,"employeeName":"djl","id":10002,"memberId":1006,"overdueStatus":0,"phone":"15901267518","status":0,"storeId":1002,"storeName":"乌鲁木齐人民医院派送点"},{"address":"桂花大街250号","businessId":100000125,"createBy":5,"createTime":"2017-05-04 16:10:04","creditAmount":8,"creditType":1,"employeeName":"djl","id":10001,"memberId":1025,"overdueStatus":0,"phone":"15901267518","status":0,"storeId":1002,"storeName":"乌鲁木齐人民医院派送点"}]
     * sort : false
     * totalPage : 1
     * totalRecord : 4
     */

    private boolean hasMore;
    private int pageNo;
    private int pageSize;
    private int totalPage;
    private int totalRecord;
    private List<ResultsBean> results;

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * address : 桂花大街250号
         * businessId : 100000186
         * createBy : 5
         * createTime : 2017-05-19 10:24:13
         * creditAmount : 4
         * creditType : 1
         * employeeName : djl
         * id : 10004
         * memberId : 1025
         * overdueStatus : 0
         * phone : 15901267518
         * status : 0
         * storeId : 1002
         * storeName : 乌鲁木齐人民医院派送点
         */

        private String address;
        private String phone;
        private String id;
        private int creditType;
        private String creditAmount;
        private int status;
        private String createTime;
        private int memberId;
        private int storeId;
        private String storeName;

        private int businessId;
        private int createBy;
        private String employeeName;
        private int overdueStatus;


        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getBusinessId() {
            return businessId;
        }

        public void setBusinessId(int businessId) {
            this.businessId = businessId;
        }

        public int getCreateBy() {
            return createBy;
        }

        public void setCreateBy(int createBy) {
            this.createBy = createBy;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreditAmount() {
            return creditAmount;
        }

        public void setCreditAmount(String creditAmount) {
            this.creditAmount = creditAmount;
        }

        public int getCreditType() {
            return creditType;
        }

        public void setCreditType(int creditType) {
            this.creditType = creditType;
        }

        public String getEmployeeName() {
            return employeeName;
        }

        public void setEmployeeName(String employeeName) {
            this.employeeName = employeeName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public int getOverdueStatus() {
            return overdueStatus;
        }

        public void setOverdueStatus(int overdueStatus) {
            this.overdueStatus = overdueStatus;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getStoreId() {
            return storeId;
        }

        public void setStoreId(int storeId) {
            this.storeId = storeId;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }
    }
}
