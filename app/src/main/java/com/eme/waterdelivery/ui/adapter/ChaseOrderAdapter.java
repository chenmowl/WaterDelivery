package com.eme.waterdelivery.ui.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.model.bean.entity.ChaseOrderVo;

import java.util.List;

/**
 * 追欠订单
 * Created by dijiaoliang on 17/3/7.
 */
public class ChaseOrderAdapter extends BaseQuickAdapter<ChaseOrderVo.ResultsBean, BaseViewHolder> {


    public ChaseOrderAdapter(List<ChaseOrderVo.ResultsBean>data) {
        super(R.layout.item_chase_order, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChaseOrderVo.ResultsBean item) {
        helper.setText(R.id.tv_time_one, "欠款日期: "+ (TextUtils.isEmpty(item.getCreateTime())? Constant.STR_EMPTY:item.getCreateTime()));
        helper.setText(R.id.tv_order_number, "欠款单号: "+ (TextUtils.isEmpty(item.getId())? Constant.STR_EMPTY:item.getId()));
        switch (item.getCreditType()){
            case Constant.DEBT_REASON_1:
                helper.setText(R.id.tv_chase_reason, "欠款原因: 订单赊欠");
                break;
            case Constant.DEBT_REASON_2:
                helper.setText(R.id.tv_chase_reason, "欠款原因: 购票赊欠");
                break;
            case Constant.DEBT_REASON_3:
                helper.setText(R.id.tv_chase_reason, "欠款原因: 固定送水赊欠");
                break;
            case Constant.DEBT_REASON_4:
                helper.setText(R.id.tv_chase_reason, "欠款原因: 其他");
                break;
            default:
                helper.setText(R.id.tv_chase_reason, "欠款原因: ");
                break;
        }
        helper.setText(R.id.tv_chase_count, "欠款金额: "+(TextUtils.isEmpty(item.getCreditAmount())? Constant.STR_EMPTY:TextUtils.concat("￥",item.getCreditAmount())));
        helper.setText(R.id.tv_receiver, "欠款人: "+(TextUtils.isEmpty(item.getEmployeeName())? Constant.STR_EMPTY:item.getEmployeeName()));
        helper.setText(R.id.tv_phone, "欠款人电话: "+(TextUtils.isEmpty(item.getPhone())? Constant.STR_EMPTY:item.getPhone()));
        helper.setText(R.id.tv_address, "地址: "+(TextUtils.isEmpty(item.getAddress())? Constant.STR_EMPTY:item.getAddress()));
        helper.addOnClickListener(R.id.btn_finish);
    }
}
