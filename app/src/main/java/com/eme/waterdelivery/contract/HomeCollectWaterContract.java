package com.eme.waterdelivery.contract;


import com.eme.waterdelivery.base.BasePresenter;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.model.bean.entity.TrafficVo;

import java.util.List;

/**
 * Created by dijiaoliang on 17/3/2.
 */
public interface HomeCollectWaterContract {

    interface View extends BaseView {
        void showProgress(boolean isShow);

        void showRequestResult(boolean isSuccess, List<TrafficVo.ResultsBean> data, String message);
    }

    interface Presenter extends BasePresenter {

        void requestTrafficList(int pageNo);

        void requestTrafficListRefresh(int pageNo);

    }

}
