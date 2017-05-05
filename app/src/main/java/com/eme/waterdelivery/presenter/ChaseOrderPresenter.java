package com.eme.waterdelivery.presenter;

import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.ChaseOrderContract;
import com.eme.waterdelivery.model.net.RetrofitHelper;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by dijiaoliang on 17/3/7.
 */
public class ChaseOrderPresenter implements ChaseOrderContract.Presenter {

    private static final String TAG = ChaseOrderPresenter.class.getSimpleName();

    private ChaseOrderContract.View view;

    RetrofitHelper retrofitHelper;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public ChaseOrderPresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (ChaseOrderContract.View) view;
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
