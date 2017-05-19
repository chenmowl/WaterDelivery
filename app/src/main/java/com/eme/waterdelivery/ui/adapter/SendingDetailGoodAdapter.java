package com.eme.waterdelivery.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eme.waterdelivery.App;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.model.bean.entity.OrderDetailBo;
import com.eme.waterdelivery.tools.ImageLoader;

import java.util.List;

/**
 * 配送中非水类产品的
 * <p>
 * Created by dijiaoliang on 17/3/9.
 */
public class SendingDetailGoodAdapter extends RecyclerView.Adapter<SendingDetailGoodAdapter.ViewHolder> {


    private List<OrderDetailBo.GoodsBean> data;
    private LayoutInflater inflater;

    public SendingDetailGoodAdapter(Context context, List<OrderDetailBo.GoodsBean> data) {
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_sending_detail_goods, null, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrderDetailBo.GoodsBean goodsBean = data.get(position);
        holder.tvGoodName.setText(goodsBean.getGoodsName());
        holder.tvSize.setText("规格: " + goodsBean.getSpecName());
        holder.tvPrice.setText("￥ " + goodsBean.getGoodsAmount());
        holder.tvSum.setText("x" + goodsBean.getGoodsNum());
        ImageLoader.load(App.getAppInstance(), goodsBean.getGoodsImage(), holder.imageView);
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        } else {
            return data.size();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        TextView tvGoodName;

        TextView tvSize;

        TextView tvPrice;

        TextView tvSum;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.sdv_good);
            tvGoodName = (TextView) itemView.findViewById(R.id.tv_good_name);
            tvSize = (TextView) itemView.findViewById(R.id.tv_good_size);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_good_price);
            tvSum = (TextView) itemView.findViewById(R.id.tv_good_count);

        }
    }
}
