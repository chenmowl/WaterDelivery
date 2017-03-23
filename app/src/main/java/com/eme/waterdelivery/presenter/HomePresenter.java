package com.eme.waterdelivery.presenter;

import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.HomeContract;
import com.eme.waterdelivery.model.net.RetrofitHelper;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;


/**
 * Created by dijiaoliang on 17/3/2.
 */
public class HomePresenter implements HomeContract.Presenter {

    private static final String TAG=HomePresenter.class.getSimpleName();

    private HomeContract.View view;

    Disposable disposable;

    RetrofitHelper retrofitHelper;

    @Inject
    public HomePresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (HomeContract.View)view;
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
