package com.eme.waterdelivery.contract;


import com.eme.waterdelivery.base.BasePresenter;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.model.bean.entity.LoginBo;

/**
 * Created by dijiaoliang on 17/3/2.
 */
public interface LoginContract {

    interface View extends BaseView {

        void showUsernameError(boolean isEmpty);

        void showPasswordError(boolean isEmpty);

        void toHome(LoginBo info);

        void showRequestError(String info);

        void showProgress(boolean b);

    }

    interface Presenter extends BasePresenter {

        void login(String username,String password);

    }

}
