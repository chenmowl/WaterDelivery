package com.eme.waterdelivery.presenter;

import android.text.TextUtils;

import com.eme.waterdelivery.App;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.LoginContract;
import com.eme.waterdelivery.model.bean.Result;
import com.eme.waterdelivery.model.bean.entity.LoginBo;
import com.eme.waterdelivery.model.net.RetrofitHelper;
import com.eme.waterdelivery.model.sp.SPBase;
import com.eme.waterdelivery.model.sp.SpConstant;
import com.eme.waterdelivery.tools.KeyboardUtils;
import com.eme.waterdelivery.tools.NetworkUtils;
import com.eme.waterdelivery.ui.LoginActivity;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dijiaoliang on 17/3/2.
 */
public class LoginPresenter implements LoginContract.Presenter {

    private static final String TAG=LoginPresenter.class.getSimpleName();

    private LoginContract.View view;

    private final CompositeDisposable disposables = new CompositeDisposable();

    RetrofitHelper retrofitHelper;

    @Inject
    public LoginPresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (LoginContract.View)view;
        this.retrofitHelper=retrofitHelper;
    }

    @Override
    public void subscribe() {
        //请求数据
    }

    private void doLogin(String uid,String password) {
        if(!NetworkUtils.isConnected(App.getAppInstance())){
            ((LoginActivity)view).showNetError();
            return;
        }
        disposables.add(retrofitHelper.pwdLogin(uid,password)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        view.showProgress(true);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Result<LoginBo>>() {
                    @Override
                    public void accept(Result<LoginBo> loginBoResult) throws Exception {
                        checkResult(loginBoResult);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showRequestError(null);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                    view.showProgress(false);
                    }
                }));
    }

    /**
     * 检验并处理请求结果
     * @param loginInfoResult
     */
    void checkResult(Result<LoginBo> loginInfoResult){
        if(loginInfoResult==null){
            view.showRequestError(null);
            return;
        }
        if(loginInfoResult.isSuccess()){
            LoginBo info= loginInfoResult.getData().getInfo();
            if(Constant.CODE_COMPLETE.equals(loginInfoResult.getData().getCode())){
                //响应码为10000，说明逻辑正确
                //数据返回正确的话直接存储用户信息
                SPBase.setContent(App.getAppInstance(),SpConstant.HEAD_FILE_NAME,SpConstant.HEAD_COOKIE_UID, info.getUsername());
                SPBase.setContent(App.getAppInstance(),SpConstant.HEAD_FILE_NAME,SpConstant.HEAD_COOKIE_SIG, info.getSig());
                SPBase.setContent(App.getAppInstance(),SpConstant.USER_FILE_NAME,SpConstant.USER_STORE_ID, info.getStoreId());
                view.toHome(info);
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
        view=null;
        retrofitHelper=null;
        disposables.clear();
    }

    @Override
    public void login(String username, String password) {
        //首先隐藏软键盘
        KeyboardUtils.hideSoftInput((LoginActivity)view);
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
