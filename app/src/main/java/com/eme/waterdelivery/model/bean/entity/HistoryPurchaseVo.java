package com.eme.waterdelivery.model.bean.entity;

import java.util.List;

/**
 * Created by dijiaoliang on 17/3/24.
 */
public class HistoryPurchaseVo {

    private boolean hasMore;

    private List<PurchaseBo> list;
    /**
     * list : [{"applicantName":"乌鲁木齐市政府派送点","createTime":"2017-03-24 14:45:44","purchaseGoods":[{"categoryName":"冰川水","changeAmount":0,"goodsCommonName":"农夫山泉冰川水","goodsName":"test0","preChangeAmount":10,"specName":"5升/桶","unitName":"桶"},{"categoryName":"冰川水","changeAmount":0,"goodsCommonName":"怡宝冰川水","goodsName":"测试商品5","preChangeAmount":20,"specName":"5升/桶","unitName":"桶"}],"stationName":"","stationPhone":"","status":0,"trafficNo":"Y000_1490337944164"},{"applicantName":"乌鲁木齐市政府派送点","createTime":"2017-03-24 14:43:20","purchaseGoods":[{"categoryName":"冰川水","changeAmount":0,"goodsCommonName":"农夫山泉冰川水","goodsName":"test0","preChangeAmount":10,"specName":"5升/桶","unitName":"桶"},{"categoryName":"冰川水","changeAmount":0,"goodsCommonName":"怡宝冰川水","goodsName":"测试商品5","preChangeAmount":20,"specName":"5升/桶","unitName":"桶"}],"stationName":"","stationPhone":"","status":0,"trafficNo":"Y000_1490337796589"},{"applicantName":"乌鲁木齐市政府派送点","createTime":"2017-03-23 15:48:19","purchaseGoods":[{"categoryName":"冰川水","changeAmount":0,"goodsCommonName":"农夫山泉冰川水","goodsName":"test0","preChangeAmount":50,"specName":"5升/桶","unitName":"桶"},{"categoryName":"冰川水","changeAmount":0,"goodsCommonName":"怡宝冰川水","goodsName":"测试商品5","preChangeAmount":20,"specName":"5升/桶","unitName":"桶"}],"stationName":"乌鲁木齐第一水站","stationPhone":"1580018974","status":0,"trafficNo":"a20170323001"},{"applicantName":"乌鲁木齐市政府派送点","createTime":"2017-03-22 12:28:21","purchaseGoods":[{"categoryName":"冰川水","changeAmount":60,"goodsCommonName":"怡宝冰川水","goodsName":"测试商品5","preChangeAmount":60,"specName":"5升/桶","unitName":"桶"}],"stationName":"乌鲁木齐第一水站","stationPhone":"1580018974","status":1,"trafficNo":"a20170323002"},{"applicantName":"乌鲁木齐市政府派送点","createTime":"2017-03-16 13:11:44","purchaseGoods":[{"categoryName":"冰川水","changeAmount":70,"goodsCommonName":"怡宝冰川水","goodsName":"测试商品5","preChangeAmount":70,"specName":"5升/桶","unitName":"桶"}],"stationName":"乌鲁木齐第一水站","stationPhone":"1580018974","status":2,"trafficNo":"a20170323003"}]
     * purchaseHistoryOrderSum : {"purchaseHistoryOrderAllSum":5,"purchaseHistoryOrderDaySum":2,"purchaseHistoryOrderMonthSum":5}
     */

    private PurchaseHistoryOrderSumBean purchaseHistoryOrderSum;

    public PurchaseHistoryOrderSumBean getPurchaseHistoryOrderSum() {
        return purchaseHistoryOrderSum;
    }

    public void setPurchaseHistoryOrderSum(PurchaseHistoryOrderSumBean purchaseHistoryOrderSum) {
        this.purchaseHistoryOrderSum = purchaseHistoryOrderSum;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public List<PurchaseBo> getList() {
        return list;
    }

    public void setList(List<PurchaseBo> list) {
        this.list = list;
    }

    public static class PurchaseHistoryOrderSumBean {
        /**
         * purchaseHistoryOrderAllSum : 5
         * purchaseHistoryOrderDaySum : 2
         * purchaseHistoryOrderMonthSum : 5
         */

        private int purchaseHistoryOrderAllSum;
        private int purchaseHistoryOrderDaySum;
        private int purchaseHistoryOrderMonthSum;

        public int getPurchaseHistoryOrderAllSum() {
            return purchaseHistoryOrderAllSum;
        }

        public void setPurchaseHistoryOrderAllSum(int purchaseHistoryOrderAllSum) {
            this.purchaseHistoryOrderAllSum = purchaseHistoryOrderAllSum;
        }

        public int getPurchaseHistoryOrderDaySum() {
            return purchaseHistoryOrderDaySum;
        }

        public void setPurchaseHistoryOrderDaySum(int purchaseHistoryOrderDaySum) {
            this.purchaseHistoryOrderDaySum = purchaseHistoryOrderDaySum;
        }

        public int getPurchaseHistoryOrderMonthSum() {
            return purchaseHistoryOrderMonthSum;
        }

        public void setPurchaseHistoryOrderMonthSum(int purchaseHistoryOrderMonthSum) {
            this.purchaseHistoryOrderMonthSum = purchaseHistoryOrderMonthSum;
        }
    }
}
