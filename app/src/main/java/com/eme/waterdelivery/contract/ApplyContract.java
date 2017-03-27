package com.eme.waterdelivery.contract;


import com.eme.waterdelivery.base.BasePresenter;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.model.bean.entity.ApplyOneLevelBo;
import com.eme.waterdelivery.model.bean.entity.ApplyTwoLevelGoodBo;

import java.util.List;

/**
 * Created by dijiaoliang on 17/3/2.
 */
public interface ApplyContract {

    interface View extends BaseView {
        void updateOneLevel(List<ApplyOneLevelBo.ListBean> listBeen);

        void updateTwoLevel(List<ApplyOneLevelBo.ListBean> listBeen);

        void updateTwoLevelGoods(List<ApplyTwoLevelGoodBo.ListBean> listBeen);

        void requestError();

        void showProgress(boolean b);

        void showSubmitResult(boolean isSuccess,String msg);
    }

    interface Presenter extends BasePresenter {

        void requestApplyOneLevel();

        void requestApplyTwoLevel(String id);

        void requestApplyTwoLevelGoods(String id);

        void submitApplications(String storeId,String remark,String json);

    }

}
