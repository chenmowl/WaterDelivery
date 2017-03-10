package com.eme.waterdelivery.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.BaseActivity;
import com.eme.waterdelivery.contract.HomeContract;
import com.eme.waterdelivery.presenter.HomePresenter;
import com.eme.waterdelivery.tools.ToastUtil;
import com.eme.waterdelivery.tools.Util;
import com.eme.waterdelivery.ui.adapter.HomeFragmentAdapter;
import com.eme.waterdelivery.ui.fragment.DelayFragment;
import com.eme.waterdelivery.ui.fragment.SendingFragment;
import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by dijiaoliang on 17/3/7.
 */

public class HomeActivity extends BaseActivity<HomePresenter> implements HomeContract.View {


    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_main)
    TabLayout tabMain;
    @BindView(R.id.vp_main)
    ViewPager vpMain;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    public static String[] tabTitle = new String[]{"待接单 (12)", "配送中 (34)"};
    List<Fragment> fragments = new ArrayList<>();
    private HomeFragmentAdapter homeFragmentAdapter;

    @Override
    protected void initInject() {
        getViewComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolBar(mToolbar, "");

        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0);
        /**
         *  1、这里代码里设置了NavigationView的背景，这里背景只能在代码里设置
         *  2、设置了距离屏幕顶部的padding，防止内容被掩盖
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            navigationView.setBackgroundColor(Color.WHITE);
            headerLayout.setPadding(0, Util.getStatusBarHeight(), 0, 0);
        }
        /**
         * 去除滚动条
         */
        if (navigationView != null) {
            NavigationMenuView contentView = (NavigationMenuView) navigationView.getChildAt(0);
            if (contentView != null) {
                contentView.setVerticalScrollBarEnabled(false);
            }
        }
        //设置NavigationView子控件的点击事件
        initNavClickListener(headerLayout);

        fragments.add(new DelayFragment());
        fragments.add(new SendingFragment());
        homeFragmentAdapter = new HomeFragmentAdapter(getSupportFragmentManager(), fragments);
        vpMain.setAdapter(homeFragmentAdapter);
        //todo TabLayout配合ViewPager有时会出现不显示Tab文字的Bug,需要按如下顺序
        tabMain.setupWithViewPager(vpMain, true);
        tabMain.getTabAt(0).setText(tabTitle[0]);
        tabMain.getTabAt(1).setText(tabTitle[1]);
    }

    /**
     * 侧边栏监听事件
     *
     * @param headerLayout
     */
    private void initNavClickListener(View headerLayout) {
        //这里设置点击事件
//        headerLayout.findViewById(R.id.ll_quit).setOnClickListener(this);
        RxView.clicks(headerLayout.findViewById(R.id.ll_quit))
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        alertQuitDialog();
                    }
                });
        RxView.clicks(headerLayout.findViewById(R.id.rl_day_order))
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Intent intent = new Intent(HomeActivity.this, CompleteActivity.class);
                        intent.putExtra(CompleteActivity.TAB, CompleteActivity.TAB_0);
                        startActivity(intent);
                    }
                });
        RxView.clicks(headerLayout.findViewById(R.id.rl_apply))
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Intent intent = new Intent(HomeActivity.this, MyApplyActivity.class);
                        intent.putExtra(CompleteActivity.TAB, CompleteActivity.TAB_0);
                        startActivity(intent);
                    }
                });
    }

    /**
     * 退出框
     */
    private void alertQuitDialog() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("退出当前应用");
        builder.setMessage("确定退出GeekNews吗");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * SupportActivity 提供的方法支持 onBackPressed()
     */
    @Override
    public void onBackPressedSupport() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private long mExitTime = 0;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                if (System.currentTimeMillis() - mExitTime > 3000) {
                    ToastUtil.shortToast(this, "再按一次退出程序");
                    mExitTime = System.currentTimeMillis();
                    return true;
                }
            }
        }
        return super.dispatchKeyEvent(event);
    }

}
