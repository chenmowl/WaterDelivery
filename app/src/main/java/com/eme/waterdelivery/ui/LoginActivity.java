package com.eme.waterdelivery.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.BaseActivity;
import com.eme.waterdelivery.contract.LoginContract;
import com.eme.waterdelivery.model.bean.entity.LoginBo;
import com.eme.waterdelivery.presenter.LoginPresenter;
import com.eme.waterdelivery.tools.ToastUtil;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;


/**
 * 用户登录
 *
 * Created by dijiaoliang on 17/3/6.
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    private static final String TAG=LoginActivity.class.getSimpleName();

    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_login_username)
    EditText etLoginUsername;
    @BindView(R.id.til_username)
    TextInputLayout tilUsername;
    @BindView(R.id.et_login_password)
    EditText etLoginPassword;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    @BindView(R.id.ll_input)
    LinearLayout llInput;
    @BindView(R.id.ll_av_loading_transparent_44)
    LinearLayout llAvLoadingTransparent44;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void initInject() {
        getViewComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        //初始化并设置控件监听
        back.setVisibility(View.GONE);
        tvTitle.setText(getText(R.string.login));
        isShowLayer(llAvLoadingTransparent44, false);

        //设置btn监听,防止连续的二次点击
        RxView.clicks(btnLogin)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mPresenter.login(etLoginUsername.getText().toString(),etLoginPassword.getText().toString());
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void showUsernameError(boolean isEmpty) {
        if(isEmpty){
            ToastUtil.shortToast(this,getText(R.string.empty_username).toString());
        }else{
            ToastUtil.shortToast(this,getText(R.string.error_username).toString());
        }
    }

    @Override
    public void showPasswordError(boolean isEmpty) {
        if(isEmpty){
            ToastUtil.shortToast(this,getText(R.string.empty_password).toString());
        }else{
            ToastUtil.shortToast(this,getText(R.string.error_password).toString());
        }
    }

    @Override
    public void toHome(LoginBo info) {
        Intent intent=new Intent(this,HomeActivity.class);
        Bundle bundle=new Bundle();
        bundle.putParcelable(Constant.LOGIN_INFO,info);
        intent.putExtras(bundle);
        startActivity(intent);
        mPresenter.unSubscribe();
        finish();
    }

    @Override
    public void showRequestError(String info) {
        if(!TextUtils.isEmpty(info)){
            ToastUtil.shortToast(this,info);
            etLoginPassword.setText(Constant.STR_EMPTY);
        }else{
            ToastUtil.shortToast(this,getText(R.string.error_request).toString());
            etLoginPassword.setText(Constant.STR_EMPTY);
        }
    }

    @Override
    public void showProgress(boolean b) {
        isShowLayer(llAvLoadingTransparent44,b);
    }

    @OnTextChanged({R.id.et_login_username,R.id.et_login_password})
    void textChange(){
        if(TextUtils.isEmpty(etLoginUsername.getText().toString()) || TextUtils.isEmpty(etLoginPassword.getText().toString())){
            btnLogin.setBackgroundResource(R.drawable.btn_status_unable);
            btnLogin.setEnabled(false);
        }else{
            btnLogin.setBackgroundResource(R.drawable.btn_status_enable);
            btnLogin.setEnabled(true);
        }
    }
}
