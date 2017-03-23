package com.eme.waterdelivery.model.bean.entity;

import java.util.List;

/**
 * Created by dijiaoliang on 17/3/22.
 */

public class WaitingOrderVo {

    private boolean hasMore;
    private List<WaitingOrderBo> list;

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
}
