package com.eme.waterdelivery.contract;


import com.eme.waterdelivery.base.BasePresenter;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.model.bean.entity.OrderSumBo;
import com.eme.waterdelivery.model.bean.entity.WaitingOrderBo;

import java.util.List;

/**
 * Created by dijiaoliang on 17/3/2.
 */
public interface DelayFragContract {

    interface View extends BaseView {
        void showProgress(boolean b);

        void requestFailure(int flag,String message);

        void updateUi(List<WaitingOrderBo> data, int flag);

        void notifyNoData();

        void updateOrderSum(OrderSumBo orderSumBo);

        void showOrderSumError();

        void showReceiveOrderStatus(String message);

        void netError(int flag);
    }

    interface Presenter extends BasePresenter {

        void requestData(int refreshFlag);

        void receiveOrder(String orderId);

    }

}
