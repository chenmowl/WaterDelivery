package com.eme.waterdelivery.model.bean.entity;

import java.util.List;

/**
 * 水站派送到派送点的运单
 *
 * Created by dijiaoliang on 17/6/5.
 */
public class TrafficVo {


    /**
     * pageNo : 1
     * pageSize : 10
     * params : {}
     * results : [{"createMemo":"auto","createTime":"2017-06-05 01:05:00","id":23,"pointId":1002,"pointName":"乌鲁木齐人民医院派送点","stationId":100,"stationName":"乌鲁木齐第一水站","status":0,"trafficNo":"Y001_1496595900063"},{"createMemo":"auto","createTime":"2017-06-04 01:05:00","id":22,"pointId":1002,"pointName":"乌鲁木齐人民医院派送点","stationId":100,"stationName":"乌鲁木齐第一水站","status":0,"trafficNo":"Y001_1496509500122"}]
     * totalPage : 1
     * totalRecord : 10
     */

    private int pageNo;
    private String pageSize;
    private int totalPage;
    private int totalRecord;
    private List<ResultsBean> results;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
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
         * createMemo : auto
         * createTime : 2017-06-05 01:05:00
         * id : 23
         * pointId : 1002
         * pointName : 乌鲁木齐人民医院派送点
         * stationId : 100
         * stationName : 乌鲁木齐第一水站
         * status : 0
         * trafficNo : Y001_1496595900063
         */

        private String createMemo;
        private String createTime;
        private int id;
        private int pointId;
        private String pointName;
        private int stationId;
        private String stationName;
        private int status;
        private String trafficNo;

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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPointId() {
            return pointId;
        }

        public void setPointId(int pointId) {
            this.pointId = pointId;
        }

        public String getPointName() {
            return pointName;
        }

        public void setPointName(String pointName) {
            this.pointName = pointName;
        }

        public int getStationId() {
            return stationId;
        }

        public void setStationId(int stationId) {
            this.stationId = stationId;
        }

        public String getStationName() {
            return stationName;
        }

        public void setStationName(String stationName) {
            this.stationName = stationName;
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
    }
}
