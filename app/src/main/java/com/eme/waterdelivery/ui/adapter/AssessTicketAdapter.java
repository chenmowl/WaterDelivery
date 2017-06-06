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
import com.eme.waterdelivery.model.bean.entity.AssessTicketVo;

import java.util.List;

/**
 * 应缴水票
 * <p>
 * Created by dijiaoliang on 17/3/7.
 */
public class AssessTicketAdapter extends BaseQuickAdapter<AssessTicketVo.DayTicketsCountBean, BaseViewHolder> {

    private LayoutInflater inflater;

    public AssessTicketAdapter(Context context, List<AssessTicketVo.DayTicketsCountBean> data) {
        super(R.layout.item_assess_ticket, data);
        inflater = LayoutInflater.from(context);
    }

    @Override
    protected void convert(BaseViewHolder helper, AssessTicketVo.DayTicketsCountBean item) {
//        helper.setText(R.id.tv_receiver, "收件人:"+item.getName());
        helper.setText(R.id.tv_time_one, TextUtils.concat("创建时间: ", TextUtils.isEmpty(item.getCreateTime()) ? Constant.STR_EMPTY : item.getCreateTime()));
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

        List<AssessTicketVo.DayTicketsCountBean.TiketsDayCountBean> tiketsDayCount = item.getTiketsDayCount();
        if (tiketsDayCount != null && tiketsDayCount.size() != 0) {
            LinearLayout ll_container = helper.getView(R.id.ll_container);
            ll_container.removeAllViews();
            for (AssessTicketVo.DayTicketsCountBean.TiketsDayCountBean tiketsDayCountBean : tiketsDayCount) {
                TextView tv = (TextView) inflater.inflate(R.layout.item_history_record, null, false);
                tv.setText(TextUtils.concat(tiketsDayCountBean.getTicketsName(), "   X", String.valueOf(tiketsDayCountBean.getTotalCount())));
                ll_container.addView(tv);
            }
        }
    }
}
