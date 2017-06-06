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
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.BaseActivity;
import com.eme.waterdelivery.contract.HomeContract;
import com.eme.waterdelivery.model.bean.VersionResult;
import com.eme.waterdelivery.model.bean.entity.LoginBo;
import com.eme.waterdelivery.model.bean.entity.VersionBo;
import com.eme.waterdelivery.model.net.ApiConfig;
import com.eme.waterdelivery.model.sp.SPBase;
import com.eme.waterdelivery.model.sp.SpConstant;
import com.eme.waterdelivery.presenter.HomePresenter;
import com.eme.waterdelivery.tools.ImageLoader;
import com.eme.waterdelivery.tools.ToastUtil;
import com.eme.waterdelivery.tools.Util;
import com.eme.waterdelivery.tools.XmlUtil;
import com.eme.waterdelivery.ui.adapter.HomeFragmentAdapter;
import com.eme.waterdelivery.ui.fragment.DelayFragment;
import com.eme.waterdelivery.ui.fragment.FixedFragment;
import com.eme.waterdelivery.ui.fragment.SendingFragment;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import ezy.boost.update.IUpdateParser;
import ezy.boost.update.UpdateInfo;
import ezy.boost.update.UpdateManager;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * 主页面
 *
 * Created by dijiaoliang on 17/3/7.
 */
public class HomeActivity extends BaseActivity<HomePresenter> implements HomeContract.View, ViewPager.OnPageChangeListener {


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

        /**处理侧边栏数据**/
        if(getIntent().getExtras()!=null){
            LoginBo loginBo=getIntent().getExtras().getParcelable(Constant.LOGIN_INFO);
            if(loginBo!=null){
                //更新侧边栏信息
                CircleImageView ivHeader = (CircleImageView) headerLayout.findViewById(R.id.profile_image);
                TextView tvName = (TextView) headerLayout.findViewById(R.id.tv_name);
                TextView tvJobNumber = (TextView) headerLayout.findViewById(R.id.tv_job_number);
                TextView tvCount = (TextView) headerLayout.findViewById(R.id.tv_count);
                TextView tvStationPhone = (TextView) headerLayout.findViewById(R.id.tv_station_phone);
                tvName.setText(loginBo.getCname());
                tvJobNumber.setText(TextUtils.concat(getText(R.string.number_job),loginBo.getUserId()));
                tvStationPhone.setText(loginBo.getStorePhone());
                if(!TextUtils.isEmpty(loginBo.getImg())){
                    ImageLoader.load(this,loginBo.getImg(),ivHeader);
                }
            }
        }
        DelayFragment delayFragment=new DelayFragment();
        FixedFragment fixedFragment=new FixedFragment();
        SendingFragment sendingFragment=new SendingFragment();
        fragments.add(delayFragment);
        fragments.add(fixedFragment);
        fragments.add(sendingFragment);
        homeFragmentAdapter = new HomeFragmentAdapter(getSupportFragmentManager(), fragments);
        vpMain.setAdapter(homeFragmentAdapter);
        //todo TabLayout配合ViewPager有时会出现不显示Tab文字的Bug,需要按如下顺序
        tabMain.setupWithViewPager(vpMain, true);
        tabMain.getTabAt(0).setText(getText(R.string.waiting_home).toString());
        tabMain.getTabAt(1).setText(getText(R.string.fixed_order).toString());
        tabMain.getTabAt(2).setText(getText(R.string.sending_home).toString());

        vpMain.setOnPageChangeListener(this);

