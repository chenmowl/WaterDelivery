package com.eme.waterdelivery.presenter;

import com.eme.waterdelivery.App;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.WaterSignContract;
import com.eme.waterdelivery.model.bean.Result;
import com.eme.waterdelivery.model.bean.StatusResult;
import com.eme.waterdelivery.model.bean.entity.GetQRCodeBo;
import com.eme.waterdelivery.model.net.RetrofitHelper;
import com.eme.waterdelivery.model.sp.SPBase;
import com.eme.waterdelivery.model.sp.SpConstant;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by dijiaoliang on 17/3/7.
 */
public class WaterSignPresenter implements WaterSignContract.Presenter {

    private static final String TAG = WaterSignPresenter.class.getSimpleName();

    private WaterSignContract.View view;

    RetrofitHelper retrofitHelper;
    private String storeId;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public WaterSignPresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (WaterSignContract.View) view;
        this.retrofitHelper = retrofitHelper;
        storeId= SPBase.getContent(App.getAppInstance(),SpConstant.USER_FILE_NAME,SpConstant.USER_STORE_ID);
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
    public void getWXImage() {
        disposables.add(
                retrofitHelper.getQRCode(storeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Result<GetQRCodeBo>>() {
                    @Override
                    public void accept(Result<GetQRCodeBo> getQRCodeBoResult) throws Exception {
                        if(getQRCodeBoResult!=null && getQRCodeBoResult.isSuccess() && getQRCodeBoResult.getData()!=null && Constant.CODE_COMPLETE.equals(getQRCodeBoResult.getData().getCode())){
                            GetQRCodeBo code=getQRCodeBoResult.getData().getInfo();
                            view.loadWXImage(code.getQrCode());
                        }else{
                            String message = null;
                            if(getQRCodeBoResult!=null && getQRCodeBoResult.isSuccess() && getQRCodeBoResult.getData()!=null && Constant.CODE_COMPLETE.equals(getQRCodeBoResult.getData().getCode())){
                                message=getQRCodeBoResult.getData().getMessage();
                            }
                            view.getImageFailure(message);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.getImageFailure(null);
                    }
                })
        );
    }

    @Override
    public void sign(String orderId, String payType, String payAmountGoods) {
        disposables.add(
                retrofitHelper.orderSign(orderId,payType,payAmountGoods)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        view.showProgress(true);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<StatusResult>() {
                    @Override
                    public void accept(StatusResult statusResult) throws Exception {
                        if(statusResult!=null && statusResult.isSuccess() && statusResult.getData()!=null && Constant.CODE_COMPLETE.equals(statusResult.getData().getCode())){
                            view.showSignResult(true,statusResult.getData().getMessage());
                        }else{
                            view.showSignResult(false,null);
                        }
                        view.showProgress(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showProgress(false);
                        view.showSignResult(false,null);
                    }
                })
        );
    }
}
