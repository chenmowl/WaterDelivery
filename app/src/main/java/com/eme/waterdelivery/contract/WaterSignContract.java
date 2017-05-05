package com.eme.waterdelivery.contract;


import com.eme.waterdelivery.base.BasePresenter;
import com.eme.waterdelivery.base.BaseView;

/**
 * Created by dijiaoliang on 17/3/2.
 */
public interface WaterSignContract {

    interface View extends BaseView {

        void loadWXImage(String url);

        void getImageFailure(String message);

        void showProgress(boolean b);

        void showSignResult(boolean isSuccess,String message);
    }

    interface Presenter extends BasePresenter {

        void getWXImage();

        void sign(String orderId,String payType,String payAmountGoods);
    }

}
