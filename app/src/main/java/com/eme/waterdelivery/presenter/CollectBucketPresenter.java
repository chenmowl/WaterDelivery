package com.eme.waterdelivery.presenter;

import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.CollectBucketContract;
import com.eme.waterdelivery.model.net.RetrofitHelper;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by dijiaoliang on 17/3/7.
 */
public class CollectBucketPresenter implements CollectBucketContract.Presenter {

    private static final String TAG = CollectBucketPresenter.class.getSimpleName();

    private CollectBucketContract.View view;

    RetrofitHelper retrofitHelper;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public CollectBucketPresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (CollectBucketContract.View) view;
        this.retrofitHelper = retrofitHelper;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        view = null;
        retrofitHelper = null;
        disposables.clear();
    }

}
