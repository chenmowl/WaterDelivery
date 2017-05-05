package com.eme.waterdelivery.ui;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eme.waterdelivery.App;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.BaseActivity;
import com.eme.waterdelivery.contract.SaleTicketRecordContract;
import com.eme.waterdelivery.model.bean.entity.SaleTicketRecordBo;
import com.eme.waterdelivery.presenter.SaleTicketRecordPresenter;
import com.eme.waterdelivery.tools.ToastUtil;
import com.eme.waterdelivery.ui.adapter.SaleTicketRecordAdapter;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * 售票记录
 * Created by dijiaoliang on 17/4/25.
 */
public class SaleTicketRecordActivity extends BaseActivity<SaleTicketRecordPresenter> implements SaleTicketRecordContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

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
    @BindView(R.id.ll_av_loading_transparent_44)
    LinearLayout llAvLoadingTransparent44;

    private List<SaleTicketRecordBo.ListBean> mData;

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
        tvTitle.setText(getText(R.string.sale_ticket_record));
        btnRight.setVisibility(View.GONE);

        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setColorSchemeColors(Color.rgb(47, 223, 189));
        rvContent.setLayoutManager(new LinearLayoutManager(App.getAppInstance()));
        mData = new ArrayList<>();
        SaleTicketRecordAdapter adapter = new SaleTicketRecordAdapter(this, mData);
        adapter.setOnLoadMoreListener(this,rvContent);
        rvContent.setAdapter(adapter);
        initListener();
        mPresenter.requestData(Constant.REFRESH_NORMAL);
    }

    private void initListener() {
        rvContent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
                if (view.getId() == R.id.btn_call) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mData.get(position).getMemberPhone()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
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
    protected void onDestroy() {
        if(rvContent!=null){
            rvContent.removeAllViews();
            rvContent=null;
        }
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        ((SaleTicketRecordAdapter)rvContent.getAdapter()).setEnableLoadMore(false);
        mPresenter.requestData(Constant.REFRESH_DOWN);
    }

    @Override
    public void onLoadMoreRequested() {
        swipeRefresh.setEnabled(false);
        mPresenter.requestData(Constant.REFRESH_UP_LOADMORE);
    }

    @Override
    public void netError(int flag) {
        switch (flag) {
            case Constant.REFRESH_NORMAL:
                break;
            case Constant.REFRESH_DOWN:
                ((SaleTicketRecordAdapter)rvContent.getAdapter()).setEnableLoadMore(true);
                break;
            case Constant.REFRESH_UP_LOADMORE:
                swipeRefresh.setEnabled(true);
                ((SaleTicketRecordAdapter)rvContent.getAdapter()).loadMoreFail();
                break;
            default:
                break;
        }
        ToastUtil.shortToast(this,getText(R.string.net_error).toString());
    }

    @Override
    public void showProgress(boolean b) {
        isShowLayer(llAvLoadingTransparent44,b);
    }

    @Override
    public void notifyNoData() {
        ToastUtil.shortToast(this, getText(R.string.no_data).toString());
        swipeRefresh.setEnabled(true);
        ((SaleTicketRecordAdapter) rvContent.getAdapter()).loadMoreEnd();
    }

    @Override
    public void showRequestListResult(boolean isSuccess, List<SaleTicketRecordBo.ListBean> list, int refreshFlag, String message) {
        if(isSuccess){
            switch (refreshFlag) {
                case Constant.REFRESH_NORMAL:
                    mData.clear();
                    isShowLayer(llAvLoadingTransparent44, false);
                    break;
                case Constant.REFRESH_DOWN:
                    mData.clear();
                    swipeRefresh.setRefreshing(false);
                    ((SaleTicketRecordAdapter)rvContent.getAdapter()).setEnableLoadMore(true);
                    break;
                case Constant.REFRESH_UP_LOADMORE:
                    swipeRefresh.setEnabled(true);
                    break;
                default:
                    break;
            }
            mData.addAll(list);
            rvContent.getAdapter().notifyDataSetChanged();
            if(Constant.REFRESH_UP_LOADMORE==refreshFlag){
                ((SaleTicketRecordAdapter)rvContent.getAdapter()).loadMoreComplete();
            }
        }else{
            switch (refreshFlag) {
                case Constant.REFRESH_NORMAL:
                    isShowLayer(llAvLoadingTransparent44, false);
                    break;
                case Constant.REFRESH_DOWN:
                    swipeRefresh.setRefreshing(false);
                    ((SaleTicketRecordAdapter)rvContent.getAdapter()).setEnableLoadMore(true);
                    break;
                case Constant.REFRESH_UP_LOADMORE:
                    ((SaleTicketRecordAdapter)rvContent.getAdapter()).loadMoreFail();
                    swipeRefresh.setEnabled(true);
                    break;
                default:
                    break;
            }
            if (message == null) {
                ToastUtil.shortToast(this, getText(R.string.request_error).toString());
            } else {
                ToastUtil.shortToast(this, message);
            }
        }
    }


}
