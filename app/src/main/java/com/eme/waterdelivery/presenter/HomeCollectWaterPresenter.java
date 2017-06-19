package com.eme.waterdelivery.presenter;

import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.HomeCollectWaterContract;
import com.eme.waterdelivery.model.bean.AResult;
import com.eme.waterdelivery.model.bean.entity.TrafficVo;
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
public class HomeCollectWaterPresenter implements HomeCollectWaterContract.Presenter {

    private static final String TAG = HomeCollectWaterPresenter.class.getSimpleName();

    private HomeCollectWaterContract.View view;

    RetrofitHelper retrofitHelper;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public HomeCollectWaterPresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (HomeCollectWaterContract.View) view;
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
     * 获取运单列表
     * @param pageNo
     */
    @Override
    public void requestTrafficList(int pageNo) {
        disposables.add(
            retrofitHelper.getTrafficList(pageNo)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            view.showProgress(true);
                        }
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<AResult<TrafficVo>>() {
                        @Override
                        public void accept(AResult<TrafficVo> trafficAResult) throws Exception {
                            if(trafficAResult!=null && trafficAResult.isSuccess() &&trafficAResult.getData()!=null && trafficAResult.getData().getResults()!=null){
                                view.showRequestResult(true,trafficAResult.getData().getResults(),null);
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

    @Override
    public void requestTrafficListRefresh(int pageNo) {
        disposables.add(
                retrofitHelper.getTrafficList(pageNo)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<AResult<TrafficVo>>() {
                            @Override
                            public void accept(AResult<TrafficVo> trafficAResult) throws Exception {
                                if(trafficAResult!=null && trafficAResult.isSuccess() &&trafficAResult.getData()!=null && trafficAResult.getData().getResults()!=null){
                                    view.showRequestResult(true,trafficAResult.getData().getResults(),null);
                                }else{
                                    view.showRequestResult(false,null,null);
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                view.showRequestResult(false,null,null);
                            }
                        })
        );
    }
}
