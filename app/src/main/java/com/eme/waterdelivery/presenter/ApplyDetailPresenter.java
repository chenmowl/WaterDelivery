package com.eme.waterdelivery.presenter;

import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.ApplyDetailContract;
import com.eme.waterdelivery.model.net.RetrofitHelper;

import javax.inject.Inject;


/**
 * Created by dijiaoliang on 17/3/2.
 */
public class ApplyDetailPresenter implements ApplyDetailContract.Presenter {

    private static final String TAG=ApplyDetailPresenter.class.getSimpleName();

    private ApplyDetailContract.View view;

    RetrofitHelper retrofitHelper;

    @Inject
    public ApplyDetailPresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (ApplyDetailContract.View)view;
        this.retrofitHelper=retrofitHelper;
    }

    @Override
    public void subscribe() {
        //请求数据
//        loadData();
    }

    @Override
    public void unSubscribe() {
    }
}
