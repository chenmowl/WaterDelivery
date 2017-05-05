package com.eme.waterdelivery.presenter;

import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.AssessmentContract;
import com.eme.waterdelivery.model.net.RetrofitHelper;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by dijiaoliang on 17/3/7.
 */
public class AssessmentPresenter implements AssessmentContract.Presenter {

    private static final String TAG = AssessmentPresenter.class.getSimpleName();

    private AssessmentContract.View view;

    RetrofitHelper retrofitHelper;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public AssessmentPresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (AssessmentContract.View) view;
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
