package com.eme.waterdelivery.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.BaseActivity;
import com.eme.waterdelivery.contract.LaunchContract;
import com.eme.waterdelivery.model.bean.entity.LoginBo;
import com.eme.waterdelivery.presenter.LaunchPresenter;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * 启动页
 * <p>
 * Created by dijiaoliang on 17/3/21.
 */
public class LaunchActivity extends BaseActivity<LaunchPresenter> implements LaunchContract.View {

    @BindView(R.id.ll_bg)
    LinearLayout llBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void initInject() {
        getViewComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_launch;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

    }

    @Override
    public void toLoginPage() {
        startActivity(new Intent(this, LoginActivity.class));
        mPresenter.unSubscribe();
        finish();
    }

    @Override
    public void toHomePage(LoginBo loginBo) {
        Intent intent = new Intent(this, HomeActivity.class);
        Bundle bundle=new Bundle();
        bundle.putParcelable(Constant.LOGIN_INFO,loginBo);
        intent.putExtras(bundle);
        startActivity(intent);
        mPresenter.unSubscribe();
        finish();
    }
}
