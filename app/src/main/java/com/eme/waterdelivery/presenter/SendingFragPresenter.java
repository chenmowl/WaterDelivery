package com.eme.waterdelivery.presenter;

import com.eme.waterdelivery.App;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.SendingFragContract;
import com.eme.waterdelivery.model.bean.Result;
import com.eme.waterdelivery.model.bean.WaitingBeanZip;
import com.eme.waterdelivery.model.bean.entity.OrderSumBo;
import com.eme.waterdelivery.model.bean.entity.WaitingOrderVo;
import com.eme.waterdelivery.model.net.RetrofitHelper;
import com.eme.waterdelivery.model.sp.SPBase;
import com.eme.waterdelivery.model.sp.SpConstant;
import com.eme.waterdelivery.tools.NetworkUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by dijiaoliang on 17/3/7.
 */

public class SendingFragPresenter implements SendingFragContract.Presenter {

    private static final String TAG = SendingFragPresenter.class.getSimpleName();

    private SendingFragContract.View view;

    RetrofitHelper retrofitHelper;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private int pageNum;//页数
    private String storeId;
    private boolean hasMoreData;

    @Inject
    public SendingFragPresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (SendingFragContract.View) view;
        this.retrofitHelper = retrofitHelper;
    }

    @Override
    public void subscribe() {
        hasMoreData = true;
        pageNum = Constant.ZERO;
        storeId = SPBase.getContent(App.getAppInstance(), SpConstant.USER_FILE_NAME, SpConstant.USER_STORE_ID);
        requestData(Constant.REFRESH_NORMAL);
    }

    @Override
    public void unSubscribe() {
        view = null;
        retrofitHelper = null;
        disposables.clear();
    }

    /**
     * 请求列表数据
     * @param refreshFlag
     */
    public void requestData(final int refreshFlag) {
        if(!NetworkUtils.isConnected(App.getAppInstance())){
            view.netError(refreshFlag);
            return;
        }
        int pNum = 0;
        switch (refreshFlag) {
            case Constant.REFRESH_NORMAL:
                pNum = Constant.ONE;
                break;
            case Constant.REFRESH_DOWN:
                pNum = Constant.ONE;
                break;
            case Constant.REFRESH_UP_LOADMORE:
                if (hasMoreData) {
                    pNum = pageNum + Constant.ONE;
                } else {
                    view.notifyNoData();
                    return;
                }
                break;
        }
        disposables.add(Observable.zip(retrofitHelper.getDistributingOrders(storeId, pNum, Constant.PAGE_SIZE), retrofitHelper.getOrderSum(storeId), new BiFunction<Result<WaitingOrderVo>, Result<OrderSumBo>, WaitingBeanZip>() {
            @Override
            public WaitingBeanZip apply(Result<WaitingOrderVo> waitingOrderVoResult, Result<OrderSumBo> orderSumBoResult) throws Exception {
                return new WaitingBeanZip(waitingOrderVoResult, orderSumBoResult);
            }
        }).subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (Constant.REFRESH_NORMAL == refreshFlag) {
                            view.showProgress(true);
                        }
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WaitingBeanZip>() {
                    @Override
                    public void accept(WaitingBeanZip waitingBeanZip) throws Exception {
                        if (waitingBeanZip == null) {
                            view.requestFailure(refreshFlag, null);
                            return;
                        }
                        Result<OrderSumBo> orderSumBoResult = waitingBeanZip.getOrderSumBoResult();
                        if (orderSumBoResult != null && orderSumBoResult.isSuccess() && orderSumBoResult.getData() != null && Constant.CODE_COMPLETE.equals(orderSumBoResult.getData().getCode())) {
                            view.updateOrderSum(orderSumBoResult.getData().getInfo());
                        } else {
                            view.showOrderSumError();
                        }
                        Result<WaitingOrderVo> waitingOrderBoListResult = waitingBeanZip.getWaitingOrderVoResult();
                        if (waitingOrderBoListResult != null && waitingOrderBoListResult.isSuccess() && waitingOrderBoListResult.getData() != null && Constant.CODE_COMPLETE.equals(waitingOrderBoListResult.getData().getCode())) {
                            hasMoreData = waitingOrderBoListResult.getData().getInfo().isHasMore();
                            view.updateUi(waitingOrderBoListResult.getData().getInfo().getList(), refreshFlag);
                            switch (refreshFlag) {
                                case Constant.REFRESH_NORMAL:
                                    pageNum=Constant.ONE;
                                    break;
                                case Constant.REFRESH_DOWN:
                                    pageNum=Constant.ONE;
                                    break;
                                case Constant.REFRESH_UP_LOADMORE:
                                    pageNum++;
                                    break;
                                default:
                                    break;
                            }
                        } else {
                            view.requestFailure(refreshFlag, waitingOrderBoListResult.getData().getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.requestFailure(refreshFlag, null);
                    }
                }));
    }

    @Override
    public void refreshOrderList() {
        disposables.add(retrofitHelper.getDistributingOrders(storeId, Constant.ONE, Constant.PAGE_SIZE).subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {view.showProgress(true);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Result<WaitingOrderVo>>() {
                    @Override
                    public void accept(Result<WaitingOrderVo> waitingOrderVoResult) throws Exception {
                        if (waitingOrderVoResult != null && waitingOrderVoResult.isSuccess() && waitingOrderVoResult.getData() != null && Constant.CODE_COMPLETE.equals(waitingOrderVoResult.getData().getCode())) {
                            hasMoreData = waitingOrderVoResult.getData().getInfo().isHasMore();
                            view.updateUi(waitingOrderVoResult.getData().getInfo().getList(), Constant.REFRESH_NORMAL);
                        } else {
                            view.requestFailure(Constant.REFRESH_NORMAL, waitingOrderVoResult.getData().getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.requestFailure(Constant.REFRESH_NORMAL, null);
                    }
                }));
    }


}
