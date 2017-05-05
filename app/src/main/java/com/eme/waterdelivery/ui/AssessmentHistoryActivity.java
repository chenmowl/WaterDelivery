package com.eme.waterdelivery.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eme.waterdelivery.App;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.BaseActivity;
import com.eme.waterdelivery.contract.AssessmentHistoryContract;
import com.eme.waterdelivery.presenter.AssessmentHistoryPresenter;
import com.eme.waterdelivery.ui.adapter.AssessmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dijiaoliang on 17/4/25.
 */

public class AssessmentHistoryActivity extends BaseActivity<AssessmentHistoryPresenter> implements AssessmentHistoryContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_right)
    TextView btnRight;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private LayoutInflater inflater;

    private List<String> mData;

    @Override
    protected void initInject() {
        getViewComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_assessment;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        tvTitle.setText(getText(R.string.assessment_history));
        btnRight.setVisibility(View.GONE);

        inflater=LayoutInflater.from(App.getAppInstance());
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setColorSchemeColors(Color.rgb(47, 223, 189));
        rvContent.setLayoutManager(new LinearLayoutManager(App.getAppInstance()));
        mData = new ArrayList<>();
        mData.add("1");
        mData.add("1");
        mData.add("1");
        AssessmentAdapter adapter=new AssessmentAdapter(this,mData);
        rvContent.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onRefresh() {

    }
}
