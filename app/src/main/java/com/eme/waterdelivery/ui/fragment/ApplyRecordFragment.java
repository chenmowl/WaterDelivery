package com.eme.waterdelivery.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eme.waterdelivery.App;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.BaseFragment;
import com.eme.waterdelivery.contract.ApplyRecordContract;
import com.eme.waterdelivery.model.bean.entity.HistoryPurchaseVo;
import com.eme.waterdelivery.model.bean.entity.PurchaseBo;
import com.eme.waterdelivery.presenter.ApplyRecordPresenter;
import com.eme.waterdelivery.tools.NetworkUtils;
import com.eme.waterdelivery.tools.ToastUtil;
import com.eme.waterdelivery.ui.ApplyDetailActivity;
import com.eme.waterdelivery.ui.adapter.ApplyRecordAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 申请记录 Fragment
 * <p>
 * Created by dijiaoliang on 17/3/7.
 */
public class ApplyRecordFragment extends BaseFragment<ApplyRecordPresenter> implements ApplyRecordContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.ll_av_loading_transparent_44)
    LinearLayout llAvLoadingTransparent44;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    TextView tvToday;
    TextView tvMonth;
    TextView tvAll;

    private ApplyRecordAdapter applyRecordAdapter;
    private List<PurchaseBo> applyRecordData;

    @Override
    protected void initInject() {
        getViewComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_common_list;
    }

    @Override
    protected void initEventAndData() {
        swipeRefresh.setColorSchemeColors(Color.rgb(47, 223, 189));
        swipeRefresh.setOnRefreshListener(this);
        rvContent.setLayoutManager(new LinearLayoutManager(App.getAppInstance()));
        applyRecordData = new ArrayList<>();
        applyRecordAdapter = new ApplyRecordAdapter(App.getAppInstance(),applyRecordData);
//        applyRecordAdapter.setAutoLoadMoreSize(8);
        applyRecordAdapter.setOnLoadMoreListener(this);
//        applyRecordAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        rvContent.setAdapter(applyRecordAdapter);
        rvContent.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                if(!NetworkUtils.isConnected(mActivity)){
                    ToastUtil.shortToast(mActivity,getText(R.string.net_error).toString());
                    return;
                }
                PurchaseBo bo=applyRecordData.get(position);
                if(bo!=null && bo.getTrafficNo()!=null){
                    Intent intent=new Intent(mActivity, ApplyDetailActivity.class);
                    intent.putExtra(Constant.TRAFFIC_NO,bo.getTrafficNo());
                    startActivity(intent);
                }else {
                    ToastUtil.shortToast(mActivity,getText(R.string.order_info_error).toString());
                }
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
            }
        });

        //// TODO: 2017/3/7 RecyclerView添加头布局
        View v = LayoutInflater.from(mActivity).inflate(R.layout.header_apply, null);
        tvToday = (TextView) v.findViewById(R.id.tv_one);
        tvMonth = (TextView) v.findViewById(R.id.tv_two);
        tvAll = (TextView) v.findViewById(R.id.tv_there);
        applyRecordAdapter.addHeaderView(v);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onRefresh() {
        applyRecordAdapter.setEnableLoadMore(false);
        mPresenter.requestData(Constant.REFRESH_DOWN);
    }

    @Override
    public void onLoadMoreRequested() {
        swipeRefresh.setEnabled(false);
        mPresenter.requestData(Constant.REFRESH_UP_LOADMORE);
    }

    @Override
    public void showProgress(boolean b) {
        isShowLayer(llAvLoadingTransparent44, b);
    }

    @Override
    public void requestFailure(int flag, String message) {
        switch (flag) {
            case Constant.REFRESH_NORMAL:
                isShowLayer(llAvLoadingTransparent44, false);
                break;
            case Constant.REFRESH_DOWN:
                swipeRefresh.setRefreshing(false);
                applyRecordAdapter.setEnableLoadMore(true);
                break;
            case Constant.REFRESH_UP_LOADMORE:
//                applyRecordAdapter.loadMoreFail();
                swipeRefresh.setEnabled(true);
                break;
            default:
                break;
        }
        if (message == null) {
            ToastUtil.shortToast(mActivity, getText(R.string.request_error).toString());
        } else {
            ToastUtil.shortToast(mActivity, message);
        }
    }

    @Override
    public void updateUi(HistoryPurchaseVo data, int flag) {
        switch (flag) {
            case Constant.REFRESH_NORMAL:
                applyRecordData.clear();
                isShowLayer(llAvLoadingTransparent44, false);
                break;
            case Constant.REFRESH_DOWN:
                applyRecordData.clear();
                swipeRefresh.setRefreshing(false);
                applyRecordAdapter.setEnableLoadMore(true);
                break;
            case Constant.REFRESH_UP_LOADMORE:
                swipeRefresh.setEnabled(true);
                break;
            default:
                break;
        }
        applyRecordData.addAll(data.getList());
        applyRecordAdapter.notifyDataSetChanged();
        // TODO: 17/3/27 更新header
        tvAll.setText(TextUtils.concat(getText(R.string.apply_all_title),String.valueOf(data.getPurchaseHistoryOrderSum().getPurchaseHistoryOrderAllSum()),getText(R.string.apply_today_second_rate)));
        tvMonth.setText(TextUtils.concat(getText(R.string.apply_month_title),String.valueOf(data.getPurchaseHistoryOrderSum().getPurchaseHistoryOrderMonthSum()),getText(R.string.apply_today_second_rate)));
        tvToday.setText(TextUtils.concat(getText(R.string.apply_today_title),String.valueOf(data.getPurchaseHistoryOrderSum().getPurchaseHistoryOrderDaySum()),getText(R.string.apply_today_second_rate)));
//        if (applyRecordData.size() < 8) {
//            applyRecordAdapter.loadMoreEnd(true);
//        }
    }

    @Override
    public void notifyNoData() {
        ToastUtil.shortToast(mActivity, getText(R.string.no_data).toString());
        applyRecordAdapter.loadMoreEnd();
//        applyRecordAdapter.setEnableLoadMore(false);
        swipeRefresh.setEnabled(true);
    }

    @Override
    public void netError(int flag) {
        switch (flag) {
            case Constant.REFRESH_NORMAL:
                break;
            case Constant.REFRESH_DOWN:
                applyRecordAdapter.setEnableLoadMore(true);
                break;
            case Constant.REFRESH_UP_LOADMORE:
                swipeRefresh.setEnabled(true);
                break;
            default:
                break;
        }
        ToastUtil.shortToast(mActivity,getText(R.string.net_error).toString());
    }

    public void refreshPage(){
        mPresenter.requestData(Constant.REFRESH_DOWN);
    }
}
