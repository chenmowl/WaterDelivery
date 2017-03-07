package com.eme.waterdelivery.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.eme.waterdelivery.App;
import com.eme.waterdelivery.injector.component.DaggerViewComponent;
import com.eme.waterdelivery.injector.component.ViewComponent;
import com.eme.waterdelivery.injector.module.ViewModule;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 *
 */
public abstract class BaseActivity<T extends BasePresenter> extends SupportActivity implements BaseView {

    @Inject
    protected T mPresenter;
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mUnBinder = ButterKnife.bind(this);
        initInject();
        if (mPresenter != null)
            mPresenter.subscribe();
        initEventAndData();
    }

    protected void setToolBar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressedSupport();
            }
        });
    }

    protected ViewComponent getViewComponent() {
        return DaggerViewComponent.builder()
                .appComponent(App.getAppInstance().getAppComponent())
                .viewModule(getViewModule())
                .build();
    }

    protected ViewModule getViewModule() {
        return new ViewModule(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.unSubscribe();
        mUnBinder.unbind();
    }

    /**
     * 控制图层显示隐藏
     *
     * @param isShow
     */
    public void isShowLayer(View v, boolean isShow) {
        int visiable = v.getVisibility();
        if (isShow) {
            if (visiable == View.GONE || visiable == View.INVISIBLE) {
                v.setVisibility(View.VISIBLE);
            }
        } else {
            if (visiable == View.VISIBLE) {
                v.setVisibility(View.GONE);
            }
        }
    }

    protected abstract void initInject();

    protected abstract int getLayout();

    protected abstract void initEventAndData();
}
