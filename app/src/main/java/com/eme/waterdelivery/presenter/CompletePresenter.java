package com.eme.waterdelivery.presenter;

import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.CompleteContract;
import com.eme.waterdelivery.model.net.RetrofitHelper;

import javax.inject.Inject;

/**
 * Created by dijiaoliang on 17/3/2.
 */
public class CompletePresenter implements CompleteContract.Presenter {

    private static final String TAG=CompletePresenter.class.getSimpleName();

    private CompleteContract.View view;

    RetrofitHelper retrofitHelper;

    @Inject
    public CompletePresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (CompleteContract.View)view;
        this.retrofitHelper=retrofitHelper;
    }

    @Override
    public void subscribe() {
        //请求数据
    }

    @Override
    public void unSubscribe() {
        view=null;
        retrofitHelper=null;
    }
}
