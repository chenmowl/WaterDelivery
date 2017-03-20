package com.eme.waterdelivery.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.eme.waterdelivery.App;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.LoginContract;
import com.eme.waterdelivery.model.bean.Result;
import com.eme.waterdelivery.model.bean.entity.LoginInfo;
import com.eme.waterdelivery.model.bean.entity.LoginVo;
import com.eme.waterdelivery.model.net.ApiConstant;
import com.eme.waterdelivery.model.net.RetrofitHelper;
import com.eme.waterdelivery.model.sp.SPBase;
import com.eme.waterdelivery.model.sp.SpConstant;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by dijiaoliang on 17/3/2.
 */
public class LoginPresenter implements LoginContract.Presenter {

    private static final String TAG=LoginPresenter.class.getSimpleName();

    private LoginContract.View view;

    private CompositeSubscription compositeSubscription;

    RetrofitHelper retrofitHelper;

    @Inject
    public LoginPresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (LoginContract.View)view;
        this.retrofitHelper=retrofitHelper;
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void subscribe() {
        //请求数据
    }

    private void doLogin(String uid,String password) {
        Subscription subscription = retrofitHelper.pwdLogin(uid,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Result<LoginVo>>() {
                    @Override
                    public void call(Result<LoginVo> loginVoResult) {
                        checkResult(loginVoResult);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, throwable.toString());
                        view.showRequestError(null);
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "complete");
                    }
                });
        compositeSubscription.add(subscription);
    }

    /**
     * 检验并处理请求结果
     * @param loginInfoResult
     */
    void checkResult(Result<LoginVo> loginInfoResult){
        if(loginInfoResult==null){
            view.showRequestError(null);
            return;
        }
        if(loginInfoResult.isSuccess()){
            LoginInfo info= loginInfoResult.getData().getInfo();
            if(ApiConstant.CODE_COMPLETE.equals(loginInfoResult.getData().getCode())){
                //响应码为10000，说明逻辑正确
                //数据返回正确的话直接存储用户信息
                SPBase.setContent(App.getAppInstance(),SpConstant.HEAD_FILE_NAME,SpConstant.HEAD_COOKIE_UID, info.getUsername());
                SPBase.setContent(App.getAppInstance(),SpConstant.HEAD_FILE_NAME,SpConstant.HEAD_COOKIE_SIG, info.getSig());
                view.toHome();
            }else{
                view.showRequestError(loginInfoResult.getData().getMessage());
            }
        }else{
            //success为false 无关业务逻辑，只是服务器异常
            view.showRequestError(loginInfoResult.getData().getMessage());
        }
    }

    @Override
    public void unSubscribe() {
        compositeSubscription.clear();
    }

    @Override
    public void login(String username, String password) {
        //判断数据是否为空
        if(TextUtils.isEmpty(username)){
            view.showUsernameError(true);
            return;
        }
        if(TextUtils.isEmpty(password)){
            view.showPasswordError(true);
        }
//        view.toHome();
        doLogin(username,password);
    }

}
