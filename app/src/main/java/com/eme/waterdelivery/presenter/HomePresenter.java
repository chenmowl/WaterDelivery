package com.eme.waterdelivery.presenter;

import com.eme.waterdelivery.App;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.HomeContract;
import com.eme.waterdelivery.model.net.RetrofitHelper;
import com.eme.waterdelivery.model.sp.SPBase;
import com.eme.waterdelivery.model.sp.SpConstant;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * Created by dijiaoliang on 17/3/2.
 */
public class HomePresenter implements HomeContract.Presenter {

    private static final String TAG = HomePresenter.class.getSimpleName();

    private HomeContract.View view;

    Disposable disposable;

    RetrofitHelper retrofitHelper;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private String storeId;

    @Inject
    public HomePresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (HomeContract.View) view;
        this.retrofitHelper = retrofitHelper;
    }

    @Override
    public void subscribe() {
        //请求数据
        storeId = SPBase.getContent(App.getAppInstance(), SpConstant.USER_FILE_NAME, SpConstant.USER_STORE_ID);
//        checkVersion();

    }

    @Override
    public void unSubscribe() {
        view = null;
        retrofitHelper = null;
        disposables.clear();
    }

}
