package com.eme.waterdelivery.presenter;

import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.AssessMoneyContract;
import com.eme.waterdelivery.model.bean.AResult;
import com.eme.waterdelivery.model.bean.entity.AssessMoneyVo;
import com.eme.waterdelivery.model.net.RetrofitHelper;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 应缴金额
 *
 * Created by dijiaoliang on 17/3/2.
 */
public class AssessMoneyPresenter implements AssessMoneyContract.Presenter {

    private static final String TAG=AssessMoneyPresenter.class.getSimpleName();

    private AssessMoneyContract.View view;

    RetrofitHelper retrofitHelper;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public AssessMoneyPresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (AssessMoneyContract.View)view;
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
                retrofitHelper.cashStatements()
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
                .subscribe(new Consumer<AResult<AssessMoneyVo>>() {
                    @Override
                    public void accept(AResult<AssessMoneyVo> assessMoneyVoAResult) throws Exception {
                        if(assessMoneyVoAResult!=null && assessMoneyVoAResult.isSuccess() && assessMoneyVoAResult.getData()!=null){
                            view.showRequestResult(true,assessMoneyVoAResult.getData(),flag);
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
