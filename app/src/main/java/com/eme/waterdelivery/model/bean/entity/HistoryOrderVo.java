package com.eme.waterdelivery.model.bean.entity;

import java.util.List;

/**
 * Created by dijiaoliang on 17/3/22.
 */

public class HistoryOrderVo {

    private boolean hasMore;
    private List<WaitingOrderBo> list;
    private List<SellRecord> historySellSum;

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public List<WaitingOrderBo> getList() {
        return list;
    }

    public void setList(List<WaitingOrderBo> list) {
        this.list = list;
    }

    public List<SellRecord> getHistorySellSum() {
        return historySellSum;
    }

    public void setHistorySellSum(List<SellRecord> historySellSum) {
        this.historySellSum = historySellSum;
    }

    public class SellRecord{

        /**
         * categoryId : 3
         * categoryName : 瓜果
         * goodsSum : 1
         */

        private String categoryId;
        private String categoryName;
        private int goodsSum;

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public int getGoodsSum() {
            return goodsSum;
        }

        public void setGoodsSum(int goodsSum) {
            this.goodsSum = goodsSum;
        }
    }
}
