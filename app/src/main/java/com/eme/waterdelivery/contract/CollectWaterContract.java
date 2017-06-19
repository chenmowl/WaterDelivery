package com.eme.waterdelivery.contract;


import com.eme.waterdelivery.base.BasePresenter;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.model.bean.entity.TrafficDetailVo;

import java.util.List;

/**
 * Created by dijiaoliang on 17/3/2.
 */
public interface CollectWaterContract {

    interface View extends BaseView {

        void showProgress(boolean isShow);

        void showRequestResult(boolean isSuccess, List<TrafficDetailVo.GoodsBean> data);

        void showConfirmResult(boolean isSuccess);
    }

    interface Presenter extends BasePresenter {

        void requestTrafficDetail(String trafficNo);

        void handleTraffic(String trafficNo);

    }

}
