package com.eme.waterdelivery.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eme.waterdelivery.App;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.BaseActivity;
import com.eme.waterdelivery.contract.CollectBucketContract;
import com.eme.waterdelivery.model.bean.entity.BackBarrelBo;
import com.eme.waterdelivery.presenter.CollectBucketPresenter;
import com.eme.waterdelivery.tools.ImageLoader;
import com.eme.waterdelivery.tools.ToastUtil;
import com.eme.waterdelivery.ui.adapter.WBaseRecycleAdapter;
import com.eme.waterdelivery.ui.adapter.WBaseRecycleViewHolder;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * 回桶数量
 * <p>
 * Created by dijiaoliang on 17/4/25.
 */
public class CollectBucketActivity extends BaseActivity<CollectBucketPresenter> implements CollectBucketContract.View {


    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_goods)
    RecyclerView rvGoods;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.ll_av_loading_transparent_44)
    LinearLayout llAvLoadingTransparent44;

    private List<BackBarrelBo> mGoodsData;

    @Override
    protected void initInject() {
        getViewComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_collect_water;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        tvTitle.setText(getText(R.string.collect_bucket));
//        如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvGoods.setHasFixedSize(true);
        rvGoods.setNestedScrollingEnabled(false);
        LinearLayoutManager manager = new LinearLayoutManager(App.getAppInstance());
        manager.setAutoMeasureEnabled(true);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvGoods.setLayoutManager(manager);
        mGoodsData = new ArrayList<>();
        WBaseRecycleAdapter<BackBarrelBo> mTicketAdapter = new WBaseRecycleAdapter<BackBarrelBo>(this, mGoodsData, R.layout.item_collect_water_goods) {
            @Override
            public void onBindViewHolder(WBaseRecycleViewHolder holder, int position, BackBarrelBo s) {
                ImageLoader.load(CollectBucketActivity.this, TextUtils.isEmpty(s.getGoodsImage())? Constant.STR_EMPTY:s.getGoodsImage(), (ImageView) holder.getView(R.id.sdv_good));
                holder.setText(R.id.tv_good_name, TextUtils.isEmpty(s.getGoodsName())? Constant.STR_EMPTY:s.getGoodsName());
                holder.setText(R.id.tv_good_size, "规格: " + (TextUtils.isEmpty(s.getSpecName())? Constant.STR_EMPTY:s.getSpecName()));
                holder.setText(R.id.tv_good_count, "x" + s.getTotalRecoveryNum());
            }
        };
        rvGoods.setAdapter(mTicketAdapter);
        initListener();
        btnConfirm.setVisibility(View.GONE);
        mPresenter.requestBucketData();
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void showProgress(boolean isShow) {
        isShowLayer(llAvLoadingTransparent44, isShow);
    }

    @Override
    public void showRequestResult(boolean isSuccess, List<BackBarrelBo> data) {
        if (isSuccess) {
            if (data != null && data.size() != 0) {
                mGoodsData.clear();
                mGoodsData.addAll(data);
                rvGoods.getAdapter().notifyDataSetChanged();
            } else {
                ToastUtil.shortToast(this, getText(R.string.empty_request_data).toString());
            }
        } else {
            ToastUtil.shortToast(this, getText(R.string.request_data_failure).toString());
        }
    }
}
