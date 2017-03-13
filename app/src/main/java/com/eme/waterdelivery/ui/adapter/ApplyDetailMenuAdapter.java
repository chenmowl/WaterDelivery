package com.eme.waterdelivery.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eme.waterdelivery.R;

import java.util.List;

/**
 * Created by dijiaoliang on 17/3/7.
 */
public class ApplyDetailMenuAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public ApplyDetailMenuAdapter(List<String> data) {
        super(R.layout.item_apply_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
//        helper.addOnClickListener(R.id.rl_delete);
    }
}
