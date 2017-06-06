package com.eme.waterdelivery.contract;


import com.eme.waterdelivery.base.BasePresenter;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.model.bean.entity.ChaseOrderVo;

import java.util.List;

/**
 * Created by dijiaoliang on 17/3/2.
 */
public interface ChaseOrderContract {

    interface View extends BaseView {

        void netError(int refreshFlag);

        void notifyNoData();

        void showProgress(boolean isShow);

        void updateUi(List<ChaseOrderVo.ResultsBean> data,int refreshFlag);

        void requestFailure(int refreshFlag);

        void showConfirmCrefditResult(boolean isSuccess);
    }

    interface Presenter extends BasePresenter {

        void requestData(int refreshFlag);

        void confirmCrefdit(String id);

    }

}
