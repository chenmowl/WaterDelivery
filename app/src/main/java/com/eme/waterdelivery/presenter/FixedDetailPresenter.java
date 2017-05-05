package com.eme.waterdelivery.presenter;

import com.eme.waterdelivery.App;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.FixedDetailContract;
import com.eme.waterdelivery.model.bean.Result;
import com.eme.waterdelivery.model.bean.entity.CalculationPayAmountBo;
import com.eme.waterdelivery.model.bean.entity.OrderDetailBo;
import com.eme.waterdelivery.model.net.RetrofitHelper;
import com.eme.waterdelivery.tools.NetworkUtils;
import com.eme.waterdelivery.ui.SendingDetailActivity;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by dijiaoliang on 17/3/7.
 */

public class FixedDetailPresenter implements FixedDetailContract.Presenter {

    private static final String TAG = FixedDetailPresenter.class.getSimpleName();

    private FixedDetailContract.View view;

    RetrofitHelper retrofitHelper;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public FixedDetailPresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (FixedDetailContract.View) view;
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

    @Override
    public void requestData(String orderId) {
        if(!NetworkUtils.isConnected(App.getAppInstance())){
            ((SendingDetailActivity)view).showNetError();
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

    @Override
    public void updateAmount(String orderId, String payAmountGoods) {
        disposables.add(
                retrofitHelper.calculationPayAmount(orderId,payAmountGoods)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Result<CalculationPayAmountBo>>() {
                    @Override
                    public void accept(Result<CalculationPayAmountBo> calculationPayAmountBoResult) throws Exception {
                        if(calculationPayAmountBoResult!=null &&calculationPayAmountBoResult.isSuccess()&& calculationPayAmountBoResult.getData()!=null &&Constant.CODE_COMPLETE.equals(calculationPayAmountBoResult.getData().getCode())){
                            view.showUpdateAmount(true,calculationPayAmountBoResult.getData().getInfo().getPayAmount());
                        }else{
                            view.showUpdateAmount(false,null);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showUpdateAmount(false,null);
                    }
                })
        );
    }
}
