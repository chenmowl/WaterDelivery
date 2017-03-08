package com.eme.waterdelivery.presenter;

import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.SendingContract;
import com.eme.waterdelivery.model.net.RetrofitHelper;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;


/**
 * Created by dijiaoliang on 17/3/7.
 */

public class SendingPresenter implements SendingContract.Presenter {

    private static final String TAG = SendingPresenter.class.getSimpleName();

    private SendingContract.View view;

    private CompositeSubscription compositeSubscription;

    RetrofitHelper retrofitHelper;

    @Inject
    public SendingPresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (SendingContract.View) view;
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
