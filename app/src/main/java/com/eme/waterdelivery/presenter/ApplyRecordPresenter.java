package com.eme.waterdelivery.presenter;

import com.eme.waterdelivery.App;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.ApplyRecordContract;
import com.eme.waterdelivery.model.bean.Result;
import com.eme.waterdelivery.model.bean.entity.HistoryPurchaseVo;
import com.eme.waterdelivery.model.net.RetrofitHelper;
import com.eme.waterdelivery.model.sp.SPBase;
import com.eme.waterdelivery.model.sp.SpConstant;
import com.eme.waterdelivery.tools.NetworkUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dijiaoliang on 17/3/2.
 */
public class ApplyRecordPresenter implements ApplyRecordContract.Presenter {

    private static final String TAG=ApplyRecordPresenter.class.getSimpleName();

    private ApplyRecordContract.View view;

    RetrofitHelper retrofitHelper;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private int pageNum;//页数
    private String storeId;
    private boolean hasMoreData;

    @Inject
    public ApplyRecordPresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (ApplyRecordContract.View)view;
        this.retrofitHelper=retrofitHelper;
    }

    @Override
    public void subscribe() {
        //请求数据
        hasMoreData = true;
        pageNum = Constant.ZERO;
        storeId = SPBase.getContent(App.getAppInstance(), SpConstant.USER_FILE_NAME, SpConstant.USER_STORE_ID);
//        requestData(Constant.REFRESH_NORMAL);
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
                pNum = pageNum + Constant.ONE;
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
        disposables.add(
                retrofitHelper.getHistoryPurchaseOrders(storeId, pNum, Constant.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
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
                .subscribe(new Consumer<Result<HistoryPurchaseVo>>() {
                    @Override
                    public void accept(Result<HistoryPurchaseVo> historyPurchaseVoResult) throws Exception {
                        if(historyPurchaseVoResult!=null && historyPurchaseVoResult.isSuccess() && historyPurchaseVoResult.getData()!=null && Constant.CODE_COMPLETE.equals(historyPurchaseVoResult.getData().getCode())){
                            hasMoreData = historyPurchaseVoResult.getData().getInfo().isHasMore();
                            HistoryPurchaseVo historyPurchaseVo=historyPurchaseVoResult.getData().getInfo();
                            view.updateUi(historyPurchaseVo, refreshFlag);
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
                        }else{
                            view.requestFailure(refreshFlag, historyPurchaseVoResult.getData().getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.requestFailure(refreshFlag, null);
                    }
                })
        );
    }
}
