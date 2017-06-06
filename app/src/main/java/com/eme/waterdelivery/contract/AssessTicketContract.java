package com.eme.waterdelivery.contract;


import com.eme.waterdelivery.base.BasePresenter;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.model.bean.entity.AssessTicketVo;

/**
 * Created by dijiaoliang on 17/3/2.
 */
public interface AssessTicketContract {

    interface View extends BaseView {

        void showProgress(boolean b);

        void showRequestResult(boolean isSuccess, AssessTicketVo assessTicketVo, int flag);
    }

    interface Presenter extends BasePresenter {

        void requestData(int flag);

    }

}
