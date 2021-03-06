package com.eme.waterdelivery.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eme.waterdelivery.App;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.BaseActivity;
import com.eme.waterdelivery.contract.FixedDetailContract;
import com.eme.waterdelivery.model.bean.entity.BucketBean;
import com.eme.waterdelivery.model.bean.entity.OrderAmountGoodsBean;
import com.eme.waterdelivery.model.bean.entity.OrderDetailBo;
import com.eme.waterdelivery.model.bean.entity.OrderSignBean;
import com.eme.waterdelivery.presenter.FixedDetailPresenter;
import com.eme.waterdelivery.tools.ConstUtils;
import com.eme.waterdelivery.tools.NetworkUtils;
import com.eme.waterdelivery.tools.TimeUtils;
import com.eme.waterdelivery.tools.ToastUtil;
import com.eme.waterdelivery.ui.adapter.FixedDetailGoodAdapter;
import com.eme.waterdelivery.ui.adapter.SendingFixedTicketAdapter;
import com.eme.waterdelivery.ui.dialog.PayTypeDialog;
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
 * 固定订单详情
 * <p>
 * Created by dijiaoliang on 17/3/8.
 */
public class FixedDetailActivity extends BaseActivity<FixedDetailPresenter> implements FixedDetailContract.View{

    public static final int FIXED_REQUEST_CODE = 1101;

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
    @BindView(R.id.rl_choose_pay_type)
    RelativeLayout rlChoosePayType;
    @BindView(R.id.rv_ticket)
    RecyclerView rvTicket;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;

    private AMap aMap;

    private List<OrderDetailBo.GoodsBean> mData;

