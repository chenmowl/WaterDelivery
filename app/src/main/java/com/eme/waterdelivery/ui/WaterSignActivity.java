package com.eme.waterdelivery.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.eme.waterdelivery.App;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.BaseActivity;
import com.eme.waterdelivery.contract.WaterSignContract;
import com.eme.waterdelivery.model.bean.entity.BucketBean;
import com.eme.waterdelivery.model.bean.entity.OrderSignBean;
import com.eme.waterdelivery.presenter.WaterSignPresenter;
import com.eme.waterdelivery.tools.ImageLoader;
import com.eme.waterdelivery.tools.NetworkUtils;
import com.eme.waterdelivery.tools.ToastUtil;
import com.eme.waterdelivery.ui.adapter.WBaseRecycleAdapter;
import com.eme.waterdelivery.ui.adapter.WBaseRecycleViewHolder;
import com.eme.waterdelivery.widget.FullyLinearLayoutManager;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * 订单签收页面
 * <p>
 * Created by dijiaoliang on 17/4/24.
 */
public class WaterSignActivity extends BaseActivity<WaterSignPresenter> implements WaterSignContract.View {

    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_wx)
    ImageView ivWx;
    @BindView(R.id.rv_ticket)
    RecyclerView rvTicket;
    @BindView(R.id.rv_bucket)
    RecyclerView rvBucket;
    @BindView(R.id.btn_sign)
    Button btnSign;
    @BindView(R.id.ll_av_loading_transparent_44)
    LinearLayout llAvLoadingTransparent44;
    @BindView(R.id.tv_order_amount)
    TextView tvOrderAmount;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R.id.ll_wx_image)
    LinearLayout llWxImage;

    private List<BucketBean> mBucketData;

    OrderSignBean orderSignBean;

    @Override
    protected void initInject() {
        getViewComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_sign;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        tvTitle.setText(getText(R.string.order_detail_sign));
        orderSignBean = getIntent().getParcelableExtra(Constant.ORDER_SIGN_BEAN);
        tvOrderAmount.setText(orderSignBean.getOrderAmount());
        switch (orderSignBean.getPayType()) {
            case Constant.PAY_TYPE_MONEY:
                tvPayType.setText(getText(R.string.order_pay_mode_money));
                llWxImage.setVisibility(View.GONE);
                break;
            case Constant.PAY_TYPE_WEIXIN:
                tvPayType.setText(getText(R.string.order_pay_mode_weixin));
                llWxImage.setVisibility(View.VISIBLE);
                if (NetworkUtils.isConnected(this)) {
                    mPresenter.getWXImage();
                } else {
                    showNetError();
                }
                break;
            case Constant.PAY_TYPE_DEBT:
                tvPayType.setText(getText(R.string.order_pay_mode_debt));
                llWxImage.setVisibility(View.GONE);
                break;
            default:
                break;
        }
        Bundle bundle = getIntent().getExtras();
        ArrayList<BucketBean> payAmountGoods = bundle.getParcelableArrayList(Constant.ORDER_BUCKETS);
        mBucketData = new ArrayList<>();
        mBucketData.addAll(payAmountGoods);
        // 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvTicket.setHasFixedSize(true);
        rvTicket.setNestedScrollingEnabled(false);
        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(App.getAppInstance());
        manager.setAutoMeasureEnabled(true);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvTicket.setLayoutManager(manager);
        WBaseRecycleAdapter<BucketBean> mTicketAdapter = new WBaseRecycleAdapter<BucketBean>(this, mBucketData, R.layout.item_sign_ticket) {
            @Override
            public void onBindViewHolder(WBaseRecycleViewHolder holder, int position, BucketBean s) {
                holder.setText(R.id.tv_title, s.getTicketName());
                holder.setText(R.id.tv_value, String.valueOf(s.getTicketUsedCount()));
            }
        };
        rvTicket.setAdapter(mTicketAdapter);

//                如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvBucket.setHasFixedSize(true);
        rvBucket.setNestedScrollingEnabled(false);
        FullyLinearLayoutManager bucketManager = new FullyLinearLayoutManager(App.getAppInstance());
        bucketManager.setAutoMeasureEnabled(true);
        bucketManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvBucket.setLayoutManager(bucketManager);
        WBaseRecycleAdapter<BucketBean> mBucketAdapter = new WBaseRecycleAdapter<BucketBean>(this, mBucketData, R.layout.item_sign_bucket) {
            @Override
            public void onBindViewHolder(final WBaseRecycleViewHolder holder, int position, BucketBean s) {
                final int shouldCount = s.getGoodsNumber() - s.getMortgageBucketCount() - s.getSellBucketCount();
                holder.setText(R.id.tv_title, s.getBucketName());
                holder.setText(R.id.tv_should_count, TextUtils.concat(String.valueOf(shouldCount), getText(R.string.bucket)).toString());
                holder.setText(R.id.tv_debt_count, TextUtils.concat(String.valueOf(shouldCount), getText(R.string.bucket)).toString());
                final EditText editText = holder.getView(R.id.et_real_count);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        String text = editable.toString().trim();
                        int len = text.length();
                        if (len == 1 && text.equals("0")) {
                            editable.clear();
                        }
                        int realCount;
                        if (TextUtils.isEmpty(text)) {
                            realCount = 0;
                        } else {
                            realCount = Integer.parseInt(text);
                        }
                        if (realCount > shouldCount) {
                            realCount = shouldCount;
                            editText.setText(String.valueOf(shouldCount));
                            ToastUtil.shortToast(WaterSignActivity.this, R.string.tip_bucket);
                        }
                        holder.setText(R.id.tv_debt_count, TextUtils.concat(String.valueOf(shouldCount - realCount), getText(R.string.bucket)).toString());
                    }
                });
            }
        };
        rvBucket.setAdapter(mBucketAdapter);
        initListener();
    }

    private void initListener() {
        RxView.clicks(back)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        finish();
                    }
                });
        RxView.clicks(btnSign)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if(NetworkUtils.isConnected(WaterSignActivity.this)){
                            mPresenter.sign(orderSignBean.getOrderId(),orderSignBean.getPayType(), JSON.toJSONString(mBucketData));
                        }else{
                            showNetError();
                        }
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
    public void loadWXImage(String url) {
        ImageLoader.load(this, url, ivWx);
    }

    @Override
    public void getImageFailure(String message) {
        if (TextUtils.isEmpty(message)) {
            ToastUtil.shortToast(this, R.string.get_wx_image_failure);
        } else {
            ToastUtil.shortToast(this, message);
        }
    }

    @Override
    public void showProgress(boolean b) {
        if(b){
            llAvLoadingTransparent44.setVisibility(View.VISIBLE);
        }else{
            llAvLoadingTransparent44.setVisibility(View.GONE);
        }
    }

    @Override
    public void showSignResult(boolean isSuccess, String message) {
        if(isSuccess){
            //确认签收成功
            if(!TextUtils.isEmpty(message)){
                ToastUtil.shortToast(this,message);
            }else{
                ToastUtil.shortToast(this,R.string.sign_success);
            }
            setResult(RESULT_OK);
            finish();
        }else{
            //确认签收失败
            if(!TextUtils.isEmpty(message)){
                ToastUtil.shortToast(this,message);
            }else{
                ToastUtil.shortToast(this,R.string.sign_failure);
            }
        }
    }
}
