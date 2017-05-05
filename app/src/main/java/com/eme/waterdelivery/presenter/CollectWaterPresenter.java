package com.eme.waterdelivery.presenter;

import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.CollectWaterContract;
import com.eme.waterdelivery.model.net.RetrofitHelper;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by dijiaoliang on 17/3/7.
 */
public class CollectWaterPresenter implements CollectWaterContract.Presenter {

    private static final String TAG = CollectWaterPresenter.class.getSimpleName();

    private CollectWaterContract.View view;

    RetrofitHelper retrofitHelper;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public CollectWaterPresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (CollectWaterContract.View) view;
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
