package com.eme.waterdelivery.contract;


import com.eme.waterdelivery.base.BasePresenter;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.event.CompleteNumEvent;
import com.eme.waterdelivery.model.bean.entity.HistoryOrderSumBo;

/**
 * Created by dijiaoliang on 17/3/2.
 */
public interface HomeContract {

    interface View extends BaseView {
        void updateOrderSum(HistoryOrderSumBo historyOrderSumBo);
        void updateNavSum(CompleteNumEvent completeNumEvent);
    }

    interface Presenter extends BasePresenter {

        void requestCompleteNumber();

    }

}
