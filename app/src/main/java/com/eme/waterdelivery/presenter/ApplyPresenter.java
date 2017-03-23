package com.eme.waterdelivery.presenter;

import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.ApplyContract;
import com.eme.waterdelivery.model.net.RetrofitHelper;

import javax.inject.Inject;

/**
 * Created by dijiaoliang on 17/3/2.
 */
public class ApplyPresenter implements ApplyContract.Presenter {

    private static final String TAG=ApplyPresenter.class.getSimpleName();

    private ApplyContract.View view;

    RetrofitHelper retrofitHelper;

    @Inject
    public ApplyPresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (ApplyContract.View)view;
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
