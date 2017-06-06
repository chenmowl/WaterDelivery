package com.eme.waterdelivery.presenter;

import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.CollectWaterContract;
import com.eme.waterdelivery.model.bean.AResult;
import com.eme.waterdelivery.model.bean.AStatusResult;
import com.eme.waterdelivery.model.bean.entity.TrafficDetailVo;
import com.eme.waterdelivery.model.net.RetrofitHelper;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by dijiaoliang on 17/3/7.
 */
public class CollectWaterPresenter implements CollectWaterContract.Presenter {

    private static final String TAG = CollectWaterPresenter.class.getSimpleName();

    private CollectWaterContract.View view;

    RetrofitHelper retrofitHelper;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public CollectWaterPresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (CollectWaterContract.View) view;
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

    /**
     * 运单详情
     * @param trafficNo
     */
    @Override
    public void requestTrafficDetail(String trafficNo) {
        disposables.add(
                retrofitHelper.getTrafficDetail(trafficNo)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        view.showProgress(true);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AResult<TrafficDetailVo>>() {
                    @Override
                    public void accept(AResult<TrafficDetailVo> trafficDetailAResult) throws Exception {
                        if(trafficDetailAResult!=null && trafficDetailAResult.isSuccess() &&trafficDetailAResult.getData()!=null && trafficDetailAResult.getData().getGoods()!=null){
                            view.showRequestResult(true,trafficDetailAResult.getData().getGoods(),null);
                        }else{
                            view.showRequestResult(false,null,null);
                        }
                        view.showProgress(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showRequestResult(false,null,null);
                        view.showProgress(false);
                    }
                })
        );
    }

    /**
     * 运单确认
     * @param trafficNo
     */
    @Override
    public void handleTraffic(String trafficNo) {
        disposables.add(
                retrofitHelper.confirmTraffic(trafficNo)
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
                                if(aStatusResult!=null && aStatusResult.isSuccess() && aStatusResult.getData()== Constant.ONE){
                                    view.showConfirmResult(true);
                                }else{
                                    view.showConfirmResult(false);
                                }
                                view.showProgress(false);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                view.showConfirmResult(false);
                                view.showProgress(false);
                            }
                        })
        );
    }


}
