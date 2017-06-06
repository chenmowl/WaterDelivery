package com.eme.waterdelivery.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.BaseActivity;
import com.eme.waterdelivery.contract.CompleteContract;
import com.eme.waterdelivery.presenter.CompletePresenter;
import com.eme.waterdelivery.ui.adapter.HomeFragmentAdapter;
import com.eme.waterdelivery.ui.fragment.AllOrderFragment;
import com.eme.waterdelivery.ui.fragment.CurrentDayFragment;
import com.eme.waterdelivery.ui.fragment.MonthOrderFragment;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * 已完成订单页面
 * <p>
 * Created by dijiaoliang on 17/3/9.
 */
public class CompleteActivity extends BaseActivity<CompletePresenter> implements CompleteContract.View, ViewPager.OnPageChangeListener {


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
    @BindView(R.id.btn_right)
    TextView btnRight;

    private CurrentDayFragment currentDayFragment;
    private MonthOrderFragment monthOrderFragment;
    private AllOrderFragment allOrderFragment;

    List<Fragment> fragments = new ArrayList<>();

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
        btnRight.setVisibility(View.GONE);
        currentDayFragment = new CurrentDayFragment();
        monthOrderFragment = new MonthOrderFragment();
        allOrderFragment = new AllOrderFragment();
        fragments.add(currentDayFragment);
        fragments.add(monthOrderFragment);
        fragments.add(allOrderFragment);
        HomeFragmentAdapter homeFragmentAdapter = new HomeFragmentAdapter(getSupportFragmentManager(), fragments);
        vpMain.setOffscreenPageLimit(2);
        vpMain.setAdapter(homeFragmentAdapter);
        //todo TabLayout配合ViewPager有时会出现不显示Tab文字的Bug,需要按如下顺序
        tabMain.setupWithViewPager(vpMain, true);
        tabMain.getTabAt(0).setText(getText(R.string.today_receives).toString());
        tabMain.getTabAt(1).setText(getText(R.string.month_receives).toString());
        tabMain.getTabAt(2).setText(getText(R.string.all_receives).toString());

        tvTitle.setText(getText(R.string.complete));
        initListener();

        vpMain.setOnPageChangeListener(this);

        // TODO: 17/3/29 延迟加载，因为fragment初始化需要时间
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
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
                    default:
                        break;
                }
            }
        }, 200);
    }

    private void initListener() {
        RxView.clicks(back)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
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

    public void updateOrderSum(String flag, int sum) {
        switch (flag) {
            case Constant.ORDER_TODAY:
                tabMain.getTabAt(0).setText(TextUtils.concat(getText(R.string.today_receive).toString(), String.valueOf(sum), getText(R.string.sign)));
                break;
            case Constant.ORDER_MONTH:
                tabMain.getTabAt(1).setText(TextUtils.concat(getText(R.string.month_receive).toString(), String.valueOf(sum), getText(R.string.sign)));
                break;
            case Constant.ORDER_ALL:
                tabMain.getTabAt(2).setText(TextUtils.concat(getText(R.string.all_receive).toString(), String.valueOf(sum), getText(R.string.sign)));
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case Constant.ZERO:
                currentDayFragment.refreshPage();
                break;
            case Constant.ONE:
                monthOrderFragment.refreshPage();
                break;
            case Constant.TWO:
                allOrderFragment.refreshPage();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
