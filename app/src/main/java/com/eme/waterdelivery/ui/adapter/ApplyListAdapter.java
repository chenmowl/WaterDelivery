package com.eme.waterdelivery.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.OnRecyclerItemClickListener;

import java.util.List;

import butterknife.BindView;

/**
 * Created by dijiaoliang on 17/3/9.
 */
public class ApplyListAdapter extends RecyclerView.Adapter<ApplyListAdapter.ViewHolder> implements View.OnClickListener {


    private List<String> data;
    private LayoutInflater inflater;

    private OnRecyclerItemClickListener onRecyclerItemClickListener;

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    public ApplyListAdapter(Context context, List<String> data) {
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_apply, null, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.delete.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
//        Log.e("getItemCount",data.size()+"");
        return data.size();
    }

    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.rl_delete:
                //todo  添加传递数据
                break;
        }
        onRecyclerItemClickListener.onItemClick(view.getId(), bundle);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rl_delete)
        RelativeLayout delete;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
