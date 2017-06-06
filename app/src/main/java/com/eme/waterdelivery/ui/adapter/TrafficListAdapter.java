package com.eme.waterdelivery.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.model.bean.entity.TrafficVo;

import java.util.List;

/**
 * 运单列表
 * <p>
 * Created by dijiaoliang on 17/3/7.
 */
public class TrafficListAdapter extends BaseQuickAdapter<TrafficVo.ResultsBean, BaseViewHolder> {

    public TrafficListAdapter(List<TrafficVo.ResultsBean> data) {
        super(R.layout.item_traffic, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TrafficVo.ResultsBean item) {
        helper.setText(R.id.tv_time_one, "下单时间: " + item.getCreateTime());
        helper.setText(R.id.tv_order_number, "运单号: " + item.getTrafficNo());
        helper.setText(R.id.tv_collect_water_point_name, "目的地派送点名称: " + item.getPointName());
        helper.setText(R.id.tv_collect_water_station_name, "发货仓库名称: " + item.getStationName());
        switch (item.getStatus()) {
            case Constant.TRAFFIC_STATUS_0:
                helper.setText(R.id.tv_pay_type, "待配车");
                break;
            case Constant.TRAFFIC_STATUS_1:
                helper.setText(R.id.tv_pay_type, "已配车待发货");
                break;
            case Constant.TRAFFIC_STATUS_2:
                helper.setText(R.id.tv_pay_type, "运输中待收货");
                break;
            case Constant.TRAFFIC_STATUS_3:
                helper.setText(R.id.tv_pay_type, "已收货待回执");
                break;
            case Constant.TRAFFIC_STATUS_4:
                helper.setText(R.id.tv_pay_type, "货运回执");
                break;
        }
    }
}
