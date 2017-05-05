package com.eme.waterdelivery.presenter;

import com.eme.waterdelivery.App;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.ApplyTicketRecordContract;
import com.eme.waterdelivery.model.bean.Result;
import com.eme.waterdelivery.model.bean.entity.SaleTicketRecordBo;
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
 * 售票记录
 *
 * Created by dijiaoliang on 17/3/7.
 */
public class ApplyTicketRecordPresenter implements ApplyTicketRecordContract.Presenter {

    private static final String TAG = ApplyTicketRecordPresenter.class.getSimpleName();

    private ApplyTicketRecordContract.View view;

    RetrofitHelper retrofitHelper;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private int pageNum;//页数
    private String storeId;
    private boolean hasMoreData;

    @Inject
    public ApplyTicketRecordPresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (ApplyTicketRecordContract.View) view;
        this.retrofitHelper = retrofitHelper;
        hasMoreData = true;
        pageNum = Constant.ZERO;
        storeId = SPBase.getContent(App.getAppInstance(), SpConstant.USER_FILE_NAME, SpConstant.USER_STORE_ID);
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
                retrofitHelper.getSellTicketByPage(storeId,pNum,Constant.PAGE_SIZE)
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
                .subscribe(new Consumer<Result<SaleTicketRecordBo>>() {
                    @Override
                    public void accept(Result<SaleTicketRecordBo> saleTicketRecordBoResult) throws Exception {
                        if(saleTicketRecordBoResult != null && saleTicketRecordBoResult.isSuccess() && saleTicketRecordBoResult.getData() != null && Constant.CODE_COMPLETE.equals(saleTicketRecordBoResult.getData().getCode())){
                            hasMoreData = saleTicketRecordBoResult.getData().getInfo().isHasMore();
                            view.showRequestListResult(true,saleTicketRecordBoResult.getData().getInfo().getList(),refreshFlag,saleTicketRecordBoResult.getData().getMessage());
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
                            String message=null;
                            if(saleTicketRecordBoResult != null && saleTicketRecordBoResult.isSuccess() && saleTicketRecordBoResult.getData() != null){
                                message=saleTicketRecordBoResult.getData().getMessage();
                            }
                            view.showRequestListResult(false,null,refreshFlag,message);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showRequestListResult(false,null,refreshFlag,null);
                    }
                })
        );
    }
}
