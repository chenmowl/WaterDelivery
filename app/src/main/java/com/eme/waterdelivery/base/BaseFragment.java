package com.eme.waterdelivery.base;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eme.waterdelivery.App;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.injector.component.DaggerViewComponent;
import com.eme.waterdelivery.injector.component.ViewComponent;
import com.eme.waterdelivery.injector.module.ViewModule;
import com.eme.waterdelivery.tools.ToastUtil;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 *
 */
public abstract class BaseFragment<T extends BasePresenter> extends SupportFragment implements BaseView {

    @Inject
    protected T mPresenter;
    protected View mView;
    private Unbinder mUnBinder;
    protected boolean isInited = false;


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
        mPresenter.subscribe();
        mUnBinder = ButterKnife.bind(this, view);
        isInited = true;
        initEventAndData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        if (!isInited && !hidden) {
//            isInited = true;
//            initEventAndData();
//        }
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

    protected Activity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity=activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity=null;
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

    /**
     * 网络不可用提示异常
     */
    public void showNetError(){
        ToastUtil.shortToast(getActivity(),getText(R.string.net_error).toString());
    }

    protected abstract void initInject();

    protected abstract int getLayoutId();

    protected abstract void initEventAndData();

}
