package com.eme.waterdelivery.contract;


import com.eme.waterdelivery.base.BasePresenter;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.model.bean.entity.ApplyDetailVo;

/**
 * Created by dijiaoliang on 17/3/2.
 */
public interface ApplyDetailContract {

    interface View extends BaseView {

        void showProgress(boolean b);

        void updateUi(ApplyDetailVo applyDetailVo);

        void showRequestMsg(String msg);
    }

    interface Presenter extends BasePresenter {
        void requestData(String trafficNo);
    }

}
