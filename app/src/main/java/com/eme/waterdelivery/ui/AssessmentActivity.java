package com.eme.waterdelivery.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eme.waterdelivery.App;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.BaseActivity;
import com.eme.waterdelivery.contract.AssessmentContract;
import com.eme.waterdelivery.presenter.AssessmentPresenter;
import com.eme.waterdelivery.ui.adapter.AssessmentAdapter;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * 应缴金额
 *
 * Created by dijiaoliang on 17/4/25.
 */
public class AssessmentActivity extends BaseActivity<AssessmentPresenter> implements AssessmentContract.View, SwipeRefreshLayout.OnRefreshListener {

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

    private LinearLayout llHeader;
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
        tvTitle.setText(getText(R.string.assessment));
        btnRight.setText(getText(R.string.assessment_history));
        btnRight.setTextColor(getResources().getColor(R.color.main_color_red));

        inflater=LayoutInflater.from(App.getAppInstance());
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setColorSchemeColors(Color.rgb(47, 223, 189));
        rvContent.setLayoutManager(new LinearLayoutManager(App.getAppInstance()));
        mData = new ArrayList<>();
        mData.add("1");
        mData.add("1");
        mData.add("1");
        AssessmentAdapter adapter=new AssessmentAdapter(this,mData);
//        currentDayAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        rvContent.setAdapter(adapter);

        // TODO: 2017/3/7 RecyclerView添加头布局
        llHeader = (LinearLayout) inflater.inflate(R.layout.header_recycler, null,false);
        adapter.addHeaderView(llHeader);
        initListener();
    }

    private void initListener() {
        RxView.clicks(btnRight)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {

                    @Override
                    public void accept(Object o) throws Exception {
                        startActivity(new Intent(AssessmentActivity.this, AssessmentHistoryActivity.class));
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
    public void onDestroy() {
        llHeader=null;
        if(rvContent!=null){
            rvContent.removeAllViews();
            rvContent=null;
        }
        super.onDestroy();
    }

    @Override
    public void onRefresh() {

    }
}
