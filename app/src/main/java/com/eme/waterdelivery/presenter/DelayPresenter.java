package com.eme.waterdelivery.presenter;

import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.DelayContract;
import com.eme.waterdelivery.model.net.RetrofitHelper;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by dijiaoliang on 17/3/7.
 */

public class DelayPresenter implements DelayContract.Presenter {

    private static final String TAG = DelayPresenter.class.getSimpleName();

    private DelayContract.View view;

    private CompositeSubscription compositeSubscription;

    RetrofitHelper retrofitHelper;

    @Inject
    public DelayPresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (DelayContract.View) view;
        this.retrofitHelper = retrofitHelper;
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        compositeSubscription.clear();
    }
}
