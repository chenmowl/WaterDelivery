package com.eme.waterdelivery.contract;


import com.eme.waterdelivery.base.BasePresenter;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.model.bean.entity.SaleTicketRecordBo;

import java.util.List;

/**
 * Created by dijiaoliang on 17/3/2.
 */
public interface SaleTicketRecordContract {

    interface View extends BaseView {

        void netError(int flag);

        void showProgress(boolean b);

        void notifyNoData();

        void showRequestListResult(boolean isSuccess,List<SaleTicketRecordBo.ListBean> list,int refreshFlag,String message);
    }

    interface Presenter extends BasePresenter {

        void requestData(int refreshFlag);

    }

}
