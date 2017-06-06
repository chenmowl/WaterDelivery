package com.eme.waterdelivery.model.bean.entity;

import java.util.List;

/**
 * 应缴水票
 * Created by dijiaoliang on 17/6/6.
 */
public class AssessTicketVo {


    private List<DayTicketsCountBean> dayTicketsCount;
    private List<TotalTicketsCountBean> totalTicketsCount;

    public List<DayTicketsCountBean> getDayTicketsCount() {
        return dayTicketsCount;
    }

    public void setDayTicketsCount(List<DayTicketsCountBean> dayTicketsCount) {
        this.dayTicketsCount = dayTicketsCount;
    }

    public List<TotalTicketsCountBean> getTotalTicketsCount() {
        return totalTicketsCount;
    }

    public void setTotalTicketsCount(List<TotalTicketsCountBean> totalTicketsCount) {
        this.totalTicketsCount = totalTicketsCount;
    }

    public static class DayTicketsCountBean {
        /**
         * createTime : 2017-06-05 14:06:15
         * status : 0
         * storeId : 1002
         * tiketsDayCount : [{"ticketsName":"冰川五加仑水票15元","totalCount":1}]
         */

        private String createTime;
        private int status;
        private int storeId;
        private List<TiketsDayCountBean> tiketsDayCount;

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

        public int getStoreId() {
            return storeId;
        }

        public void setStoreId(int storeId) {
            this.storeId = storeId;
        }

        public List<TiketsDayCountBean> getTiketsDayCount() {
            return tiketsDayCount;
        }

        public void setTiketsDayCount(List<TiketsDayCountBean> tiketsDayCount) {
            this.tiketsDayCount = tiketsDayCount;
        }

        public static class TiketsDayCountBean {
            /**
             * ticketsName : 冰川五加仑水票15元
             * totalCount : 1
             */

            private String ticketsName;
            private int totalCount;

            public String getTicketsName() {
                return ticketsName;
            }

            public void setTicketsName(String ticketsName) {
                this.ticketsName = ticketsName;
            }

            public int getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(int totalCount) {
                this.totalCount = totalCount;
            }
        }
    }

    public static class TotalTicketsCountBean {
        /**
         * ticketsName : 冰川五加仑水票15元
         * totalCount : 1
         */

        private String ticketsName;
        private int totalCount;

        public String getTicketsName() {
            return ticketsName;
        }

        public void setTicketsName(String ticketsName) {
            this.ticketsName = ticketsName;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }
    }
}
