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
import com.eme.waterdelivery.model.bean.entity.SaleTicketRecordBo;

import java.util.List;

/**
 * 售票记录
 *
 * Created by dijiaoliang on 17/3/7.
 */
public class SaleTicketRecordAdapter extends BaseQuickAdapter<SaleTicketRecordBo.ListBean, BaseViewHolder> {

    private LayoutInflater inflater;

    public SaleTicketRecordAdapter(Context context, List<SaleTicketRecordBo.ListBean>data) {
        super(R.layout.item_sale_ticket_record, data);
        inflater=LayoutInflater.from(context);
    }

    @Override
    protected void convert(BaseViewHolder helper, SaleTicketRecordBo.ListBean item) {
        helper.setText(R.id.tv_time_one, TextUtils.concat("下单时间: ",item.getCreateTime()));
        helper.setText(R.id.tv_order_number,TextUtils.concat("订单号: ",item.getId()));
        String payMode= Constant.STR_EMPTY;
        switch (item.getPayType()){
            case Constant.PAY_TYPE_MONEY:
                payMode="现金: ￥";
                break;
            case Constant.PAY_TYPE_WEIXIN:
                payMode="微信: ￥";
                break;
        }
        helper.setText(R.id.tv_pay_mode,TextUtils.concat(payMode,item.getAmount()));
        LinearLayout linearLayout=helper.getView(R.id.ll_container);
        linearLayout.removeAllViews();
        List<SaleTicketRecordBo.ListBean.TicketBean> ticket =item.getTicket();
        if(ticket!=null && ticket.size()>0){
            for(SaleTicketRecordBo.ListBean.TicketBean bean: ticket){
                TextView textView = (TextView) inflater.inflate(R.layout.item_good,null,false);
                textView.setText(TextUtils.concat(bean.getName(),": ",bean.getPrice(),"元x",String.valueOf(bean.getNumber()),"张"));
                linearLayout.addView(textView);
            }
        }
        helper.setText(R.id.tv_receiver,TextUtils.concat("收件人: ",item.getMemberName()));
        helper.setText(R.id.tv_address,TextUtils.concat("地址: ",item.getMemberAddress()));
        helper.addOnClickListener(R.id.btn_call);
    }
}
