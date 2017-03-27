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
import com.eme.waterdelivery.model.bean.entity.PurchaseBo;
import com.eme.waterdelivery.model.bean.entity.PurchaseGoodBo;

import java.util.List;

/**
 * Created by dijiaoliang on 17/3/7.
 */
public class ApplyRecordAdapter extends BaseQuickAdapter<PurchaseBo, BaseViewHolder> {

    private LayoutInflater inflater;

    public ApplyRecordAdapter(Context context,List<PurchaseBo> data) {
        super(R.layout.item_apply_record, data);
        inflater=LayoutInflater.from(context);
    }

    @Override
    protected void convert(BaseViewHolder helper, PurchaseBo item) {
        helper.setText(R.id.tv_time_one, "申请时间:" + item.getCreateTime());
        helper.setText(R.id.tv_order_id, "订单号:" + item.getTrafficNo());
        helper.setText(R.id.tv_apply_people, "申请人:" + item.getApplicantName());
        helper.setText(R.id.tv_process_station, "处理站点:" + item.getStationName());
        helper.setText(R.id.tv_phone, item.getStationPhone());
        switch (item.getStatus()){
            case Constant.APPLY_RECORD_STATUS_UNHANDLE:
                helper.setText(R.id.tv_record_status, "待处理");
                break;
            case Constant.APPLY_RECORD_STATUS_DELIVERY:
                helper.setText(R.id.tv_record_status, "派送中");
                break;
            case Constant.APPLY_RECORD_STATUS_COMPLETE:
                helper.setText(R.id.tv_record_status, "已完结");
                break;
        }
        LinearLayout llContainer= (LinearLayout) helper.itemView.findViewById(R.id.ll_container);
        llContainer.removeAllViews();
        for(PurchaseGoodBo bean: item.getPurchaseGoods()){
            TextView tv= (TextView) inflater.inflate(R.layout.item_good,null,false);
            tv.setText(TextUtils.concat(bean.getCategoryName(),": ",bean.getGoodsName(), "    (", String.valueOf("0".equals(item.getStatus())?bean.getPreChangeAmount():bean.getChangeAmount()), bean.getUnitName(), ")"));
            llContainer.addView(tv);
        }
    }
}
