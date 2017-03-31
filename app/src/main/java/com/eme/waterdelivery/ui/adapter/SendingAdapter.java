package com.eme.waterdelivery.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.model.bean.entity.WaitingOrderBo;
import com.eme.waterdelivery.tools.ConstUtils;
import com.eme.waterdelivery.tools.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by dijiaoliang on 17/3/7.
 */
public class SendingAdapter extends BaseQuickAdapter<WaitingOrderBo, BaseViewHolder> {

    private LayoutInflater inflater;

    public SendingAdapter(Context context, List<WaitingOrderBo> data) {
        super(R.layout.item_sending, data);
        inflater = LayoutInflater.from(context);
    }

    @Override
    protected void convert(BaseViewHolder helper, WaitingOrderBo item) {
        helper.addOnClickListener(R.id.btn_call);
        helper.setText(R.id.tv_time_one, "下单时间:" + item.getCreateTime());
        helper.setText(R.id.tv_order_number, "订单号:" + item.getOrderId());
        LinearLayout llContainer = (LinearLayout) helper.itemView.findViewById(R.id.ll_container);
        llContainer.removeAllViews();
        for (WaitingOrderBo.GoodsBean bean : item.getGoods()) {
            TextView tv = (TextView) inflater.inflate(R.layout.item_good, null, false);
            tv.setText(TextUtils.concat(bean.getGoodsName(), "    (", String.valueOf(bean.getGoodsNum()), bean.getUnitName(), ")"));
            llContainer.addView(tv);
        }
        String time = item.getOrderShipperTime();
        if (TextUtils.isEmpty(time)) {
            //为空是立即送达
            helper.setText(R.id.tv_delivery_type, "立即送达");
            helper.setText(R.id.tv_title_time, "已用时间");
            if (!TextUtils.isEmpty(item.getShippingTime())) {
                helper.setText(R.id.tv_delivery_time, TimeUtils.getIntervalTime(TimeUtils.getCurTimeString(), item.getShippingTime(), ConstUtils.TimeUnit.MIN) + "分钟");
            }
        } else {
            helper.setText(R.id.tv_delivery_type, TimeUtils.date2String(TimeUtils.string2Date(time), new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault())));
            helper.setText(R.id.tv_title_time, "剩余时间");
            if (!TextUtils.isEmpty(item.getOrderShipperTime())) {
                if (TimeUtils.string2Milliseconds(item.getOrderShipperTime()) - TimeUtils.getCurTimeMills() > 0) {
                    helper.setText(R.id.tv_delivery_time, TimeUtils.getIntervalByNow(item.getOrderShipperTime(), ConstUtils.TimeUnit.MIN) + "分钟");
                } else {
                    helper.setText(R.id.tv_delivery_time, "0分钟");
                }
            }
        }
        helper.setText(R.id.tv_receiver, "收件人: " + item.getMemberName());
        helper.setText(R.id.tv_address, "地址: " + item.getMemberAddress());
    }
}
