package com.eme.waterdelivery.presenter;

import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.AssessTicketContract;
import com.eme.waterdelivery.model.bean.AResult;
import com.eme.waterdelivery.model.bean.entity.AssessTicketVo;
import com.eme.waterdelivery.model.net.RetrofitHelper;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 应缴水票
 *
 * Created by dijiaoliang on 17/3/2.
 */
public class AssessTicketPresenter implements AssessTicketContract.Presenter {

    private static final String TAG=AssessTicketPresenter.class.getSimpleName();

    private AssessTicketContract.View view;

    RetrofitHelper retrofitHelper;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public AssessTicketPresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (AssessTicketContract.View)view;
        this.retrofitHelper=retrofitHelper;
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

    /**
     * 请求列表数据
     */
    public void requestData(final int flag) {
        disposables.add(
                retrofitHelper.ticketsStatements()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if(flag==Constant.REFRESH_NORMAL){
                            view.showProgress(true);
                        }
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AResult<AssessTicketVo>>() {
                    @Override
                    public void accept(AResult<AssessTicketVo> assessTicketVoAResult) throws Exception {
                        if(assessTicketVoAResult!=null && assessTicketVoAResult.isSuccess() && assessTicketVoAResult.getData()!=null){
                            view.showRequestResult(true,assessTicketVoAResult.getData(),flag);
                        }else{
                            view.showRequestResult(false,null,flag);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showRequestResult(false,null,flag);
                    }
                })

        );
    }
}
