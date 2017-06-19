package com.eme.waterdelivery.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eme.waterdelivery.App;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.BaseActivity;
import com.eme.waterdelivery.contract.HomeCollectWaterContract;
import com.eme.waterdelivery.model.bean.entity.TrafficVo;
import com.eme.waterdelivery.presenter.HomeCollectWaterPresenter;
import com.eme.waterdelivery.tools.NetworkUtils;
import com.eme.waterdelivery.tools.ToastUtil;
import com.eme.waterdelivery.ui.adapter.TrafficListAdapter;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * 收水页面
 * <p>
 * Created by dijiaoliang on 17/6/5.
 */
public class HomeCollectWaterActivity extends BaseActivity<HomeCollectWaterPresenter> implements HomeCollectWaterContract.View, BaseQuickAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {



    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.ll_av_loading_transparent_44)
    LinearLayout llAvLoadingTransparent44;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private List<TrafficVo.ResultsBean> mGoodsData;

    @Override
    protected void initInject() {
        getViewComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_home_collect_water;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        tvTitle.setText(getText(R.string.collect_water));
        if (!NetworkUtils.isConnected(this)) {
            showNetError();
            return;
        }
        rvContent.setHasFixedSize(true);
        rvContent.setNestedScrollingEnabled(false);
        LinearLayoutManager manager = new LinearLayoutManager(App.getAppInstance());
        manager.setAutoMeasureEnabled(true);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvContent.setLayoutManager(manager);
        mGoodsData = new ArrayList<>();
        TrafficListAdapter mTicketAdapter=new TrafficListAdapter(mGoodsData);
        mTicketAdapter.setOnItemClickListener(this);
        rvContent.setAdapter(mTicketAdapter);
        initListener();
        mPresenter.requestTrafficList(Constant.ONE);
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
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setColorSchemeColors(Color.rgb(47, 223, 189));
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
    public void showRequestResult(boolean isSuccess, List<TrafficVo.ResultsBean> data, String message) {
        if (isSuccess) {
            //成功
            mGoodsData.clear();
            if (data != null && data.size() != 0) {
                mGoodsData.addAll(data);
            } else {
                ToastUtil.shortToast(this, getText(R.string.empty_traffic).toString());
            }
            rvContent.getAdapter().notifyDataSetChanged();
        } else {
            //失败
            ToastUtil.shortToast(this, getText(R.string.get_traffic_failure).toString());
        }
        if(swipeRefresh.isRefreshing()){
            swipeRefresh.setRefreshing(false);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if(NetworkUtils.isConnected(this)){
            Intent intent=new Intent(this,CollectWaterActivity.class);
            intent.putExtra(Constant.TRAFFIC_NO,mGoodsData.get(position).getTrafficNo());
            startActivityForResult(intent,Constant.REQUEST_CODE_COLLECT_WATER);
        }else{
            showNetError();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Constant.REQUEST_CODE_COLLECT_WATER && resultCode==RESULT_OK){
            mPresenter.requestTrafficList(Constant.ONE);
        }
    }

    @Override
    public void onRefresh() {
        if(NetworkUtils.isConnected(this)){
            mPresenter.requestTrafficListRefresh(Constant.ONE);
        }else{
            showNetError();
            swipeRefresh.setRefreshing(false);
        }
    }
}
