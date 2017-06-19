package com.eme.waterdelivery.model.bean.entity;

import java.util.List;

/**
 * Created by dijiaoliang on 17/6/7.
 */
public class AssessMoneyRecordVo {


    /**
     * hasMore : false
     * pageNo : 1
     * pageSize : 10
     * results : [{"createTime":"2017-06-05 14:06:15","statementAmount":150,"status":2,"storeId":1002}]
     * totalPage : 1
     * totalRecord : 1
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
         * createTime : 2017-06-05 14:06:15
         * statementAmount : 150
         * status : 2
         * storeId : 1002
         */

        private String createTime;
        private String statementAmount;
        private int status;
        private String storeId;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getStatementAmount() {
            return statementAmount;
        }

        public void setStatementAmount(String statementAmount) {
            this.statementAmount = statementAmount;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }
    }
}
