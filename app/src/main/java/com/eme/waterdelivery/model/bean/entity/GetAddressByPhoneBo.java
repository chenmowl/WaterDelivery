package com.eme.waterdelivery.model.bean.entity;

import java.util.List;

/**
 * 根据电话检索地址信息（getAddressByPhone）
 *
 * Created by dijiaoliang on 17/4/28.
 */
public class GetAddressByPhoneBo {


    /**
     * hasMore : false
     * list : [{"address":"i am address","id":103,"trueName":"nick"}]
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
         * address : i am address
         * id : 103
         * trueName : nick
         */

        private String address;
        private String id;
        private String trueName;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTrueName() {
            return trueName;
        }

        public void setTrueName(String trueName) {
            this.trueName = trueName;
        }
    }
}
