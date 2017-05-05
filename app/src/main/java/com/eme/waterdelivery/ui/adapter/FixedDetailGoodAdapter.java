package com.eme.waterdelivery.ui.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eme.waterdelivery.App;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.model.bean.entity.OrderDetailBo;
import com.eme.waterdelivery.tools.ImageLoader;

import java.util.List;

/**
 * 固定订单商品adapter
 * <p>
 * Created by dijiaoliang on 17/3/9.
 */
public class FixedDetailGoodAdapter extends BaseQuickAdapter<OrderDetailBo.GoodsBean, BaseViewHolder> {


    public FixedDetailGoodAdapter(List<OrderDetailBo.GoodsBean> data) {
        super(R.layout.item_fixed_detail_goods, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetailBo.GoodsBean item) {
        helper.setText(R.id.tv_good_name, item.getGoodsName());
        helper.setText(R.id.tv_item_number, String.valueOf(item.getGoodsNum()));
        helper.setText(R.id.tv_good_size, TextUtils.concat("规格: ", item.getSpecName()));
        helper.setText(R.id.tv_good_price, TextUtils.concat("￥ ", item.getGoodsAmount()));
        int yaCount=item.getMortgageBucketCount();
        if(yaCount<=0){
            helper.setText(R.id.tv_ya, "押");
        }else{
            helper.setText(R.id.tv_ya, TextUtils.concat("押 (", String.valueOf(yaCount),")"));
        }
        int saleCount=item.getSellBucketCount();
        if(saleCount<=0){
            helper.setText(R.id.tv_sale, "售");
        }else{
            helper.setText(R.id.tv_sale, TextUtils.concat("售 (", String.valueOf(saleCount),")"));
        }
        ImageLoader.load(App.getAppInstance(), item.getGoodsImage(), (ImageView) helper.getView(R.id.sdv_good));
        helper.addOnClickListener(R.id.tv_ya);
        helper.addOnClickListener(R.id.tv_sale);
        helper.addOnClickListener(R.id.btn_reduce);
        helper.addOnClickListener(R.id.btn_add);
    }

}
