package com.eme.waterdelivery.contract;


import com.eme.waterdelivery.base.BasePresenter;
import com.eme.waterdelivery.base.BaseView;

/**
 * Created by dijiaoliang on 17/3/2.
 */
public interface LoginContract {

    interface View extends BaseView {

        void showUsernameError(boolean isEmpty);

        void showPasswordError(boolean isEmpty);

        void toHome();

        void showRequestError(String info);

    }

    interface Presenter extends BasePresenter {

        void login(String username,String password);

    }

}
