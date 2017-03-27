package com.eme.waterdelivery.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.model.bean.entity.ApplyTwoLevelGoodBo;

import java.util.List;

/**
 * Created by dijiaoliang on 17/3/7.
 */
public class ApplyMenuAdapter extends BaseQuickAdapter<ApplyTwoLevelGoodBo.ListBean, BaseViewHolder> {


    public ApplyMenuAdapter(List<ApplyTwoLevelGoodBo.ListBean> data) {
        super(R.layout.item_apply, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ApplyTwoLevelGoodBo.ListBean item) {
        helper.addOnClickListener(R.id.rl_delete);
        helper.setText(R.id.tv_title,"产品清单"+helper.getLayoutPosition());
        helper.setText(R.id.tv_type,"产品品类: "+item.getGoodsCommonName());
        helper.setText(R.id.tv_name,"产品名称: "+item.getGoodsName());
        helper.setText(R.id.tv_size,"产品规格: "+item.getSpecName());
        helper.setText(R.id.tv_count,"订购数量: "+item.getCount()+item.getUnitName());
    }
}
