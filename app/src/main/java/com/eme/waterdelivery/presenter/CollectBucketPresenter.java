package com.eme.waterdelivery.presenter;

import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.CollectBucketContract;
import com.eme.waterdelivery.model.bean.AResult;
import com.eme.waterdelivery.model.bean.entity.BackBarrelBo;
import com.eme.waterdelivery.model.net.RetrofitHelper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by dijiaoliang on 17/3/7.
 */
public class CollectBucketPresenter implements CollectBucketContract.Presenter {

    private static final String TAG = CollectBucketPresenter.class.getSimpleName();

    private CollectBucketContract.View view;

    RetrofitHelper retrofitHelper;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public CollectBucketPresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (CollectBucketContract.View) view;
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


    @Override
    public void requestBucketData() {
        disposables.add(
                retrofitHelper.backBarrel()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        view.showProgress(true);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AResult<List<BackBarrelBo>>>() {
                    @Override
                    public void accept(AResult<List<BackBarrelBo>> listAResult) throws Exception {
                        if(listAResult!=null && listAResult.isSuccess() && listAResult.getData()!=null){
                            view.showRequestResult(true,listAResult.getData());
                        }else{
                            view.showRequestResult(true,null);
                        }
                        view.showProgress(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showRequestResult(true,null);
                        view.showProgress(false);
                    }
                })
        );
    }
}
