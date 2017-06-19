package com.eme.waterdelivery.ui.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.model.bean.entity.AssessMoneyRecordVo;

import java.util.List;

/**
 * 应缴金额
 * <p>
 * Created by dijiaoliang on 17/3/7.
 */
public class AssessMoneyRecordAdapter extends BaseQuickAdapter<AssessMoneyRecordVo.ResultsBean, BaseViewHolder> {

    public AssessMoneyRecordAdapter(List<AssessMoneyRecordVo.ResultsBean> data) {
        super(R.layout.item_assess_money, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AssessMoneyRecordVo.ResultsBean item) {
//        helper.setText(R.id.tv_receiver, "收件人:"+item.getName());
        helper.setText(R.id.tv_time_one, TextUtils.concat("创建时间: ", TextUtils.isEmpty(item.getCreateTime()) ? Constant.STR_EMPTY : item.getCreateTime()));
        helper.setText(R.id.tv_order_number, TextUtils.concat("应缴金额: ", TextUtils.isEmpty(item.getStatementAmount()) ? Constant.STR_EMPTY : TextUtils.concat("￥", item.getStatementAmount())));
        switch (item.getStatus()) {
            case Constant.ASSESS_MONEY_0:
                helper.setText(R.id.tv_pay_type, "缴款状态: 待上缴");
                break;
            case Constant.ASSESS_MONEY_1:
                helper.setText(R.id.tv_pay_type, "缴款状态: 水站确认");
                break;
            case Constant.ASSESS_MONEY_2:
                helper.setText(R.id.tv_pay_type, "缴款状态: 已上缴");
                break;
            case Constant.ASSESS_MONEY_3:
                helper.setText(R.id.tv_pay_type, "缴款状态: 逾期拖欠");
                break;
            case Constant.ASSESS_MONEY_4:
                helper.setText(R.id.tv_pay_type, "缴款状态: 逾期上缴");
                break;
        }
    }
}
