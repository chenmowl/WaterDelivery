package com.eme.waterdelivery.presenter;

import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.AssessRecordContract;
import com.eme.waterdelivery.model.net.RetrofitHelper;

import javax.inject.Inject;

/**
 * Created by dijiaoliang on 17/3/2.
 */
public class AssessRecordPresenter implements AssessRecordContract.Presenter {

    private static final String TAG=AssessRecordPresenter.class.getSimpleName();

    private AssessRecordContract.View view;

//    RetrofitHelper retrofitHelper;
    @Inject
    public AssessRecordPresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (AssessRecordContract.View)view;
//        this.retrofitHelper=retrofitHelper;
    }

    @Override
    public void subscribe() {
        //请求数据
    }

    @Override
    public void unSubscribe() {
        view=null;
//        retrofitHelper=null;
    }
}
