package com.eme.waterdelivery.presenter;

import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.SendingDetailContract;
import com.eme.waterdelivery.model.net.RetrofitHelper;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by dijiaoliang on 17/3/7.
 */

public class SendingDetailPresenter implements SendingDetailContract.Presenter {

    private static final String TAG = SendingDetailPresenter.class.getSimpleName();

    private SendingDetailContract.View view;

    private CompositeSubscription compositeSubscription;

    RetrofitHelper retrofitHelper;

    @Inject
    public SendingDetailPresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (SendingDetailContract.View) view;
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
