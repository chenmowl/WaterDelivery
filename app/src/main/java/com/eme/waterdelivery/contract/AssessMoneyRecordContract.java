package com.eme.waterdelivery.contract;


import com.eme.waterdelivery.base.BasePresenter;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.model.bean.entity.AssessMoneyRecordVo;

import java.util.List;

/**
 * Created by dijiaoliang on 17/3/2.
 */
public interface AssessMoneyRecordContract {

    interface View extends BaseView {
        void showProgress(boolean b);

        void requestFailure(int flag);

        void updateUi(List<AssessMoneyRecordVo.ResultsBean> data, int flag);

        void notifyNoData();

        void netError(int flag);
    }

    interface Presenter extends BasePresenter {

        void requestData(int refreshFlag);

    }
}
