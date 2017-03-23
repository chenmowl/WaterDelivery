package com.eme.waterdelivery.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eme.waterdelivery.App;
import com.eme.waterdelivery.injector.component.DaggerViewComponent;
import com.eme.waterdelivery.injector.component.ViewComponent;
import com.eme.waterdelivery.injector.module.ViewModule;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 */
public abstract class BaseNormalFragment<T extends BasePresenter> extends Fragment implements BaseView {

    @Inject
    protected T mPresenter;
    protected View mView;
    private Unbinder mUnBinder;


    protected ViewComponent getViewComponent() {
        return DaggerViewComponent.builder()
                .appComponent(App.getAppInstance().getAppComponent())
                .viewModule(getViewModule())
                .build();
    }

    protected ViewModule getViewModule() {
        return new ViewModule(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), null);
        initInject();
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
        initEventAndData();
        mPresenter.subscribe();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.unSubscribe();
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

    protected abstract int getLayoutId();

    protected abstract void initEventAndData();

}
