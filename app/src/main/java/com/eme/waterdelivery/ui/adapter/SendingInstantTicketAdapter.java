package com.eme.waterdelivery.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.model.bean.entity.OrderDetailBo;

import java.util.List;

/**
 * 即时配送订单详情中水票列表adapter
 * <p>
 * Created by dijiaoliang on 17/3/9.
 */
public class SendingInstantTicketAdapter extends BaseQuickAdapter<OrderDetailBo.GoodsBean, BaseViewHolder> {

    private boolean isPayOnline;

    public SendingInstantTicketAdapter(List<OrderDetailBo.GoodsBean> data) {
        super(R.layout.item_sending_instant_ticket,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetailBo.GoodsBean item) {
        if(getData()!=null){
            int size=getData().size();
            int position=getParentPosition(item);
            if(size>=1 && size-1==position){
                helper.setVisible(R.id.line_bottom,false);
            }else{
                helper.setVisible(R.id.line_bottom,true);
            }
        }
        if(!isPayOnline && Constant.STR_TWO.equals(item.getTicketsModel())){
            helper.setVisible(R.id.ll_number,true);
            helper.setVisible(R.id.tv_ticket_num,false);
            helper.setText(R.id.tv_item_number,String.valueOf(item.getTicketUsedCount()));
            helper.addOnClickListener(R.id.btn_add);
            helper.addOnClickListener(R.id.btn_reduce);
        }else{
            helper.setVisible(R.id.ll_number,false);
            helper.setVisible(R.id.tv_ticket_num,true);
            helper.setText(R.id.tv_ticket_num,"X"+String.valueOf(item.getTicketUsedCount()));
        }
        helper.setText(R.id.tv_ticket_type,item.getTicketName());
    }

    public boolean isPayOnline() {
        return isPayOnline;
    }

    public void setPayOnline(boolean payOnline) {
        isPayOnline = payOnline;
    }
}
