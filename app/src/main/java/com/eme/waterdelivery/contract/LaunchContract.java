package com.eme.waterdelivery.contract;


import com.eme.waterdelivery.base.BasePresenter;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.model.bean.entity.LoginBo;

/**
 * Created by dijiaoliang on 17/3/2.
 */
public interface LaunchContract {

    interface View extends BaseView {

        void toLoginPage();

        void toHomePage(LoginBo loginBo);

    }

    interface Presenter extends BasePresenter {

    }

}
