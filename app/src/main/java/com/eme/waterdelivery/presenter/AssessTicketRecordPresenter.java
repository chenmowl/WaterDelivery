package com.eme.waterdelivery.presenter;

import com.eme.waterdelivery.App;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.AssessTicketRecordContract;
import com.eme.waterdelivery.model.bean.AResult;
import com.eme.waterdelivery.model.bean.entity.AssessTicketRecordVo;
import com.eme.waterdelivery.model.net.RetrofitHelper;
import com.eme.waterdelivery.tools.NetworkUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * 应缴水票记录
 * Created by dijiaoliang on 17/3/7.
 */
public class AssessTicketRecordPresenter implements AssessTicketRecordContract.Presenter {

    private static final String TAG = AssessTicketRecordPresenter.class.getSimpleName();

    private AssessTicketRecordContract.View view;

    RetrofitHelper retrofitHelper;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private int pageNum;//页数
    private boolean hasMoreData;

    @Inject
    public AssessTicketRecordPresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (AssessTicketRecordContract.View) view;
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
        disposables.add(
                retrofitHelper.ticketsStatementRecord(pNum)
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
                .subscribe(new Consumer<AResult<AssessTicketRecordVo>>() {
                    @Override
                    public void accept(AResult<AssessTicketRecordVo> assessTicketRecordVoAResult) throws Exception {
                        if (assessTicketRecordVoAResult != null && assessTicketRecordVoAResult.isSuccess() && assessTicketRecordVoAResult.getData() != null ) {
                            hasMoreData = assessTicketRecordVoAResult.getData().isHasMore();
                            view.updateUi(assessTicketRecordVoAResult.getData().getResults(), refreshFlag);
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

}
