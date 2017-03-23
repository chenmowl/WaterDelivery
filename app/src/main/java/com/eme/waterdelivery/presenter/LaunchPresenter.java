package com.eme.waterdelivery.presenter;

import android.text.TextUtils;

import com.eme.waterdelivery.App;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.base.BaseView;
import com.eme.waterdelivery.contract.LaunchContract;
import com.eme.waterdelivery.model.bean.Result;
import com.eme.waterdelivery.model.bean.entity.LoginBo;
import com.eme.waterdelivery.model.net.RetrofitHelper;
import com.eme.waterdelivery.model.sp.SPBase;
import com.eme.waterdelivery.model.sp.SpConstant;
import com.eme.waterdelivery.tools.NetworkUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dijiaoliang on 17/3/2.
 */
public class LaunchPresenter implements LaunchContract.Presenter {

    private static final String TAG = LaunchPresenter.class.getSimpleName();

    private LaunchContract.View view;

    RetrofitHelper retrofitHelper;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public LaunchPresenter(BaseView view, RetrofitHelper retrofitHelper) {
        this.view = (LaunchContract.View) view;
        this.retrofitHelper = retrofitHelper;
    }

    @Override
    public void subscribe() {
        //判断是否有cookie_uid和cookie_sig,有的话直接走cookie登录,如果没有直接进入密码登录页面
        String cookieUid = SPBase.getContent(App.getAppInstance(), SpConstant.HEAD_FILE_NAME, SpConstant.HEAD_COOKIE_UID);
        String cookieSig = SPBase.getContent(App.getAppInstance(), SpConstant.HEAD_FILE_NAME, SpConstant.HEAD_COOKIE_SIG);
        if (TextUtils.isEmpty(cookieUid) || TextUtils.isEmpty(cookieSig)) {
            //直接进入密码登录页面
            view.toLoginPage();
        } else {
            if (!NetworkUtils.isConnected(App.getAppInstance())) {
                view.toLoginPage();
                return;
            }
            //cookie登录，成功直接进入首页失败进入密码登录页面
            disposables.add(retrofitHelper.cookieLogin()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Result<LoginBo>>() {
                        @Override
                        public void accept(Result<LoginBo> loginBoResult) throws Exception {
                            checkResult(loginBoResult);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            view.toLoginPage();
                        }
                    }));
        }
    }

    /**
     * 检验并处理请求结果
     *
     * @param loginInfoResult
     */
    void checkResult(Result<LoginBo> loginInfoResult) {
        if (loginInfoResult == null || !loginInfoResult.isSuccess() || !Constant.CODE_COMPLETE.equals(loginInfoResult.getData().getCode())) {
            //直接进入密码登录页面
            view.toLoginPage();
            return;
        }
        LoginBo info = loginInfoResult.getData().getInfo();
        //响应码为10000，说明逻辑正确
        //数据返回正确的话直接存储用户信息
        SPBase.setContent(App.getAppInstance(), SpConstant.HEAD_FILE_NAME, SpConstant.HEAD_COOKIE_UID, info.getUsername());
        SPBase.setContent(App.getAppInstance(), SpConstant.HEAD_FILE_NAME, SpConstant.HEAD_COOKIE_SIG, info.getSig());
        SPBase.setContent(App.getAppInstance(), SpConstant.USER_FILE_NAME, SpConstant.USER_STORE_ID, info.getStoreId());
        view.toHomePage(info);
    }


    @Override
    public void unSubscribe() {
        view=null;
        retrofitHelper=null;
        disposables.clear();
    }
}
