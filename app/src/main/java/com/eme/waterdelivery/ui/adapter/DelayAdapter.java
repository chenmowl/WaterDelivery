package com.eme.waterdelivery.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.model.bean.DelayBean;

import java.util.List;

/**
 * Created by dijiaoliang on 17/3/7.
 */
public class DelayAdapter extends BaseQuickAdapter<DelayBean, BaseViewHolder> {


    public DelayAdapter(List<DelayBean> data) {
        super(R.layout.item_delay, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DelayBean item) {
        helper.addOnClickListener(R.id.btn_receiving);
        helper.setText(R.id.tv_receiver, "收件人:"+item.getName());
    }
}
