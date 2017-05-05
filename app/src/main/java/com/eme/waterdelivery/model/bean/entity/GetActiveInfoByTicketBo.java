package com.eme.waterdelivery.model.bean.entity;

import java.util.List;

/**
 * 获取购票活动信息（getActiveInfoByTicket）
 *
 * Created by dijiaoliang on 17/4/28.
 */
public class GetActiveInfoByTicketBo {

    /**
     * hasMore : false
     * list : [{"activeName":"购票赠桶","giftGoodsName":"六加仑水桶","giftGoodsNumber":1,"ticketName":"冰川五加仑水票20","ticketNumber":10},{"activeName":"购票赠桶","giftGoodsName":"六加仑水桶","giftGoodsNumber":1,"ticketName":"冰纯六加仑水票24","ticketNumber":12}]
     */

    private boolean hasMore;
    private List<ListBean> list;

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * activeName : 购票赠桶
         * giftGoodsName : 六加仑水桶
         * giftGoodsNumber : 1
         * ticketName : 冰川五加仑水票20
         * ticketNumber : 10
         */

        private String activeName;
        private String giftGoodsName;
        private int giftGoodsNumber;
        private String ticketName;
        private int ticketNumber;

        public String getActiveName() {
            return activeName;
        }

        public void setActiveName(String activeName) {
            this.activeName = activeName;
        }

        public String getGiftGoodsName() {
            return giftGoodsName;
        }

        public void setGiftGoodsName(String giftGoodsName) {
            this.giftGoodsName = giftGoodsName;
        }

        public int getGiftGoodsNumber() {
            return giftGoodsNumber;
        }

        public void setGiftGoodsNumber(int giftGoodsNumber) {
            this.giftGoodsNumber = giftGoodsNumber;
        }

        public String getTicketName() {
            return ticketName;
        }

        public void setTicketName(String ticketName) {
            this.ticketName = ticketName;
        }

        public int getTicketNumber() {
            return ticketNumber;
        }

        public void setTicketNumber(int ticketNumber) {
            this.ticketNumber = ticketNumber;
        }
    }
}
