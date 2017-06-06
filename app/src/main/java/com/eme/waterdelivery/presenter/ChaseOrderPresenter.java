package com.eme.waterdelivery.presenter;

import com.eme.waterdelivery.App;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.ChaseOrderContract;
import com.eme.waterdelivery.model.bean.AResult;
import com.eme.waterdelivery.model.bean.AStatusResult;
import com.eme.waterdelivery.model.bean.entity.ChaseOrderVo;
import com.eme.waterdelivery.model.net.RetrofitHelper;
import com.eme.waterdelivery.tools.NetworkUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by dijiaoliang on 17/3/7.
 */
public class ChaseOrderPresenter implements ChaseOrderContract.Presenter {

    private static final String TAG = ChaseOrderPresenter.class.getSimpleName();

    private ChaseOrderContract.View view;

    RetrofitHelper retrofitHelper;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private int pageNum;//页数
    private boolean hasMoreData;

    @Inject
    public ChaseOrderPresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (ChaseOrderContract.View) view;
        this.retrofitHelper = retrofitHelper;
    }

    @Override
    public void subscribe() {
        hasMoreData = true;
        pageNum = Constant.ZERO;
        requestData(Constant.REFRESH_NORMAL);
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
                retrofitHelper.getCrefditRecords(String.valueOf(pNum))
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
                .subscribe(new Consumer<AResult<ChaseOrderVo>>() {
                    @Override
                    public void accept(AResult<ChaseOrderVo> chaseOrderVoAResult) throws Exception {
                        if(chaseOrderVoAResult!=null && chaseOrderVoAResult.isSuccess() && chaseOrderVoAResult.getData()!=null && chaseOrderVoAResult.getData().getResults()!=null){
                            hasMoreData = chaseOrderVoAResult.getData().isHasMore();
                            view.updateUi(chaseOrderVoAResult.getData().getResults(), refreshFlag);
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
                            view.requestFailure(refreshFlag);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.requestFailure(refreshFlag);
                    }
                })
        );
    }

    /**
     * 欠款完结接口
     * confirmCrefdit
     * @param id
     */
    @Override
    public void confirmCrefdit(String id) {
        disposables.add(
                retrofitHelper.confirmCrefdit(id)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        view.showProgress(true);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AStatusResult>() {
                    @Override
                    public void accept(AStatusResult aStatusResult) throws Exception {
                        if(aStatusResult!=null && aStatusResult.isSuccess() && aStatusResult.getData()==Constant.ONE){
                            view.showConfirmCrefditResult(true);
                        }else{
                            view.showConfirmCrefditResult(false);
                        }
                        view.showProgress(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showConfirmCrefditResult(false);
                        view.showProgress(false);
                    }
                })

        );
    }
}