    private String orderId;
    private String payType;
    private String orderAmount;

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
        map.onCreate(savedInstanceState);
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = map.getMap();
        }
        // TODO: 17/3/8  改变可视区域,添加坐标点 
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
        final FixedDetailGoodAdapter goodAdapter = new FixedDetailGoodAdapter(mData);
        rvContent.setAdapter(goodAdapter);
        rvContent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
                OrderDetailBo.GoodsBean goodsBean = mData.get(position);
                switch (view.getId()) {
                    case R.id.tv_ya:
                        alertBucketDialog(adapter, goodsBean, false, position);
                        break;
                    case R.id.tv_sale:
                        alertBucketDialog(adapter, goodsBean, true, position);
                        break;
                    case R.id.btn_reduce:
                        int goodsNum = goodsBean.getGoodsNum();
                        if (goodsNum <= 0) {
                            ToastUtil.shortToast(FixedDetailActivity.this, R.string.reach_zero);
                            return;
                        }
                        goodsBean.setGoodsNum(goodsNum - 1);
                        goodsBean.setTicketUsedCount(goodsNum - 1);
                        goodsBean.setMortgageBucketCount(0);
                        goodsBean.setSellBucketCount(0);
                        adapter.notifyItemChanged(position);
                        rvTicket.getAdapter().notifyItemChanged(position);
                        updateAmount();
                        break;
                    case R.id.btn_add:
                        int goodNum = goodsBean.getGoodsNum() + 1;
                        goodsBean.setGoodsNum(goodNum);
                        goodsBean.setTicketUsedCount(goodNum);
                        goodsBean.setMortgageBucketCount(0);
                        goodsBean.setSellBucketCount(0);
                        adapter.notifyItemChanged(position);
                        rvTicket.getAdapter().notifyItemChanged(position);
                        updateAmount();
                        break;
                    default:
                        break;
                }
            }
        });

        rvTicket.setHasFixedSize(true);
        rvTicket.setNestedScrollingEnabled(false);
        FullyLinearLayoutManager manager02 = new FullyLinearLayoutManager(App.getAppInstance());
        manager02.setAutoMeasureEnabled(true);
        manager02.setOrientation(LinearLayoutManager.VERTICAL);
        rvTicket.setLayoutManager(manager02);
        SendingFixedTicketAdapter sendingDetailGoodAdapter = new SendingFixedTicketAdapter(mData);
        rvTicket.setAdapter(sendingDetailGoodAdapter);
        rvTicket.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
                OrderDetailBo.GoodsBean goodsBean = mData.get(position);
                switch (view.getId()) {
                    case R.id.btn_reduce:
                        int goodsNum = goodsBean.getTicketUsedCount();
                        if (goodsNum <= 0) {
                            ToastUtil.shortToast(FixedDetailActivity.this, R.string.reach_zero);
                            return;
                        }
                        goodsBean.setTicketUsedCount(goodsNum - 1);
                        adapter.notifyItemChanged(position);
                        updateAmount();
                        break;
                    case R.id.btn_add:
                        int goodNum = goodsBean.getTicketUsedCount() + 1;
                        if (goodNum > goodsBean.getGoodsNum()) {
                            ToastUtil.shortToast(FixedDetailActivity.this, R.string.tip_ticket);
                            return;
                        }
                        goodsBean.setTicketUsedCount(goodNum);
                        adapter.notifyItemChanged(position);
                        updateAmount();
                        break;
                    default:
                        break;
                }
            }
        });
        initListener();
        orderId = getIntent().getStringExtra(Constant.ORDER_ID);
        tvTitle.setText(orderId);
        mPresenter.requestData(orderId);//请求订单详情数据
    }

    /**
     * 更新订单价格
     */
    public void updateAmount() {
        if (!NetworkUtils.isConnected(App.getAppInstance())) {
            ToastUtil.shortToast(this, R.string.update_order_amount_failure);
            return;
        }
        List<OrderAmountGoodsBean> list = new ArrayList<>();
        for (OrderDetailBo.GoodsBean goodsBean : mData) {
            OrderAmountGoodsBean orderAmountGoodsBean = new OrderAmountGoodsBean();
            orderAmountGoodsBean.setGoodsId(goodsBean.getGoodsId());
            orderAmountGoodsBean.setGoodsNumber(goodsBean.getGoodsNum());
            orderAmountGoodsBean.setTicketUsedCount(goodsBean.getTicketUsedCount());
            orderAmountGoodsBean.setMortgageBucketCount(goodsBean.getMortgageBucketCount());
            orderAmountGoodsBean.setSellBucketCount(goodsBean.getSellBucketCount());
            list.add(orderAmountGoodsBean);
        }
        mPresenter.updateAmount(orderId, JSON.toJSONString(list));
    }

    AlertDialog dialog;

    /**
     * 弹出押售桶的dialog
     *
     * @param goodsBean
     * @param isSellBucket
     */
    public void alertBucketDialog(final BaseQuickAdapter adapter, final OrderDetailBo.GoodsBean goodsBean, final boolean isSellBucket, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(FixedDetailActivity.this);
        View dialogView = LayoutInflater.from(FixedDetailActivity.this).inflate(R.layout.dialog_add_bucket, null, false);
        TextView tvContent = (TextView) dialogView.findViewById(R.id.tv_content);
        String rn = getText(R.string.order_nr).toString();
        String content = (String) TextUtils.concat(getText(R.string.order_good_name).toString(), goodsBean.getGoodsName(), rn, getText(R.string.order_good_size).toString(), goodsBean.getSpecName(), rn, getText(R.string.order_good_price).toString(), goodsBean.getGoodsPrice());
        tvContent.setText(content);
        final AppCompatEditText amount = (AppCompatEditText) dialogView.findViewById(R.id.et_amount);
//        amount.addTextChangedListener(new ZeroTextWatcher());
        AppCompatButton cancel = (AppCompatButton) dialogView.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        AppCompatButton confirm = (AppCompatButton) dialogView.findViewById(R.id.btn_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String count = amount.getText().toString().trim();
                if (TextUtils.isEmpty(count)) {
                    ToastUtil.shortToast(FixedDetailActivity.this, R.string.fixed_input_empty);
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                } else {
                    if (isSellBucket) {
                        if ((goodsBean.getMortgageBucketCount() + Integer.parseInt(count)) > goodsBean.getGoodsNum()) {
                            ToastUtil.shortToast(FixedDetailActivity.this, R.string.tip_ya_sale);
                        } else {
                            goodsBean.setSellBucketCount(Integer.parseInt(count));
                            adapter.notifyItemChanged(position);
                            updateAmount();
                        }
                    } else {
                        if ((goodsBean.getSellBucketCount() + Integer.parseInt(count)) > goodsBean.getGoodsNum()) {
                            ToastUtil.shortToast(FixedDetailActivity.this, R.string.tip_ya_sale);
                        } else {
                            goodsBean.setMortgageBucketCount(Integer.parseInt(count));
                            adapter.notifyItemChanged(position);
                            updateAmount();
                        }
                    }
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                }
            }
        });
        builder.setView(dialogView);
        dialog = builder.show();
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
                        finish();
                    }
                });
        RxView.clicks(rlChoosePayType)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        popPayTypeDialog();
                    }
                });
    }

    private void popPayTypeDialog() {
        final PayTypeDialog mDialog = new PayTypeDialog();
        mDialog.setOnChoosePayTypeListener(new PayTypeDialog.OnChoosePayTypeListener() {
            @Override
            public void chooseType(String flag) {
                switch (flag) {
                    case Constant.PAY_TYPE_MONEY:
                        tvPayType.setText(getText(R.string.order_pay_mode_money));
                        payType = Constant.PAY_TYPE_MONEY;
                        break;
                    case Constant.PAY_TYPE_WEIXIN:
                        tvPayType.setText(getText(R.string.order_pay_mode_weixin));
                        payType = Constant.PAY_TYPE_WEIXIN;
                        break;
                    case Constant.PAY_TYPE_DEBT:
                        tvPayType.setText(getText(R.string.order_pay_mode_debt));
                        payType = Constant.PAY_TYPE_DEBT;
                        break;
                    default:
                        break;
                }
                mDialog.cancel();
            }
        });
        mDialog.showDialog(this, null);
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
                payType = Constant.PAY_TYPE_MONEY;
                break;
            case Constant.PAY_TYPE_WEIXIN:
                tvPayType.setText(getText(R.string.order_pay_mode_weixin));
                payType = Constant.PAY_TYPE_WEIXIN;
                break;
            case Constant.PAY_TYPE_DEBT:
                tvPayType.setText(getText(R.string.order_pay_mode_debt));
                payType = Constant.PAY_TYPE_DEBT;
                break;
            default:
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
        if (!TextUtils.isEmpty(orderDetailBo.getShippingTime())) {
            tvOrderDetailUsedTime.setText(TimeUtils.getIntervalTime(TimeUtils.getCurTimeString(), orderDetailBo.getShippingTime(), ConstUtils.TimeUnit.MIN) + getText(R.string.order_minute).toString());
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
        rvContent.getAdapter().notifyDataSetChanged();
        rvTicket.getAdapter().notifyDataSetChanged();
        String amount = orderDetailBo.getOrderAmount();
        orderAmount = amount;
        tvBalance.setText(getText(R.string.order_balance_title) + (TextUtils.isEmpty(amount) ? Constant.STR_EMPTY : amount));
        initSignListener();
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

    @Override
    public void showSignResult(boolean isSuccess, String message) {
        if (isSuccess) {
            if (TextUtils.isEmpty(message)) {
                ToastUtil.shortToast(this, getText(R.string.order_sign_success).toString());
            } else {
                ToastUtil.shortToast(this, message);
            }
            // 成功即关闭当前页面，并刷新配送订单列表
            setResult(RESULT_OK);
            finish();
        } else {
            if (TextUtils.isEmpty(message)) {
                ToastUtil.shortToast(this, getText(R.string.order_sign_error).toString());
            } else {
                ToastUtil.shortToast(this, message);
            }
        }

    }

    @Override
    public void showUpdateAmount(boolean isRequestSuccess, String amount) {
        if (isRequestSuccess) {
            tvBalance.setText(TextUtils.concat(getText(R.string.order_balance_title), amount));
            orderAmount = amount;
        } else {
            ToastUtil.shortToast(this, R.string.update_order_amount_failure);
        }
    }

    /**
     * 初始化签收按钮监听
     */
    private void initSignListener() {
        RxView.clicks(btnSign)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if (TextUtils.isEmpty(orderId)) {
                            ToastUtil.shortToast(FixedDetailActivity.this, getText(R.string.order_info_error).toString());
                        } else {
//                            alertConfirm(orderAmount);
                            Intent intent = new Intent(FixedDetailActivity.this, WaterSignActivity.class);
                            OrderSignBean orderSignBean = new OrderSignBean();
                            orderSignBean.setOrderAmount(orderAmount);
                            orderSignBean.setOrderId(orderId);
                            orderSignBean.setPayType(payType);
                            ArrayList<BucketBean> payAmountGoods = new ArrayList();
                            for (OrderDetailBo.GoodsBean goodsBean : mData) {
                                BucketBean bucketBean = new BucketBean();
                                bucketBean.setGoodsId(goodsBean.getGoodsId());
                                bucketBean.setGoodsNumber(goodsBean.getGoodsNum());
                                bucketBean.setTicketName(goodsBean.getTicketName());
                                bucketBean.setTicketUsedCount(goodsBean.getTicketUsedCount());
                                bucketBean.setBucketName(goodsBean.getBucketName());
                                bucketBean.setMortgageBucketCount(goodsBean.getMortgageBucketCount());
                                bucketBean.setSellBucketCount(goodsBean.getSellBucketCount());
                                payAmountGoods.add(bucketBean);
                            }
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList(Constant.ORDER_BUCKETS, payAmountGoods);
                            intent.putExtra(Constant.ORDER_SIGN_BEAN, orderSignBean);
                            intent.putExtras(bundle);
                            startActivityForResult(intent, FIXED_REQUEST_CODE);
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FIXED_REQUEST_CODE && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }

}
