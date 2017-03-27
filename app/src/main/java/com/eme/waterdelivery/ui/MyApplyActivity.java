package com.eme.waterdelivery.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.BaseActivity;
import com.eme.waterdelivery.contract.MyApplyContract;
import com.eme.waterdelivery.presenter.MyApplyPresenter;
import com.eme.waterdelivery.ui.adapter.HomeFragmentAdapter;
import com.eme.waterdelivery.ui.fragment.ApplyFragment;
import com.eme.waterdelivery.ui.fragment.ApplyRecordFragment;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * 我的申请页面
 * <p>
 * Created by dijiaoliang on 17/3/9.
 */
public class MyApplyActivity extends BaseActivity<MyApplyPresenter> implements MyApplyContract.View, ViewPager.OnPageChangeListener {

    public static final String TAG=MyApplyActivity.class.getSimpleName();

    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tab_main)
    TabLayout tabMain;
    @BindView(R.id.vp_main)
    ViewPager vpMain;

    public static String[] tabTitle = new String[]{"申请采购", "申请记录"};
    List<Fragment> fragments = new ArrayList<>();
    private HomeFragmentAdapter homeFragmentAdapter;
    ApplyRecordFragment applyRecordFragment;

    @Override
    protected void initInject() {
        getViewComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_complete;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        applyRecordFragment=new ApplyRecordFragment();
        fragments.add(new ApplyFragment());
        fragments.add(applyRecordFragment);
        homeFragmentAdapter = new HomeFragmentAdapter(getSupportFragmentManager(), fragments);
        vpMain.setAdapter(homeFragmentAdapter);
        //todo TabLayout配合ViewPager有时会出现不显示Tab文字的Bug,需要按如下顺序
        tabMain.setupWithViewPager(vpMain, true);
        tabMain.getTabAt(0).setText(tabTitle[0]);
        tabMain.getTabAt(1).setText(tabTitle[1]);

        tvTitle.setText(getText(R.string.my_apply));
        initListener();
    }

    private void initListener() {
        RxView.clicks(back)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mPresenter.unSubscribe();
                        MyApplyActivity.this.finish();
                    }
                });
        vpMain.setOnPageChangeListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case Constant.ONE:
                applyRecordFragment.refreshPage();
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
