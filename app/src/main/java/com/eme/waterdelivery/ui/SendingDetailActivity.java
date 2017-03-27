package com.eme.waterdelivery.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.BaseActivity;
import com.eme.waterdelivery.contract.SendingDetailContract;
import com.eme.waterdelivery.model.bean.entity.OrderDetailBo;
import com.eme.waterdelivery.presenter.SendingDetailPresenter;
import com.eme.waterdelivery.tools.ConstUtils;
import com.eme.waterdelivery.tools.TimeUtils;
import com.eme.waterdelivery.tools.ToastUtil;
import com.eme.waterdelivery.ui.adapter.SendingDetailGoodAdapter;
import com.eme.waterdelivery.widget.FullyLinearLayoutManager;
import com.eme.waterdelivery.widget.MapContainer;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

import static com.eme.waterdelivery.R.id.tv_order_detail_pay_mode;
import static com.eme.waterdelivery.R.id.tv_order_detail_used_time;


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
    @BindView(tv_order_detail_pay_mode)
    TextView tvOrderDetailPayMode;
    @BindView(R.id.tv_order_status)
    TextView tvOrderStatus;
    @BindView(tv_order_detail_used_time)
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
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.rl_footer)
    RelativeLayout rlFooter;
    @BindView(R.id.sv)
    ScrollView sv;
    @BindView(R.id.map_container)
    MapContainer mapContainer;

    private AMap aMap;

    private List<OrderDetailBo.GoodsBean> mData;
    private SendingDetailGoodAdapter goodAdapter;
    private boolean isShowAll;

    private String orderId;

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
        changeCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                        new LatLng(39.983456, 116.3154950), 18, 30, 30)));
        aMap.clear();
        aMap.addMarker(new MarkerOptions().position(new LatLng(39.983456, 116.3154950))
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        mapContainer.setScrollView(sv);//MapContainer关联ScrollView 解决地图和ScrollView的事件冲突

//        如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvContent.setHasFixedSize(true);
        rvContent.setNestedScrollingEnabled(false);
        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(this);
        manager.setAutoMeasureEnabled(true);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvContent.setLayoutManager(manager);
        mData = new ArrayList<>();
        isShowAll = false;//商品是否展开
        goodAdapter = new SendingDetailGoodAdapter(this, mData);
        rvContent.setAdapter(goodAdapter);

        if (mData.size() <= 2) {
            llOrderOpenSurplus.setVisibility(View.GONE);
        } else {
            if (isShowAll) {
                tvOrderDetailOpenSurplus.setText(R.string.order_detail_title_close_surplus);
                ivOrderDetailOpenSurplus.setImageResource(R.mipmap.shangla);
            } else {
                tvOrderDetailOpenSurplus.setText(R.string.order_detail_title_open_surplus);
                ivOrderDetailOpenSurplus.setImageResource(R.mipmap.xiala);
            }
        }
        initListener();
        orderId=getIntent().getStringExtra(Constant.ORDER_ID);
        tvTitle.setText(orderId);
        mPresenter.requestData(orderId);//请求订单详情数据
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        //        设置btn监听,防止连续的二次点击
        RxView.clicks(llOrderOpenSurplus)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        checkShowAll();
                    }
                });
        RxView.clicks(back)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mPresenter.unSubscribe();
                        finish();
                    }
                });
    }

    /**
     * 检查商品列表是否展开
     */
    private void checkShowAll() {
        isShowAll = !isShowAll;
        goodAdapter.setIsOpenPlus(isShowAll);
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

    @Override
    public void showProgress(boolean b) {
        isShowLayer(llAvLoadingTransparent44,b);
    }

    @Override
    public void showRequestMsg(String msg) {
        if(TextUtils.isEmpty(msg)){
            ToastUtil.shortToast(this,getText(R.string.request_error).toString());
        }else{
            ToastUtil.shortToast(this,msg);
        }
    }

    @Override
    public void updateUi(OrderDetailBo orderDetailBo) {
        tvOrderDetailPlaceTime.setText(orderDetailBo.getCreateTime());
        switch (orderDetailBo.getPayType()){
            case Constant.PAY_TYPE_MONEY:
                tvOrderDetailPayMode.setText(getText(R.string.order_pay_mode_money));
                break;
            case Constant.PAY_TYPE_WEIXIN:
                tvOrderDetailPayMode.setText(getText(R.string.order_pay_mode_weixin));
                break;
            default:
                tvOrderDetailPayMode.setText(Constant.STR_EMPTY);
                break;
        }
        if(!TextUtils.isEmpty(orderDetailBo.getShippingTime())){
            tvOrderDetailUsedTime.setText(TimeUtils.getIntervalTime(TimeUtils.getCurTimeString(), orderDetailBo.getShippingTime(), ConstUtils.TimeUnit.MIN) + "分钟");
        }
        tvReceiver.setText(getText(R.string.order_receiver_title) + orderDetailBo.getMemberName());
        tvAddress.setText(getText(R.string.order_address_title) + orderDetailBo.getMemberAddress());
        tvRemark.setText(orderDetailBo.getOrderMessage());
        tvOrderDetailClientPhone.setText(orderDetailBo.getServicePhone());
        tvOrderDetailCustomerPhone.setText(orderDetailBo.getMemberPhone());
        List<OrderDetailBo.GoodsBean> data=orderDetailBo.getGoods();
        mData.clear();
        mData.addAll(data);
        goodAdapter.notifyDataSetChanged();
        if (mData.size() <= 2) {
            llOrderOpenSurplus.setVisibility(View.GONE);
        } else {
            if (isShowAll) {
                tvOrderDetailOpenSurplus.setText(R.string.order_detail_title_close_surplus);
                ivOrderDetailOpenSurplus.setImageResource(R.mipmap.shangla);
            } else {
                tvOrderDetailOpenSurplus.setText(R.string.order_detail_title_open_surplus);
                ivOrderDetailOpenSurplus.setImageResource(R.mipmap.xiala);
            }
        }
        tvBalance.setText(getText(R.string.order_balance_title) +orderDetailBo.getOrderAmount());
        initSignListener(orderDetailBo.getOrderAmount());
    }

    @Override
    public void showSignResult(boolean isSuccess,String message) {
        if(isSuccess){
            if(TextUtils.isEmpty(message)){
                ToastUtil.shortToast(this,getText(R.string.order_sign_success).toString());
            }else{
                ToastUtil.shortToast(this,message);
            }
            // 成功即关闭当前页面，并刷新配送订单列表
            mPresenter.unSubscribe();
            setResult(RESULT_OK);
            finish();
        }else{
            if(TextUtils.isEmpty(message)){
                ToastUtil.shortToast(this,getText(R.string.order_sign_error).toString());
            }else{
                ToastUtil.shortToast(this,message);
            }
        }

    }

    /**
     * 初始化签收按钮监听
     * @param orderAmount
     */
    private void initSignListener(final String orderAmount) {
        RxView.clicks(btnSign)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        alertConfirm(orderAmount);
                    }
                });
    }

    /**
     * 确定收款签收弹出框
     */
    public void alertConfirm(final String orderAmount){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle(getText(R.string.order_confirm_sign));
        builder.setMessage(getText(R.string.order_balance_title2)+orderAmount+getText(R.string.yuan));
        builder.setNegativeButton(getText(R.string.cancel), null);
        builder.setPositiveButton(getText(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mPresenter.orderSign(orderId);
            }
        });
        builder.show();
    }
}
