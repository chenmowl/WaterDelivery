package com.eme.waterdelivery.presenter;

import com.eme.waterdelivery.App;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.CompleteDetailContract;
import com.eme.waterdelivery.model.bean.Result;
import com.eme.waterdelivery.model.bean.entity.OrderDetailBo;
import com.eme.waterdelivery.model.net.RetrofitHelper;
import com.eme.waterdelivery.tools.NetworkUtils;
import com.eme.waterdelivery.ui.CompleteDetailActivity;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dijiaoliang on 17/3/2.
 */
public class CompleteDetailPresenter implements CompleteDetailContract.Presenter {

    private static final String TAG=CompleteDetailPresenter.class.getSimpleName();

    private CompleteDetailContract.View view;

    RetrofitHelper retrofitHelper;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public CompleteDetailPresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (CompleteDetailContract.View)view;
        this.retrofitHelper=retrofitHelper;
    }

    @Override
    public void subscribe() {
        //请求数据
    }


    @Override
    public void unSubscribe() {
        view = null;
        retrofitHelper = null;
        disposables.clear();
    }

    @Override
    public void requestData(String orderId) {
        if(!NetworkUtils.isConnected(App.getAppInstance())){
            ((CompleteDetailActivity)view).showNetError();
            return;
        }
        disposables.add(retrofitHelper.getOrderDetail(orderId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        view.showProgress(true);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Result<OrderDetailBo>>() {
                    @Override
                    public void accept(Result<OrderDetailBo> orderDetailBoResult) throws Exception {
                        if(orderDetailBoResult!=null && orderDetailBoResult.isSuccess() && orderDetailBoResult.getData()!=null && Constant.CODE_COMPLETE.equals(orderDetailBoResult.getData().getCode())){
                            view.updateUi(orderDetailBoResult.getData().getInfo());
                        }else{
                            view.showRequestMsg(orderDetailBoResult.getData().getMessage());
                        }
                        view.showProgress(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showRequestMsg(null);
                        view.showProgress(false);
                    }
                })
        );
    }
}
