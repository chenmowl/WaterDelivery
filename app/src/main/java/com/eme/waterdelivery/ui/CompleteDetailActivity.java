package com.eme.waterdelivery.ui;

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
import com.eme.waterdelivery.App;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.BaseActivity;
import com.eme.waterdelivery.contract.CompleteDetailContract;
import com.eme.waterdelivery.model.bean.entity.OrderDetailBo;
import com.eme.waterdelivery.presenter.CompleteDetailPresenter;
import com.eme.waterdelivery.tools.TimeUtils;
import com.eme.waterdelivery.tools.ToastUtil;
import com.eme.waterdelivery.ui.adapter.SendingDetailGoodAdapter;
import com.eme.waterdelivery.ui.adapter.WBaseRecycleAdapter;
import com.eme.waterdelivery.ui.adapter.WBaseRecycleViewHolder;
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

/**
 * 配送中订单详情
 * <p>
 * Created by dijiaoliang on 17/3/8.
 */
public class CompleteDetailActivity extends BaseActivity<CompleteDetailPresenter> implements CompleteDetailContract.View {

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
    @BindView(R.id.rv_ticket)
    RecyclerView rvTicket;
    @BindView(R.id.ll_water_ticket)
    LinearLayout llWaterTicket;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R.id.iv_arrow)
    ImageView ivArrow;

    private AMap aMap;

    private List<OrderDetailBo.GoodsBean> mData;

    @Override
    protected void initInject() {
        getViewComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_sending_instant;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        btnSign.setVisibility(View.GONE);
        ivArrow.setVisibility(View.GONE);
        map.onCreate(savedInstanceState);
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = map.getMap();
        }
        aMap.clear();
        mapContainer.setScrollView(sv);//MapContainer关联ScrollView 解决地图和ScrollView的事件冲突

