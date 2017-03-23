package com.eme.waterdelivery.contract;


import com.eme.waterdelivery.base.BasePresenter;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.model.bean.entity.HistoryOrderSumBo;
import com.eme.waterdelivery.model.bean.entity.HistoryOrderVo;

/**
 * Created by dijiaoliang on 17/3/2.
 */
public interface AllOrderContract {

    interface View extends BaseView {

        void showProgress(boolean b);

        void requestFailure(int flag, String message);

        void updateUi(HistoryOrderVo data, int flag);

        void notifyNoData();

        void updateOrderSum(HistoryOrderSumBo orderSumBo);

        void showOrderSumError();

        void netError(int flag);

    }

    interface Presenter extends BasePresenter {

        void requestData(int refreshFlag);

    }

}
