package com.eme.waterdelivery.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.model.bean.entity.GetAddressByPhoneBo;

import java.util.List;

/**
 * 搜索常用地址
 *
 * Created by dijiaoliang on 17/3/7.
 */
public class CommonAddressAdapter extends BaseQuickAdapter<GetAddressByPhoneBo.ListBean, BaseViewHolder> {

    private LayoutInflater inflater;

    public CommonAddressAdapter(Context context, List<GetAddressByPhoneBo.ListBean>data) {
        super(R.layout.item_spinner_apply, data);
        inflater=LayoutInflater.from(context);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetAddressByPhoneBo.ListBean item) {
//        helper.setText(R.id.tv_receiver, "收件人:"+item.getName());
        helper.setText(R.id.tv_content, item.getAddress());
    }
}
