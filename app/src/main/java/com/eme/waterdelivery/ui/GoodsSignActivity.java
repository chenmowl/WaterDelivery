package com.eme.waterdelivery.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.BaseActivity;
import com.eme.waterdelivery.contract.GoodsSignContract;
import com.eme.waterdelivery.model.bean.entity.BucketBean;
import com.eme.waterdelivery.model.bean.entity.OrderSignBean;
import com.eme.waterdelivery.presenter.GoodsSignPresenter;
import com.eme.waterdelivery.tools.ImageLoader;
import com.eme.waterdelivery.tools.NetworkUtils;
import com.eme.waterdelivery.tools.ToastUtil;
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
public class GoodsSignActivity extends BaseActivity<GoodsSignPresenter> implements GoodsSignContract.View {

    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_wx)
    ImageView ivWx;
    @BindView(R.id.btn_sign)
    Button btnSign;
    @BindView(R.id.ll_av_loading_transparent_44)
    LinearLayout llAvLoadingTransparent44;
    @BindView(R.id.tv_order_amount)
    TextView tvOrderAmount;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R.id.ll_list)
    LinearLayout llList;
    @BindView(R.id.ll_wx_image)
    LinearLayout llWxImage;

    OrderSignBean orderSignBean;

    private List<BucketBean> mBucketData;

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
        llList.setVisibility(View.GONE);
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
                        if(NetworkUtils.isConnected(GoodsSignActivity.this)){
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
