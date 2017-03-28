package com.eme.waterdelivery.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eme.waterdelivery.R;
import com.eme.waterdelivery.model.bean.entity.OrderDetailBo;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dijiaoliang on 17/3/9.
 */
public class SendingDetailGoodAdapter extends RecyclerView.Adapter<SendingDetailGoodAdapter.ViewHolder> {


    private List<OrderDetailBo.GoodsBean> data;
    private LayoutInflater inflater;

    private boolean isOpenAll;

    public SendingDetailGoodAdapter(Context context, List<OrderDetailBo.GoodsBean> data) {
        this.data = data;
        this.inflater = LayoutInflater.from(context);
        isOpenAll = false;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_sending_detail_goods, null, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrderDetailBo.GoodsBean goodsBean=data.get(position);
        holder.tvGoodName.setText(goodsBean.getGoodsName());
        holder.tvSize.setText("规格: "+goodsBean.getSpecName());
        holder.tvPrice.setText("￥ "+goodsBean.getGoodsAmount() );
        holder.tvSum.setText("x"+goodsBean.getGoodsNum());
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        } else {
            if (data.size() > 2) {
                if (!isOpenAll) {
                    return 2;
                } else {
                    return data.size();
                }
            } else {
                return data.size();
            }
        }
//        Log.e("getItemCount",data.size()+"");
//        return data.size();
    }

    public void setIsOpenPlus(boolean b) {
        isOpenAll = b;
        notifyDataSetChanged();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imageView;

        TextView tvGoodName;

        TextView tvSize;

        TextView tvPrice;

        TextView tvSum;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView= (CircleImageView) itemView.findViewById(R.id.sdv_good);
            tvGoodName= (TextView) itemView.findViewById(R.id.tv_good_name);
            tvSize= (TextView) itemView.findViewById(R.id.tv_good_size);
            tvPrice= (TextView) itemView.findViewById(R.id.tv_good_price);
            tvSum= (TextView) itemView.findViewById(R.id.tv_good_count);

        }
    }
}