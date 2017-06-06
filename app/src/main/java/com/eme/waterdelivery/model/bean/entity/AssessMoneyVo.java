package com.eme.waterdelivery.model.bean.entity;

import java.util.List;

/**
 * 应缴金额
 * Created by dijiaoliang on 17/6/6.
 */
public class AssessMoneyVo {


    /**
     * dayStatements : [{"createTime":"2017-06-05 14:06:15","statementAmount":150,"status":0,"storeId":1002}]
     * totalAmount : 150
     */

    private String totalAmount;
    private List<DayStatementsBean> dayStatements;

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<DayStatementsBean> getDayStatements() {
        return dayStatements;
    }

    public void setDayStatements(List<DayStatementsBean> dayStatements) {
        this.dayStatements = dayStatements;
    }

    public static class DayStatementsBean {
        /**
         * createTime : 2017-06-05 14:06:15
         * statementAmount : 150
         * status : 0
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


        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getStatementAmount() {
            return statementAmount;
        }

        public void setStatementAmount(String statementAmount) {
            this.statementAmount = statementAmount;
        }

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }
    }
}
