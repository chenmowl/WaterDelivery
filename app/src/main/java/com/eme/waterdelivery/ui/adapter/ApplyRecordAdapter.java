package com.eme.waterdelivery.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.model.bean.DelayBean;

import java.util.List;

/**
 * Created by dijiaoliang on 17/3/7.
 */
public class ApplyRecordAdapter extends BaseQuickAdapter<DelayBean, BaseViewHolder> {


    public ApplyRecordAdapter(List<DelayBean> data) {
        super(R.layout.item_apply_record, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DelayBean item) {
        helper.setText(R.id.tv_apply_people, "申请人:" + item.getName());
        switch (helper.getPosition()%3){
            case 0:
                helper.setText(R.id.tv_record_status, "未处理");
                break;
            case 1:
                helper.setText(R.id.tv_record_status, "货物派送中");
                break;
            case 2:
                helper.setText(R.id.tv_record_status, "已完成");
                break;
        }

    }
}
