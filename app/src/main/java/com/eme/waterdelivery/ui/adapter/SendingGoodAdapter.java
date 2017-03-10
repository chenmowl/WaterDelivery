package com.eme.waterdelivery.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eme.waterdelivery.R;

import java.util.List;

/**
 * Created by dijiaoliang on 17/3/9.
 */
public class SendingGoodAdapter extends RecyclerView.Adapter<SendingGoodAdapter.ViewHolder> {


    private List<String> data;
    private LayoutInflater inflater;

    private boolean isOpenAll;

    public SendingGoodAdapter(Context context, List<String> data) {
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

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
