package com.eme.waterdelivery.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eme.waterdelivery.R;
import com.eme.waterdelivery.widget.FixedListButton;

import java.util.List;

/**
 * Created by dijiaoliang on 17/3/8.
 */

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> implements View.OnClickListener {

    private List<String> data;
    private LayoutInflater inflater;

    private OnRecyclerViewItemClickListener<Bundle> onRecyclerViewItemClickListener;

    public TestAdapter(Context context, List<String> data) {
        this.data = data;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =inflater.inflate(R.layout.item_delay, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(this);
        holder.button.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View view) {
        Bundle bundle=new Bundle();
        switch (view.getId()){
            case R.id.item:
                bundle.putString("info","item click");
                break;
            case R.id.btn_receiving:
                bundle.putString("info","item child click");
                break;
        }
        onRecyclerViewItemClickListener.onClick(view.getId(),bundle);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        FixedListButton button;

        public ViewHolder(View itemView) {
            super(itemView);
            button= (FixedListButton) itemView.findViewById(R.id.btn_receiving);
        }
    }

    public interface OnRecyclerViewItemClickListener<T>{
        void onClick(int id,T data);
    }

    public void setRecyclerViewListener(OnRecyclerViewItemClickListener<Bundle> onRecyclerViewItemClickListener){
        this.onRecyclerViewItemClickListener=onRecyclerViewItemClickListener;
    }


}
