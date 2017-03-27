package com.eme.waterdelivery.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.model.bean.entity.PurchaseGoodBo;

import java.util.List;

/**
 * Created by dijiaoliang on 17/3/7.
 */
public class ApplyDetailMenuAdapter extends BaseQuickAdapter<PurchaseGoodBo, BaseViewHolder> {

    private String status;

    public ApplyDetailMenuAdapter(List<PurchaseGoodBo> data) {
        super(R.layout.item_apply_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PurchaseGoodBo item) {
//        helper.addOnClickListener(R.id.rl_delete);
        helper.setText(R.id.tv_title,"产品清单"+helper.getLayoutPosition());
        helper.setText(R.id.tv_type,"产品品类: "+item.getGoodsCommonName());
        helper.setText(R.id.tv_name,"产品名称: "+item.getGoodsName());
        helper.setText(R.id.tv_size,"产品规格: "+item.getSpecName());
        String count;
        switch (getStatus()){
            case Constant.APPLY_RECORD_STATUS_UNHANDLE:
                count=String.valueOf(item.getPreChangeAmount());
                break;
            default:
                count=String.valueOf(item.getChangeAmount());
                break;
        }
        helper.setText(R.id.tv_count,"订购数量: "+count+item.getUnitName());
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
