package com.eme.waterdelivery.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.BaseActivity;
import com.eme.waterdelivery.contract.ApplyDetailContract;
import com.eme.waterdelivery.presenter.ApplyDetailPresenter;
import com.eme.waterdelivery.ui.adapter.ApplyDetailMenuAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dijiaoliang on 17/3/13.
 */

public class ApplyDetailActivity extends BaseActivity<ApplyDetailPresenter> implements ApplyDetailContract.View {


    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @BindView(R.id.rv_apply_record)
    RecyclerView rvApplyRecord;
    @BindView(R.id.ll_av_loading_transparent_44)
    LinearLayout llAvLoadingTransparent44;

    private ApplyDetailMenuAdapter adapter;
    private List<String> data;

    private View headerView;
    private View footerView;

    @Override
    protected void initInject() {
        getViewComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_apply_detail;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        data = new ArrayList<>();
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        adapter = new ApplyDetailMenuAdapter(data);
//        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvApplyRecord.setLayoutManager(manager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvApplyRecord.setHasFixedSize(true);
//        rvApplyRecord.addItemDecoration(new SpacesItemDecoration(10));
        rvApplyRecord.setAdapter(adapter);
        headerView = LayoutInflater.from(this).inflate(R.layout.header_apply_detail, null, false);
        footerView = LayoutInflater.from(this).inflate(R.layout.footer_apply_detail, null, false);
        adapter.addHeaderView(headerView);
        adapter.addFooterView(footerView);

        rvApplyRecord.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
            }
        });

        initListener();
    }

    private void initListener() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
