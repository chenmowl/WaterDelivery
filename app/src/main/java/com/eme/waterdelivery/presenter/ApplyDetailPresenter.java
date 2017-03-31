package com.eme.waterdelivery.presenter;

import android.text.TextUtils;

import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.ApplyDetailContract;
import com.eme.waterdelivery.model.bean.Result;
import com.eme.waterdelivery.model.bean.StatusResult;
import com.eme.waterdelivery.model.bean.entity.ApplyDetailVo;
import com.eme.waterdelivery.model.net.RetrofitHelper;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by dijiaoliang on 17/3/2.
 */
public class ApplyDetailPresenter implements ApplyDetailContract.Presenter {

    private static final String TAG=ApplyDetailPresenter.class.getSimpleName();

    private ApplyDetailContract.View view;

    RetrofitHelper retrofitHelper;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public ApplyDetailPresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (ApplyDetailContract.View)view;
        this.retrofitHelper=retrofitHelper;
    }

    @Override
    public void subscribe() {
        //请求数据
//        loadData();
    }

    @Override
    public void unSubscribe() {
        view = null;
        retrofitHelper = null;
        disposables.clear();
    }

    @Override
    public void requestData(String trafficNo) {
        disposables.add(retrofitHelper.getPurchaseOrderDetail(trafficNo)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        view.showProgress(true);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Result<ApplyDetailVo>>() {
                    @Override
                    public void accept(Result<ApplyDetailVo> applyDetailVoResult) throws Exception {
                        if(applyDetailVoResult!=null && applyDetailVoResult.isSuccess() && applyDetailVoResult.getData()!=null && Constant.CODE_COMPLETE.equals(applyDetailVoResult.getData().getCode())){
                            view.updateUi(applyDetailVoResult.getData().getInfo());
                        }else{
                            view.showRequestMsg(applyDetailVoResult.getData().getMessage());
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
    public void confirmPurchase(String trafficNo) {
        disposables.add(
                retrofitHelper.confirmPurchaseOrder(trafficNo)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        view.showProgress(true);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<StatusResult>() {
                            @Override
                            public void accept(StatusResult statusResult) throws Exception {
                                view.showProgress(false);
                                if(statusResult!=null && statusResult.isSuccess() &&statusResult.getData()!=null&& Constant.CODE_COMPLETE.equals(statusResult.getData().getCode())){
                                    view.showReceiveOrderStatus(true,statusResult.getData().getMessage());
                                }else{
                                    if(statusResult!=null && statusResult.getData()!=null && !TextUtils.isEmpty(statusResult.getData().getMessage())){
                                        view.showReceiveOrderStatus(false,statusResult.getData().getMessage());
                                    }else{
                                        view.showReceiveOrderStatus(false,null);
                                    }
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                view.showProgress(false);
                                view.showReceiveOrderStatus(false,null);
                            }
                        }
                )
        );
    }
}
