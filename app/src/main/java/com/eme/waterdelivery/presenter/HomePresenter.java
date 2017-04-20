package com.eme.waterdelivery.presenter;

import com.eme.waterdelivery.App;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.HomeContract;
import com.eme.waterdelivery.event.CompleteNumEvent;
import com.eme.waterdelivery.model.bean.Result;
import com.eme.waterdelivery.model.bean.entity.HistoryOrderSumBo;
import com.eme.waterdelivery.model.net.RetrofitHelper;
import com.eme.waterdelivery.model.sp.SPBase;
import com.eme.waterdelivery.model.sp.SpConstant;
import com.eme.waterdelivery.tools.RxBus2;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by dijiaoliang on 17/3/2.
 */
public class HomePresenter implements HomeContract.Presenter {

    private static final String TAG = HomePresenter.class.getSimpleName();

    private HomeContract.View view;

    Disposable disposable;

    RetrofitHelper retrofitHelper;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private String storeId;

    @Inject
    public HomePresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (HomeContract.View) view;
        this.retrofitHelper = retrofitHelper;
    }

    @Override
    public void subscribe() {
        //请求数据
        storeId = SPBase.getContent(App.getAppInstance(), SpConstant.USER_FILE_NAME, SpConstant.USER_STORE_ID);
        RxBus2.getInstance().toObservable(CompleteNumEvent.class).subscribe(new Observer<CompleteNumEvent>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onNext(CompleteNumEvent value) {
                view.updateNavSum(value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

//        checkVersion();

    }

    @Override
    public void unSubscribe() {
        view = null;
        retrofitHelper = null;
        disposables.clear();
    }

    @Override
    public void requestCompleteNumber() {
        disposables.add(
                retrofitHelper.getHistoryOrderSum(storeId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Result<HistoryOrderSumBo>>() {
                            @Override
                            public void accept(Result<HistoryOrderSumBo> historyOrderSumBoResult) throws Exception {
                                if (historyOrderSumBoResult != null && historyOrderSumBoResult.isSuccess() && historyOrderSumBoResult.getData() != null && Constant.CODE_COMPLETE.equals(historyOrderSumBoResult.getData().getCode())) {
                                    view.updateOrderSum(historyOrderSumBoResult.getData().getInfo());
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                            }
                        })

        );
    }

}
