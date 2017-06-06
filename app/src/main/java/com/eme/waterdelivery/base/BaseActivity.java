package com.eme.waterdelivery.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.eme.waterdelivery.App;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.injector.component.DaggerViewComponent;
import com.eme.waterdelivery.injector.component.ViewComponent;
import com.eme.waterdelivery.injector.module.ViewModule;
import com.eme.waterdelivery.tools.KeyboardUtils;
import com.eme.waterdelivery.tools.ToastUtil;

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
        initEventAndData(savedInstanceState);
        if (mPresenter != null)
            mPresenter.subscribe();
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
        return DaggerViewComponent.builder().appComponent(App.getAppInstance().getAppComponent()).viewModule(getViewModule()).build();
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

    /**
     * 网络不可用提示异常
     */
    public void showNetError(){
        ToastUtil.shortToast(this,getText(R.string.net_error).toString());
    }

    protected abstract void initInject();

    protected abstract int getLayout();

    protected abstract void initEventAndData(Bundle savedInstanceState);

    //region软键盘的处理

    /**
     * 清除editText的焦点
     *
     * @param v   焦点所在View
     * @param ids 输入框
     */
    public void clearViewFocus(View v, int... ids) {
        if (null != v && null != ids && ids.length > 0) {
            for (int id : ids) {
                if (v.getId() == id) {
                    v.clearFocus();
                    break;
                }
            }
        }


    }

    /**
     * 隐藏键盘
     *
     * @param v   焦点所在View
     * @param ids 输入框
     * @return true代表焦点在edit上
     */
    public boolean isFocusEditText(View v, int... ids) {
        if (v instanceof EditText) {
            EditText tmp_et = (EditText) v;
            for (int id : ids) {
                if (tmp_et.getId() == id) {
                    return true;
                }
            }
        }
        return false;
    }

    //是否触摸在指定view上面,对某个控件过滤
    public boolean isTouchView(View[] views, MotionEvent ev) {
        if (views == null || views.length == 0) return false;
        int[] location = new int[2];
        for (View view : views) {
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if (ev.getX() > x && ev.getX() < (x + view.getWidth())
                    && ev.getY() > y && ev.getY() < (y + view.getHeight())) {
                return true;
            }
        }
        return false;
    }
    //endregion

    //region 右滑返回上级


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (isTouchView(filterViewByIds(), ev)) return super.dispatchTouchEvent(ev);
            if (hideSoftByEditViewIds() == null || hideSoftByEditViewIds().length == 0)
                return super.dispatchTouchEvent(ev);
            View v = getCurrentFocus();
            if (isFocusEditText(v, hideSoftByEditViewIds())) {
                //隐藏键盘
                KeyboardUtils.hideInputForce(this);
                clearViewFocus(v, hideSoftByEditViewIds());
            }
        }
        return super.dispatchTouchEvent(ev);

    }

    /**
     * 传入EditText的Id
     * 没有传入的EditText不做处理
     *
     * @return id 数组
     */
    public int[] hideSoftByEditViewIds() {
        return null;
    }

    /**
     * 传入要过滤的View
     * 过滤之后点击将不会有隐藏软键盘的操作
     *
     * @return id 数组
     */
    public View[] filterViewByIds() {
        return null;
    }
}