//        如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvContent.setHasFixedSize(true);
        rvContent.setNestedScrollingEnabled(false);
        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(App.getAppInstance());
        manager.setAutoMeasureEnabled(true);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvContent.setLayoutManager(manager);
        mData = new ArrayList<>();
        SendingDetailGoodAdapter goodAdapter = new SendingDetailGoodAdapter(this, mData);
        rvContent.setAdapter(goodAdapter);
        rvTicket.setHasFixedSize(true);
        rvTicket.setNestedScrollingEnabled(false);
        FullyLinearLayoutManager manager02 = new FullyLinearLayoutManager(App.getAppInstance());
        manager02.setAutoMeasureEnabled(true);
        manager02.setOrientation(LinearLayoutManager.VERTICAL);
        rvTicket.setLayoutManager(manager02);
        WBaseRecycleAdapter<OrderDetailBo.GoodsBean> ticketAdapter=new WBaseRecycleAdapter<OrderDetailBo.GoodsBean>(this,mData,R.layout.item_sending_instant_ticket) {
            @Override
            public void onBindViewHolder(WBaseRecycleViewHolder holder, int position, OrderDetailBo.GoodsBean goodsBean) {
                holder.getView(R.id.ll_number).setVisibility(View.GONE);
                TextView ticketNum=holder.getView(R.id.tv_ticket_num);
                ticketNum.setVisibility(View.VISIBLE);
                ticketNum.setText("X"+String.valueOf(goodsBean.getTicketUsedCount()));
                holder.setText(R.id.tv_ticket_type,goodsBean.getTicketName());
            }
        };
        rvTicket.setAdapter(ticketAdapter);


        initListener();

        String orderId = getIntent().getStringExtra(Constant.ORDER_ID);
        tvTitle.setText(orderId);
        mPresenter.requestData(orderId);//请求订单详情数据
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        //        设置btn监听,防止连续的二次点击
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
        isShowLayer(llAvLoadingTransparent44, b);
    }

    @Override
    public void showRequestMsg(String msg) {
        if (TextUtils.isEmpty(msg)) {
            ToastUtil.shortToast(this, getText(R.string.request_error).toString());
        } else {
            ToastUtil.shortToast(this, msg);
        }
    }

    @Override
    public void updateUi(OrderDetailBo orderDetailBo) {
        tvOrderDetailPlaceTime.setText(TextUtils.isEmpty(orderDetailBo.getCreateTime()) ? Constant.STR_EMPTY : orderDetailBo.getCreateTime());
        switch (orderDetailBo.getPayType()) {
            case Constant.PAY_TYPE_MONEY:
                tvPayType.setText(getText(R.string.order_pay_mode_money));
                break;
            case Constant.PAY_TYPE_WEIXIN:
                tvPayType.setText(getText(R.string.order_pay_mode_weixin));
                break;
            default:
                tvPayType.setText(getText(R.string.order_pay_mode_debt));
                break;
        }
        switch (orderDetailBo.getPayMethod()) {
            case Constant.PAY_METHOD_RECEIVE:
                tvOrderDetailPayMode.setText(getText(R.string.pay_method_receive));
                break;
            case Constant.PAY_METHOD_ONLINE:
                tvOrderDetailPayMode.setText(getText(R.string.pay_method_online));
                break;
            default:
                break;
        }
        String createTime = orderDetailBo.getCreateTime();
        String finishTime = orderDetailBo.getFinnshedTime();
        if (TextUtils.isEmpty(createTime) || TextUtils.isEmpty(finishTime)) {
            //为空是立即送达
            tvOrderDetailUsedTime.setText(getText(R.string.used_time_error));
        } else {
            tvOrderDetailUsedTime.setText(TimeUtils.getTimeDifference(createTime, finishTime));
        }
        tvReceiver.setText(getText(R.string.order_receiver_title) + (TextUtils.isEmpty(orderDetailBo.getMemberName()) ? Constant.STR_EMPTY : orderDetailBo.getMemberName()));
        String areaInfo=orderDetailBo.getMemberAreaInfo();
        String address=orderDetailBo.getMemberAddress();
        String addressInfo;
        if(TextUtils.isEmpty(areaInfo)){
            addressInfo=TextUtils.isEmpty(address)?Constant.STR_EMPTY:address;
        }else{
            addressInfo=TextUtils.isEmpty(address)?areaInfo:TextUtils.concat(areaInfo,",",address).toString();
        }
        tvAddress.setText(getText(R.string.order_address_title) + addressInfo);
        tvRemark.setText(TextUtils.isEmpty(orderDetailBo.getOrderMessage()) ? Constant.STR_EMPTY : orderDetailBo.getOrderMessage());
        tvOrderDetailClientPhone.setText(TextUtils.isEmpty(orderDetailBo.getServicePhone()) ? Constant.STR_EMPTY : orderDetailBo.getServicePhone());
        tvOrderDetailCustomerPhone.setText(TextUtils.isEmpty(orderDetailBo.getMemberPhone()) ? Constant.STR_EMPTY : orderDetailBo.getMemberPhone());
        List<OrderDetailBo.GoodsBean> data = orderDetailBo.getGoods();
        mData.clear();
        mData.addAll(data);
        if (orderDetailBo.isWaterOrder()) {
            boolean hasTicket=false;
            for(OrderDetailBo.GoodsBean bean:mData){
                if(bean.getTicketUsedCount()!=0){
                    if(!hasTicket)hasTicket=true;
                }
            }
            if(hasTicket){
                llWaterTicket.setVisibility(View.VISIBLE);
                rvTicket.getAdapter().notifyDataSetChanged();
            }else{
                llWaterTicket.setVisibility(View.GONE);
            }
        } else {
            llWaterTicket.setVisibility(View.GONE);
        }
        rvContent.getAdapter().notifyDataSetChanged();

        tvBalance.setText(getText(R.string.order_balance_title) + (TextUtils.isEmpty(orderDetailBo.getOrderAmount()) ? Constant.STR_EMPTY : orderDetailBo.getOrderAmount()));
        // TODO: 17/4/10 更新地图坐标
        float memberLat = orderDetailBo.getMemberLat();
        float memberLng = orderDetailBo.getMemberLng();
        if (memberLat == 0 || memberLng == 0) {
            memberLat = 39.983456f;
            memberLng = 116.3154950f;
        }
        changeCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(memberLat, memberLng), 18, 30, 30)));
        aMap.clear();
        aMap.addMarker(new MarkerOptions().position(new LatLng(memberLat, memberLng))
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    }
}
