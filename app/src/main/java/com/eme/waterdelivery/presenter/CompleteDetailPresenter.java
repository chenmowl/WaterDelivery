package com.eme.waterdelivery.presenter;

import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.CompleteDetailContract;
import com.eme.waterdelivery.model.net.RetrofitHelper;

import javax.inject.Inject;

/**
 * Created by dijiaoliang on 17/3/2.
 */
public class CompleteDetailPresenter implements CompleteDetailContract.Presenter {

    private static final String TAG=CompleteDetailPresenter.class.getSimpleName();

    private CompleteDetailContract.View view;

    RetrofitHelper retrofitHelper;

    @Inject
    public CompleteDetailPresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (CompleteDetailContract.View)view;
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
