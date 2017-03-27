package com.eme.waterdelivery.model.bean.entity;

import java.util.List;

/**
 * Created by dijiaoliang on 17/3/24.
 */

public class ApplyTwoLevelGoodBo {


    /**
     * hasMore : false
     * list : [{"goodsCommonName":"农夫山泉冰川水","goodsId":"02ca9764a3f0a37398a06e2576b275e8","goodsName":"test0","specName":"5升/桶"},{"goodsCommonName":"怡宝冰川水","goodsId":"0677fa6f351596e745502d28e45fb9c6","goodsName":"测试商品5","specName":"5升/桶"}]
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
         * goodsCommonName : 农夫山泉冰川水
         * goodsId : 02ca9764a3f0a37398a06e2576b275e8
         * goodsName : test0
         * specName : 5升/桶
         */

        private String goodsCommonName;
        private String goodsId;
        private String goodsName;
        private String specName;
        private String unitName;

        //这个根据业务加的
        private int count;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getGoodsCommonName() {
            return goodsCommonName;
        }

        public void setGoodsCommonName(String goodsCommonName) {
            this.goodsCommonName = goodsCommonName;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getSpecName() {
            return specName;
        }

        public void setSpecName(String specName) {
            this.specName = specName;
        }

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }
    }
}
