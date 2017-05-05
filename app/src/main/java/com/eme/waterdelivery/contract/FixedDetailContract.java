package com.eme.waterdelivery.contract;


import com.eme.waterdelivery.base.BasePresenter;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.model.bean.entity.OrderDetailBo;

/**
 * Created by dijiaoliang on 17/3/2.
 */
public interface FixedDetailContract {

    interface View extends BaseView {
        void showProgress(boolean b);

        void showRequestMsg(String msg);

        void updateUi(OrderDetailBo orderDetailBo);

        void showSignResult(boolean isSuccess, String message);

        void showUpdateAmount(boolean isRequestSuccess,String amount);
    }

    interface Presenter extends BasePresenter {

        void requestData(String orderId);

        void updateAmount(String orderId,String payAmountGoods);
    }

}
