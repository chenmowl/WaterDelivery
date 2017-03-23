package com.eme.waterdelivery.presenter;

import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.ApplyRecordContract;
import com.eme.waterdelivery.model.net.RetrofitHelper;

import javax.inject.Inject;

/**
 * Created by dijiaoliang on 17/3/2.
 */
public class ApplyRecordPresenter implements ApplyRecordContract.Presenter {

    private static final String TAG=ApplyRecordPresenter.class.getSimpleName();

    private ApplyRecordContract.View view;

    RetrofitHelper retrofitHelper;

    @Inject
    public ApplyRecordPresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (ApplyRecordContract.View)view;
        this.retrofitHelper=retrofitHelper;
    }

    @Override
    public void subscribe() {
        //请求数据
    }

    @Override
    public void unSubscribe() {
    }
}