        //校验应用版本
//        checkAppVersion();
    }

    /**
     * 更新待接单和配送中数量
     * @param flag
     * @param distributingOrderSum
     */
    public void updateOrderSum(int flag,int distributingOrderSum){
        switch (flag){
            case Constant.ORDER_DELAY:
                tabMain.getTabAt(0).setText(TextUtils.concat(getText(R.string.waiting_home_01).toString(),String.valueOf(distributingOrderSum),getText(R.string.sign)));
                break;
            case Constant.ORDER_FIXED:
                tabMain.getTabAt(1).setText(TextUtils.concat(getText(R.string.fixed_home).toString(),String.valueOf(distributingOrderSum),getText(R.string.sign)));
                break;
            case Constant.ORDER_SEND:
                tabMain.getTabAt(2).setText(TextUtils.concat(getText(R.string.sending).toString(),String.valueOf(distributingOrderSum),getText(R.string.sign)));
                break;
            default:
                break;
        }
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
                .subscribe(new Consumer<Object>() {

                    @Override
                    public void accept(Object o) throws Exception {
                        alertQuitDialog();
                    }
                });
        RxView.clicks(headerLayout.findViewById(R.id.rl_history_order))
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {

                    @Override
                    public void accept(Object o) throws Exception {
                        Intent intent = new Intent(HomeActivity.this, CompleteActivity.class);
                        intent.putExtra(CompleteActivity.TAB, CompleteActivity.TAB_0);
                        startActivity(intent);
                    }
                });
        RxView.clicks(headerLayout.findViewById(R.id.btn_apply_vacation))
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {

                    @Override
                    public void accept(Object o) throws Exception {
                        startActivity(new Intent(HomeActivity.this, ApplyVacationActivity.class));
                    }
                });
        RxView.clicks(headerLayout.findViewById(R.id.rl_collect_water))
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {

                    @Override
                    public void accept(Object o) throws Exception {
                        startActivity(new Intent(HomeActivity.this, HomeCollectWaterActivity.class));
                    }
                });
        RxView.clicks(headerLayout.findViewById(R.id.rl_collect_bucket))
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {

                    @Override
                    public void accept(Object o) throws Exception {
                        startActivity(new Intent(HomeActivity.this, CollectWaterActivity.class));
                    }
                });
        RxView.clicks(headerLayout.findViewById(R.id.rl_assessment))
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {

                    @Override
                    public void accept(Object o) throws Exception {
                        startActivity(new Intent(HomeActivity.this, AssessmentActivity.class));
                    }
                });
        RxView.clicks(headerLayout.findViewById(R.id.rl_sale_ticket))
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {

                    @Override
                    public void accept(Object o) throws Exception {
                        startActivity(new Intent(HomeActivity.this, SaleTicketActivity.class));
                    }
                });
        RxView.clicks(headerLayout.findViewById(R.id.rl_apply_ticket))
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {

                    @Override
                    public void accept(Object o) throws Exception {
                        startActivity(new Intent(HomeActivity.this, ApplyTicketActivity.class));
                    }
                });
        RxView.clicks(headerLayout.findViewById(R.id.rl_chase_order))
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {

                    @Override
                    public void accept(Object o) throws Exception {
                        startActivity(new Intent(HomeActivity.this, ChaseOrderActivity.class));
                    }
                });
    }

    /**
     * 退出框
     */
    private void alertQuitDialog() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle(getText(R.string.quit_app));
        builder.setMessage(getText(R.string.quit_app_message));
        builder.setNegativeButton(getText(R.string.cancel), null);
        builder.setPositiveButton(getText(R.string.quit), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SPBase.setContent(HomeActivity.this, SpConstant.HEAD_FILE_NAME,SpConstant.HEAD_COOKIE_UID, Constant.STR_EMPTY);
                SPBase.setContent(HomeActivity.this, SpConstant.HEAD_FILE_NAME,SpConstant.HEAD_COOKIE_SIG, Constant.STR_EMPTY);
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                finish();
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
//    @Override
//    public void onBackPressedSupport() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

    private long mExitTime = 0;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                if (System.currentTimeMillis() - mExitTime > 3000) {
                    ToastUtil.shortToast(this, getText(R.string.quit_app_again).toString());
                    mExitTime = System.currentTimeMillis();
                    return true;
                }
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case Constant.ZERO:
                if(fragments.get(0)!=null){
                    ((DelayFragment)fragments.get(0)).refreshPage();
                }
                break;
            case Constant.ONE:
                if(fragments.get(1)!=null){
                    ((FixedFragment)fragments.get(1)).refreshPage();
                }
                break;
            case Constant.TWO:
                if(fragments.get(2)!=null){
                    ((SendingFragment)fragments.get(2)).refreshPage();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 版本更新
     */
    private void checkAppVersion() {
        UpdateManager.create(this).setUrl(ApiConfig.VERSION_CHECK_URL).setManual(true).setNotifyId(998).setParser(new IUpdateParser() {
            @Override
            public UpdateInfo parse(String source) throws Exception {
                UpdateInfo info = new UpdateInfo();
                info.isIgnorable = false;
                boolean isForce=false;
                if(!TextUtils.isEmpty(source)){
                    VersionResult versionResult = JSON.parseObject(source, VersionResult.class);
                    if(versionResult!=null && versionResult.isSuccess()){
                        String xml=versionResult.getData();
                        Map<String,Set<String>> map= XmlUtil.handleXml(xml);
                        VersionBo bo = new VersionBo();
                        bo.setRefreshVersionList(new ArrayList<String>());
                        Set<String> idSet=map.get("id");
                        for(String id:idSet){
                            bo.setId(id);
                        }
                        Set<String> idTitle=map.get("title");
                        for(String title:idTitle){
                            bo.setTitle(title);
                        }
                        Set<String> idVersion=map.get("version");
                        for(String version:idVersion){
                            bo.setVersion(version);
                        }
                        Set<String> idDescription=map.get("description");
                        for(String description:idDescription){
                            bo.setDescription(description);
                        }
                        Set<String> idUrl=map.get("url");
                        for(String url:idUrl){
                            bo.setUrl(url);
                        }
                        Set<String> idMd5=map.get("md5");
                        for(String md5:idMd5){
                            bo.setMd5(md5);
                        }
                        Set<String> idRefreshVersion=map.get("refreshVersion");
                        List<String> list= bo.getRefreshVersionList();
                        for(String refreshVersion:idRefreshVersion){
                            list.add(refreshVersion);
                        }
                        String versionName=Util.getVersionName(HomeActivity.this);
                        if(!versionName.equals(bo.getVersion())){
                            info.hasUpdate=true;
                            info.updateContent = bo.getDescription().replace("|","\n");
                            info.versionName = bo.getVersion();
                            info.url = bo.getUrl();
                            info.md5 = bo.getMd5();
                            info.size = 10149314;
                            List<String> versions=bo.getRefreshVersionList();
                            for(String version: versions){
                                if(versionName.equals(version)){
                                    isForce=true;
                                }
                            }
                            info.isForce = isForce;
                        }else{
                            info.hasUpdate=false;
                        }
                    }
                }
                return info;
            }
        }).check();
    }

    @Override
    protected void onDestroy() {
        fragments.get(0).onDestroy();
        fragments.get(1).onDestroy();
        homeFragmentAdapter=null;
        fragments.clear();
        super.onDestroy();
    }
}
