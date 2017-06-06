package com.eme.waterdelivery.presenter;

import com.eme.waterdelivery.App;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.ApplyTicketContract;
import com.eme.waterdelivery.model.bean.Result;
import com.eme.waterdelivery.model.bean.StatusResult;
import com.eme.waterdelivery.model.bean.entity.GetActiveInfoByTicketBo;
import com.eme.waterdelivery.model.bean.entity.GetAddressByPhoneBo;
import com.eme.waterdelivery.model.bean.entity.GetQRCodeBo;
import com.eme.waterdelivery.model.bean.entity.GetTicketInfoBo;
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
 * 申请购票
 *
 * Created by dijiaoliang on 17/3/7.
 */
public class ApplyTicketPresenter implements ApplyTicketContract.Presenter {

    private static final String TAG = ApplyTicketPresenter.class.getSimpleName();

    private ApplyTicketContract.View view;

    RetrofitHelper retrofitHelper;

    private String storeId;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public ApplyTicketPresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (ApplyTicketContract.View) view;
        this.retrofitHelper = retrofitHelper;
        storeId= SPBase.getContent(App.getAppInstance(), SpConstant.USER_FILE_NAME,SpConstant.USER_STORE_ID);
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
    public void requestAddress(String phoneNumber) {
        disposables.add(
                retrofitHelper.getAddressByPhone(phoneNumber)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        view.showProgress(true);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Result<GetAddressByPhoneBo>>() {
                    @Override
                    public void accept(Result<GetAddressByPhoneBo> getAddressByPhoneBoResult) throws Exception {
                        if(getAddressByPhoneBoResult!=null && getAddressByPhoneBoResult.isSuccess() &&getAddressByPhoneBoResult.getData()!=null && Constant.CODE_COMPLETE.equals(getAddressByPhoneBoResult.getData().getCode())){
                            view.showRequestAddressResult(true,getAddressByPhoneBoResult.getData().getInfo().getList(),null);
                        }else{
                            if(getAddressByPhoneBoResult!=null && getAddressByPhoneBoResult.isSuccess() &&getAddressByPhoneBoResult.getData()!=null){
                                view.showRequestAddressResult(false,null,getAddressByPhoneBoResult.getData().getMessage());
                            }else{
                                view.showRequestAddressResult(false,null,null);
                            }
                        }
                        view.showProgress(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showRequestAddressResult(false,null,null);
                        view.showProgress(false);
                    }
                })
        );
    }

    /**
     * 这里默认只申请纸质水票
     */
    @Override
    public void requestTicketList() {
        disposables.add(
                retrofitHelper.getTicketInfo(Constant.TICKET_TYPE_PAPER)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        view.showProgress(true);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Result<GetTicketInfoBo>>() {
                    @Override
                    public void accept(Result<GetTicketInfoBo> getTicketInfoBoResult) throws Exception {
                        if(getTicketInfoBoResult!=null && getTicketInfoBoResult.isSuccess() &&getTicketInfoBoResult.getData()!=null && Constant.CODE_COMPLETE.equals(getTicketInfoBoResult.getData().getCode())){
                            view.showRequestTicketResult(true,getTicketInfoBoResult.getData().getInfo().getList(),getTicketInfoBoResult.getData().getMessage());
                        }else{
                            if(getTicketInfoBoResult!=null && getTicketInfoBoResult.isSuccess() &&getTicketInfoBoResult.getData()!=null){
                                view.showRequestTicketResult(false,null,getTicketInfoBoResult.getData().getMessage());
                            }else{
                                view.showRequestTicketResult(false,null,null);
                            }
                        }
                        view.showProgress(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showRequestTicketResult(false,null,null);
                        view.showProgress(false);
                    }
                })

        );
    }

    @Override
    public void getTicketActivityInfo(String ticketsModel, String tickets) {
        disposables.add(
                retrofitHelper.getActiveInfoByTicket(ticketsModel,tickets)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        view.showProgress(true);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Result<GetActiveInfoByTicketBo>>() {
                    @Override
                    public void accept(Result<GetActiveInfoByTicketBo> getActiveInfoByTicketBoResult) throws Exception {
                        if(getActiveInfoByTicketBoResult!=null && getActiveInfoByTicketBoResult.isSuccess() && getActiveInfoByTicketBoResult.getData()!=null && Constant.CODE_COMPLETE.equals(getActiveInfoByTicketBoResult.getData().getCode())&& getActiveInfoByTicketBoResult.getData().getInfo()!=null){
                            view.showRequestTicketActivityInfoResult(true,getActiveInfoByTicketBoResult.getData().getInfo().getList(),getActiveInfoByTicketBoResult.getData().getMessage());
                        }else{
                            if(getActiveInfoByTicketBoResult!=null && getActiveInfoByTicketBoResult.isSuccess() && getActiveInfoByTicketBoResult.getData()!=null){
                                view.showRequestTicketActivityInfoResult(false,null,getActiveInfoByTicketBoResult.getData().getMessage());
                            }else{
                                view.showRequestTicketActivityInfoResult(false,null,null);
                            }
                        }
                        view.showProgress(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showRequestTicketActivityInfoResult(false,null,null);
                        view.showProgress(false);
                    }
                })
        );
    }

    @Override
    public void getWXImage() {
        disposables.add(
                retrofitHelper.getQRCode(storeId)
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                view.showProgress(true);
                            }
                        })
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
                                view.showProgress(false);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                view.getImageFailure(null);
                                view.showProgress(false);
                            }
                        })
        );
    }

    @Override
    public void applyTicketSubmit(String memberName, String memberPhone, String memberAddress, String invoiceTitle, String ticketsModel, String payType,String payStatus, String memberAdressId, String tickets) {
        disposables.add(
                retrofitHelper.applyTicketSubmit(storeId,memberName,memberPhone,memberAddress,invoiceTitle,ticketsModel,payType,payStatus,memberAdressId,tickets)
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
                            view.showTicketSubmitResult(true,statusResult.getData().getMessage());
                        }else{
                            if(statusResult!=null && statusResult.isSuccess() && statusResult.getData()!=null){
                                view.showTicketSubmitResult(false,statusResult.getData().getMessage());
                            }else{
                                view.showTicketSubmitResult(false,null);
                            }
                        }
                        view.showProgress(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showTicketSubmitResult(false,null);
                        view.showProgress(false);
                    }
                })
        );
    }
}
