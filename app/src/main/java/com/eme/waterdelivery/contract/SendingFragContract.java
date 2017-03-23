package com.eme.waterdelivery.contract;


import com.eme.waterdelivery.base.BasePresenter;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.model.bean.entity.OrderSumBo;
import com.eme.waterdelivery.model.bean.entity.WaitingOrderBo;

import java.util.List;

/**
 * Created by dijiaoliang on 17/3/2.
 */
public interface SendingFragContract {

    interface View extends BaseView {
        void showProgress(boolean b);

        void requestFailure(int flag,String message);

        void updateUi(List<WaitingOrderBo> data, int flag);

        void notifyNoData();

        void updateOrderSum(OrderSumBo orderSumBo);

        void showOrderSumError();
    }

    interface Presenter extends BasePresenter {

        void requestData(int refreshFlag);

        void refreshOrderList();
    }

}
