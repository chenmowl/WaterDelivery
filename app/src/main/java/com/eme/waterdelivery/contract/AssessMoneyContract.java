package com.eme.waterdelivery.contract;


import com.eme.waterdelivery.base.BasePresenter;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.model.bean.entity.AssessMoneyVo;

/**
 * Created by dijiaoliang on 17/3/2.
 */
public interface AssessMoneyContract {

    interface View extends BaseView {

        void showProgress(boolean b);

        void showRequestResult(boolean isSuccess, AssessMoneyVo assessMoneyVo,int flag);
    }

    interface Presenter extends BasePresenter {

        void requestData(int flag);

    }

}
