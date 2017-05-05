package com.eme.waterdelivery.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.model.bean.entity.WaterTicketBean;

import java.util.List;

/**
 * 售票页面
 *
 * Created by dijiaoliang on 17/3/7.
 */
public class SaleTicketAdapter extends BaseQuickAdapter<WaterTicketBean, BaseViewHolder> {

    private LayoutInflater inflater;

    public SaleTicketAdapter(Context context, List<WaterTicketBean>data) {
        super(R.layout.item_sale_ticket, data);
        inflater=LayoutInflater.from(context);
    }

    @Override
    protected void convert(BaseViewHolder helper, WaterTicketBean item) {
        helper.setText(R.id.tv_order_id, TextUtils.concat("水票面值: ￥",item.getPrice()));
        helper.setText(R.id.tv_show_cart_item_number,String.valueOf(item.getNumber()));
        helper.addOnClickListener(R.id.ll_close);
        helper.addOnClickListener(R.id.btn_add);
        helper.addOnClickListener(R.id.btn_reduce);
    }
}
