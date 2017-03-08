package com.eme.waterdelivery.ui;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.BaseActivity;
import com.eme.waterdelivery.contract.SendingDetailContract;
import com.eme.waterdelivery.presenter.SendingDetailPresenter;
import com.eme.waterdelivery.ui.adapter.SendingDetailGoodAdapter;
import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * 配送中订单详情
 * <p>
 * Created by dijiaoliang on 17/3/8.
 */
public class SendingDetailActivity extends BaseActivity<SendingDetailPresenter> implements SendingDetailContract.View {

    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_order_detail_place_time)
    TextView tvOrderDetailPlaceTime;
    @BindView(R.id.tv_order_detail_pay_mode)
    TextView tvOrderDetailPayMode;
    @BindView(R.id.tv_order_status)
    TextView tvOrderStatus;
    @BindView(R.id.tv_order_detail_used_time)
    TextView tvOrderDetailUsedTime;
    @BindView(R.id.tv_receiver)
    TextView tvReceiver;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.tv_order_detail_client_phone)
    TextView tvOrderDetailClientPhone;
    @BindView(R.id.tv_order_detail_customer_phone)
    TextView tvOrderDetailCustomerPhone;
    @BindView(R.id.rv_order_goods)
    RecyclerView rvOrderGoods;
    @BindView(R.id.tv_order_detail_open_surplus)
    TextView tvOrderDetailOpenSurplus;
    @BindView(R.id.iv_order_detail_open_surplus)
    ImageView ivOrderDetailOpenSurplus;
    @BindView(R.id.ll_order_open_surplus)
    LinearLayout llOrderOpenSurplus;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.btn_sign)
    AppCompatButton btnSign;
    @BindView(R.id.ll_av_loading_transparent_44)
    LinearLayout llAvLoadingTransparent44;
    @BindView(R.id.map)
    MapView map;

    private AMap aMap;

    private List<String> mData;
    private SendingDetailGoodAdapter goodAdapter;
    private Boolean isShowAll;

    @Override
    protected void initInject() {
        getViewComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_sending_detail;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        map.onCreate(savedInstanceState);
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = map.getMap();
        }

        // TODO: 17/3/8  改变可视区域,添加坐标点 
        changeCamera(
                CameraUpdateFactory.newCameraPosition(new CameraPosition(
                        new LatLng(39.983456, 116.3154950), 18, 30, 30)));
        aMap.clear();
        aMap.addMarker(new MarkerOptions().position(new LatLng(39.983456, 116.3154950))
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        mData = new ArrayList<>();
        mData.add("1");
        mData.add("1");
        mData.add("1");
        mData.add("1");
        isShowAll=false;//商品是否展开
        rvOrderGoods.setLayoutManager(new LinearLayoutManager(this));
        rvOrderGoods.setNestedScrollingEnabled(false);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvOrderGoods.setHasFixedSize(true);
        goodAdapter = new SendingDetailGoodAdapter(mData);
        goodAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        rvOrderGoods.setAdapter(goodAdapter);

        if (mData.size() <= 2) {
            llOrderOpenSurplus.setVisibility(View.GONE);
        }else{
            if (isShowAll) {
                tvOrderDetailOpenSurplus.setText(R.string.order_detail_title_close_surplus);
                ivOrderDetailOpenSurplus.setImageResource(R.mipmap.shangla);
            } else {
                tvOrderDetailOpenSurplus.setText(R.string.order_detail_title_open_surplus);
                ivOrderDetailOpenSurplus.setImageResource(R.mipmap.xiala);
            }
        }

        //设置btn监听,防止连续的二次点击
        RxView.clicks(llOrderOpenSurplus)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        checkShowAll();
                    }
                });

    }

    /**
     * 检查商品列表是否展开
     */
    private void checkShowAll(){
        isShowAll=!isShowAll;
        goodAdapter.openHideList(isShowAll);
        if (isShowAll) {
            tvOrderDetailOpenSurplus.setText(R.string.order_detail_title_close_surplus);
            ivOrderDetailOpenSurplus.setImageResource(R.mipmap.shangla);
        } else {
            tvOrderDetailOpenSurplus.setText(R.string.order_detail_title_open_surplus);
            ivOrderDetailOpenSurplus.setImageResource(R.mipmap.xiala);
        }
    }

    /**
     * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
     */
    private void changeCamera(CameraUpdate update) {
        aMap.moveCamera(update);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        map.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        map.onDestroy();
        super.onDestroy();
    }
}
