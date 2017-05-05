package com.eme.waterdelivery.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eme.waterdelivery.R;

import java.util.List;

/**
 * Created by dijiaoliang on 17/3/7.
 */
public class ChaseOrderAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private LayoutInflater inflater;

    public ChaseOrderAdapter(Context context, List<String>data) {
        super(R.layout.item_chase_order, data);
        inflater=LayoutInflater.from(context);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
//        helper.setText(R.id.tv_receiver, "收件人:"+item.getName());
    }
}
