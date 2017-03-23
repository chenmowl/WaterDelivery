package com.eme.waterdelivery.presenter;

import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.MyApplyContract;
import com.eme.waterdelivery.model.net.RetrofitHelper;

import javax.inject.Inject;


/**
 * Created by dijiaoliang on 17/3/2.
 */
public class MyApplyPresenter implements MyApplyContract.Presenter {

    private static final String TAG=MyApplyPresenter.class.getSimpleName();

    private MyApplyContract.View view;

    RetrofitHelper retrofitHelper;

    @Inject
    public MyApplyPresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (MyApplyContract.View)view;
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
