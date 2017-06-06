package com.eme.waterdelivery.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.BaseActivity;
import com.eme.waterdelivery.contract.AssessmentContract;
import com.eme.waterdelivery.presenter.AssessmentPresenter;
import com.eme.waterdelivery.ui.adapter.HomeFragmentAdapter;
import com.eme.waterdelivery.ui.fragment.AssessMoneyFragment;
import com.eme.waterdelivery.ui.fragment.AssessTicketFragment;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * 应缴金额
 * <p>
 * Created by dijiaoliang on 17/4/25.
 */
public class AssessmentActivity extends BaseActivity<AssessmentPresenter> implements AssessmentContract.View, ViewPager.OnPageChangeListener {


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

    private AssessMoneyFragment assessMoneyFragment;
    private AssessTicketFragment assessTicketFragment;
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
        tvTitle.setText(getText(R.string.assessment));
        btnRight.setText(getText(R.string.assessment_history));
        btnRight.setTextColor(getResources().getColor(R.color.main_color_red));

        assessMoneyFragment = new AssessMoneyFragment();
        assessTicketFragment = new AssessTicketFragment();
        fragments.add(assessMoneyFragment);
        fragments.add(assessTicketFragment);
        HomeFragmentAdapter homeFragmentAdapter = new HomeFragmentAdapter(getSupportFragmentManager(), fragments);
        vpMain.setOffscreenPageLimit(2);
        vpMain.setAdapter(homeFragmentAdapter);
        //todo TabLayout配合ViewPager有时会出现不显示Tab文字的Bug,需要按如下顺序
        vpMain.setOnPageChangeListener(this);
        tabMain.setupWithViewPager(vpMain, true);
        tabMain.getTabAt(0).setText(getText(R.string.assessment_amount).toString());
        tabMain.getTabAt(1).setText(getText(R.string.assessment_ticket).toString());

        initListener();

//        // TODO: 17/3/29 延迟加载，因为fragment初始化需要时间
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                vpMain.setCurrentItem(0);
            }
        }, 200);

    }

    private void initListener() {
        RxView.clicks(btnRight)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {

                    @Override
                    public void accept(Object o) throws Exception {
                        startActivity(new Intent(AssessmentActivity.this, AssessmentHistoryActivity.class));
                    }
                });
        RxView.clicks(back)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        finish();
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case Constant.ZERO:
                assessMoneyFragment.refreshPage();
                break;
            case Constant.ONE:
                assessTicketFragment.refreshPage();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
