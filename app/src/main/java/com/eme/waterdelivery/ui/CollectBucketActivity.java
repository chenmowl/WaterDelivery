package com.eme.waterdelivery.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.eme.waterdelivery.App;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.BaseActivity;
import com.eme.waterdelivery.contract.CollectBucketContract;
import com.eme.waterdelivery.presenter.CollectBucketPresenter;
import com.eme.waterdelivery.ui.adapter.WBaseRecycleAdapter;
import com.eme.waterdelivery.ui.adapter.WBaseRecycleViewHolder;
import com.eme.waterdelivery.widget.FullyLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 回桶数量
 *
 * Created by dijiaoliang on 17/4/25.
 */
public class CollectBucketActivity extends BaseActivity<CollectBucketPresenter> implements CollectBucketContract.View {

    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_car_number)
    TextView tvCarNumber;
    @BindView(R.id.tv_driver)
    TextView tvDriver;
    @BindView(R.id.rv_goods)
    RecyclerView rvGoods;
    @BindView(R.id.sv)
    ScrollView sv;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.ll_av_loading_transparent_44)
    LinearLayout llAvLoadingTransparent44;

    private List<String> mGoodsData;

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
        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(App.getAppInstance());
        manager.setAutoMeasureEnabled(true);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvGoods.setLayoutManager(manager);
        mGoodsData = new ArrayList<>();
        mGoodsData.add("1");
        mGoodsData.add("1");
        mGoodsData.add("1");
        mGoodsData.add("1");
        mGoodsData.add("1");
        mGoodsData.add("1");
        mGoodsData.add("1");
        WBaseRecycleAdapter<String> mTicketAdapter = new WBaseRecycleAdapter<String>(this, mGoodsData, R.layout.item_collect_water_goods) {
            @Override
            public void onBindViewHolder(WBaseRecycleViewHolder holder, int position, String s) {

            }
        };
        rvGoods.setAdapter(mTicketAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
