package com.eme.waterdelivery.model.bean.entity;

import java.util.List;

/**
 * 下拉选择水票接口（getTicketInfo）
 *
 * Created by dijiaoliang on 17/4/28.
 */
public class GetTicketInfoBo {


    /**
     * hasMore : false
     * list : [{"id":"1019","name":"冰川五加仑水票","price":20}]
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
         * id : 1019
         * name : 冰川五加仑水票
         * price : 20
         */

        private String id;
        private String name;
        private String price;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
