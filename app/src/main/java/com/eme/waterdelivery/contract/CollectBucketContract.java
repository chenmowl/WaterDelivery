package com.eme.waterdelivery.contract;


import com.eme.waterdelivery.base.BasePresenter;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.model.bean.entity.BackBarrelBo;

import java.util.List;

/**
 * Created by dijiaoliang on 17/3/2.
 */
public interface CollectBucketContract {

    interface View extends BaseView {

        void showProgress(boolean isShow);

        void showRequestResult(boolean isSuccess, List<BackBarrelBo> data);
    }

    interface Presenter extends BasePresenter {

        void requestBucketData();
    }

}
