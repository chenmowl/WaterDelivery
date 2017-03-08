package com.eme.waterdelivery.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eme.waterdelivery.R;

import java.util.List;

/**
 * Created by dijiaoliang on 17/3/7.
 */
public class SendingDetailGoodAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private boolean showAll;

    public SendingDetailGoodAdapter(List<String> data) {
        super(R.layout.item_sending_detail_goods, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
    }

//    @Override
//    public int getItemCount() {
//
//        int count =getData().size();
//        if (null == getData()) {
//            return 0;
//        } else {
//            if (getData().size() > 2) {
//                if (showAll) {
//                    return getData().size();
//                } else {
//                    return 2;
//                }
//            } else {
//                return getData().size();
//            }
//
//        }
//    }


    public void openHideList(boolean showAll) {
        this.showAll = showAll;
        notifyDataSetChanged();
    }
}
