package com.eme.waterdelivery.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.BaseActivity;
import com.eme.waterdelivery.contract.CompleteContract;
import com.eme.waterdelivery.presenter.CompletePresenter;
import com.eme.waterdelivery.ui.adapter.HomeFragmentAdapter;
import com.eme.waterdelivery.ui.fragment.CurrentDayFragment;
import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * 已完成订单页面
 * <p>
 * Created by dijiaoliang on 17/3/9.
 */
public class CompleteActivity extends BaseActivity<CompletePresenter> implements CompleteContract.View {


    public static final String TAB = "TAB";
    public static final String TAB_0 = "0";
    public static final String TAB_1 = "1";
    public static final String TAB_2 = "2";

    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tab_main)
    TabLayout tabMain;
    @BindView(R.id.vp_main)
    ViewPager vpMain;

    public static String[] tabTitle = new String[]{"今日接单 (12)", "当月接单 (34)", "历史接单 (34)"};
    List<Fragment> fragments = new ArrayList<>();
    private HomeFragmentAdapter homeFragmentAdapter;

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
        fragments.add(new CurrentDayFragment());
        fragments.add(new CurrentDayFragment());
        fragments.add(new CurrentDayFragment());
        homeFragmentAdapter = new HomeFragmentAdapter(getSupportFragmentManager(), fragments);
        vpMain.setAdapter(homeFragmentAdapter);
        //todo TabLayout配合ViewPager有时会出现不显示Tab文字的Bug,需要按如下顺序
        tabMain.setupWithViewPager(vpMain, true);
        tabMain.getTabAt(0).setText(tabTitle[0]);
        tabMain.getTabAt(1).setText(tabTitle[1]);
        tabMain.getTabAt(2).setText(tabTitle[2]);

        tvTitle.setText(getText(R.string.complete));
        initListener();

        switch (getIntent().getStringExtra(TAB)) {
            case TAB_0:
                vpMain.setCurrentItem(0);
                break;
            case TAB_1:
                vpMain.setCurrentItem(1);
                break;
            case TAB_2:
                vpMain.setCurrentItem(2);
                break;
        }
    }

    private void initListener() {
        RxView.clicks(back)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        CompleteActivity.this.finish();
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
