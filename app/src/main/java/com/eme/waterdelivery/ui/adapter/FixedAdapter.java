package com.eme.waterdelivery.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.model.bean.entity.WaitingOrderBo;
import com.eme.waterdelivery.tools.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * 固定订单
 * Created by dijiaoliang on 17/3/7.
 */
public class FixedAdapter extends BaseQuickAdapter<WaitingOrderBo, BaseViewHolder> {

    private LayoutInflater inflater;

    public FixedAdapter(Context context, List<WaitingOrderBo> data) {
        super(R.layout.item_fixed, data);
        inflater=LayoutInflater.from(context);
    }

    @Override
    protected void convert(BaseViewHolder helper, WaitingOrderBo item) {
        helper.addOnClickListener(R.id.btn_reject);
        helper.addOnClickListener(R.id.btn_order_detail);
        helper.setText(R.id.tv_time_one, (TextUtils.isEmpty(item.getCreateTime())?Constant.STR_EMPTY:item.getCreateTime()));
        helper.setText(R.id.tv_order_number, TextUtils.concat("订单号:",(TextUtils.isEmpty(item.getOrderId())?Constant.STR_EMPTY:item.getOrderId())));
        switch (item.getPayType()){
            case Constant.PAY_TYPE_MONEY:
                helper.setText(R.id.tv_pay_type, "现金");
                break;
            case Constant.PAY_TYPE_WEIXIN:
                helper.setText(R.id.tv_pay_type, "微信");
                break;
            default:
                helper.setText(R.id.tv_pay_type, "其它");
                break;
        }
        LinearLayout llContainer= (LinearLayout) helper.itemView.findViewById(R.id.ll_container);
        llContainer.removeAllViews();
        for(WaitingOrderBo.GoodsBean bean: item.getGoods()){
            TextView tv= (TextView) inflater.inflate(R.layout.item_good,null,false);
            tv.setText(TextUtils.concat(bean.getGoodsName(), "    (", String.valueOf(bean.getGoodsNum()), bean.getUnitName(), ")"));
            llContainer.addView(tv);
        }
        String time=item.getOrderShipperTime();
        if(TextUtils.isEmpty(time)){
            //为空是立即送达
            helper.setText(R.id.tv_delivery_time, "立即送达");
        }else{
            helper.setText(R.id.tv_delivery_time, TimeUtils.date2String(TimeUtils.string2Date(time),new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault())));
        }
        helper.setText(R.id.tv_receiver, TextUtils.concat("收件人: ",(TextUtils.isEmpty(item.getMemberName())?Constant.STR_EMPTY:item.getMemberName())));
        helper.setText(R.id.tv_address, TextUtils.concat("地址: ",(TextUtils.isEmpty(item.getMemberAddress())?Constant.STR_EMPTY:item.getMemberAddress())));
    }
}
