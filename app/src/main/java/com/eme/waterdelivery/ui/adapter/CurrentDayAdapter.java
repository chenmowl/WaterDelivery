package com.eme.waterdelivery.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.model.bean.entity.WaitingOrderBo;

import java.util.List;

/**
 * Created by dijiaoliang on 17/3/7.
 */
public class CurrentDayAdapter extends BaseQuickAdapter<WaitingOrderBo, BaseViewHolder> {

    private LayoutInflater inflater;

    public CurrentDayAdapter(Context context,List<WaitingOrderBo>data) {
        super(R.layout.item_current_day, data);
        inflater=LayoutInflater.from(context);
    }

    @Override
    protected void convert(BaseViewHolder helper, WaitingOrderBo item) {
//        helper.setText(R.id.tv_receiver, "收件人:"+item.getName());
    }
}
