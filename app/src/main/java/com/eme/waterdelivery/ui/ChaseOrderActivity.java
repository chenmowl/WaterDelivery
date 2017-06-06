package com.eme.waterdelivery.ui;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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
import com.eme.waterdelivery.contract.ChaseOrderContract;
import com.eme.waterdelivery.model.bean.entity.ChaseOrderVo;
import com.eme.waterdelivery.presenter.ChaseOrderPresenter;
import com.eme.waterdelivery.tools.NetworkUtils;
import com.eme.waterdelivery.tools.ToastUtil;
import com.eme.waterdelivery.ui.adapter.ChaseOrderAdapter;
import com.eme.waterdelivery.ui.adapter.FixedAdapter;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * 追欠订单
 * Created by dijiaoliang on 17/4/25.
 */
public class ChaseOrderActivity extends BaseActivity<ChaseOrderPresenter> implements ChaseOrderContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

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

    private int positionConfirm;

    private List<ChaseOrderVo.ResultsBean> mData;

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
        tvTitle.setText(getText(R.string.chase_order));
        btnRight.setVisibility(View.GONE);
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setColorSchemeColors(Color.rgb(47, 223, 189));
        rvContent.setLayoutManager(new LinearLayoutManager(App.getAppInstance()));
        mData = new ArrayList<>();
//        mData.add(new ChaseOrderVo.ResultsBean());
//        mData.add(new ChaseOrderVo.ResultsBean());
//        mData.add(new ChaseOrderVo.ResultsBean());
//        mData.add(new ChaseOrderVo.ResultsBean());
        ChaseOrderAdapter adapter = new ChaseOrderAdapter(mData);
        adapter.setAutoLoadMoreSize(5);
        rvContent.setAdapter(adapter);
        adapter.setOnLoadMoreListener(this, rvContent);
        rvContent.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                super.onItemChildClick(adapter, view, position);
                switch (view.getId()) {
                    case R.id.btn_finish:
                        final ChaseOrderVo.ResultsBean resultsBean=mData.get(position);
                        AlertDialog.Builder builder = new AlertDialog.Builder(ChaseOrderActivity.this);
                        builder.setMessage(getText(R.string.is_confirm_collect_water));
                        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(NetworkUtils.isConnected(ChaseOrderActivity.this)){
                                    positionConfirm=position;
                                    mPresenter.confirmCrefdit(resultsBean.getId());
                                }else{
                                    showNetError();
                                }
                                if (dialog != null) {
                                    dialog.dismiss();
                                }
                            }
                        });
                        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (dialog != null) {
                                    dialog.dismiss();
                                }
                            }
                        });
                        builder.show();
                        break;
                    default:
                        break;
                }
            }
        });
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onRefresh() {
        ((ChaseOrderAdapter) rvContent.getAdapter()).setEnableLoadMore(false);
        mPresenter.requestData(Constant.REFRESH_DOWN);
    }

    @Override
    public void onLoadMoreRequested() {
        swipeRefresh.setEnabled(false);
        mPresenter.requestData(Constant.REFRESH_UP_LOADMORE);
    }

    @Override
    public void netError(int refreshFlag) {
        switch (refreshFlag) {
            case Constant.REFRESH_NORMAL:
                break;
            case Constant.REFRESH_DOWN:
                ((FixedAdapter)rvContent.getAdapter()).setEnableLoadMore(true);
                break;
            case Constant.REFRESH_UP_LOADMORE:
                swipeRefresh.setEnabled(true);
                ((FixedAdapter)rvContent.getAdapter()).loadMoreFail();
                break;
            default:
                break;
        }
        ToastUtil.shortToast(this,getText(R.string.net_error).toString());
    }

    @Override
    public void notifyNoData() {
        ToastUtil.shortToast(this, getText(R.string.no_data).toString());
        swipeRefresh.setEnabled(true);
        ((ChaseOrderAdapter)rvContent.getAdapter()).loadMoreEnd();
    }

    @Override
    public void showProgress(boolean isShow) {
        isShowLayer(llAvLoadingTransparent44, isShow);
    }

    @Override
    public void updateUi(List<ChaseOrderVo.ResultsBean> data, int refreshFlag) {
        switch (refreshFlag) {
            case Constant.REFRESH_NORMAL:
                mData.clear();
                isShowLayer(llAvLoadingTransparent44, false);
                break;
            case Constant.REFRESH_DOWN:
                mData.clear();
                swipeRefresh.setRefreshing(false);
                ((ChaseOrderAdapter)rvContent.getAdapter()).setEnableLoadMore(true);
                break;
            case Constant.REFRESH_UP_LOADMORE:
                swipeRefresh.setEnabled(true);
                break;
            default:
                break;
        }
        mData.addAll(data);
        rvContent.getAdapter().notifyDataSetChanged();
        if(Constant.REFRESH_UP_LOADMORE==refreshFlag){
            ((ChaseOrderAdapter)rvContent.getAdapter()).loadMoreComplete();
        }
    }

    @Override
    public void requestFailure(int refreshFlag) {
        switch (refreshFlag) {
            case Constant.REFRESH_NORMAL:
                isShowLayer(llAvLoadingTransparent44, false);
                break;
            case Constant.REFRESH_DOWN:
                swipeRefresh.setRefreshing(false);
                ((ChaseOrderAdapter) rvContent.getAdapter()).setEnableLoadMore(true);
                break;
            case Constant.REFRESH_UP_LOADMORE:
                ((ChaseOrderAdapter) rvContent.getAdapter()).loadMoreFail();
                swipeRefresh.setEnabled(true);
                break;
            default:
                break;
        }
        ToastUtil.shortToast(this, getText(R.string.request_error).toString());
    }

    @Override
    public void showConfirmCrefditResult(boolean isSuccess) {
        if(isSuccess){
            //追欠订单完结
            ToastUtil.shortToast(this,getText(R.string.crefdit_finish).toString());
            mData.remove(positionConfirm);
            rvContent.getAdapter().notifyDataSetChanged();
        }else{
            //追欠订单完结失败
            ToastUtil.shortToast(this,getText(R.string.crefdit_finish_failure).toString());
        }
    }
}
