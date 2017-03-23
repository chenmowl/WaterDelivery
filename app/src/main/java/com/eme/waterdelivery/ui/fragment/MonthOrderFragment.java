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
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.BaseFragment;
import com.eme.waterdelivery.contract.MonthOrderContract;
import com.eme.waterdelivery.model.bean.entity.HistoryOrderSumBo;
import com.eme.waterdelivery.model.bean.entity.HistoryOrderVo;
import com.eme.waterdelivery.model.bean.entity.WaitingOrderBo;
import com.eme.waterdelivery.presenter.MonthOrderPresenter;
import com.eme.waterdelivery.tools.ToastUtil;
import com.eme.waterdelivery.ui.CompleteActivity;
import com.eme.waterdelivery.ui.CompleteDetailActivity;
import com.eme.waterdelivery.ui.adapter.CurrentDayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 当日接单
 *
 * Created by dijiaoliang on 17/3/7.
 */
public class MonthOrderFragment extends BaseFragment<MonthOrderPresenter> implements MonthOrderContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.ll_av_loading_transparent_44)
    LinearLayout llAvLoadingTransparent44;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private LinearLayout llHeader;
    private LayoutInflater inflater;

    private CurrentDayAdapter currentDayAdapter;
    private List<WaitingOrderBo> currentDayData;

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
        inflater=LayoutInflater.from(getActivity());
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setColorSchemeColors(Color.rgb(47, 223, 189));
        rvContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        currentDayData = new ArrayList<>();
        currentDayAdapter = new CurrentDayAdapter(getActivity(),currentDayData);
        currentDayAdapter.setOnLoadMoreListener(this);
//        currentDayAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        rvContent.setAdapter(currentDayAdapter);
        rvContent.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                startActivity(new Intent(getActivity(), CompleteDetailActivity.class));
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
            }
        });

        //// TODO: 2017/3/7 RecyclerView添加头布局
        llHeader = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.header_recycler, null);
        currentDayAdapter.addHeaderView(llHeader);
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
        currentDayAdapter.setEnableLoadMore(false);
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
                currentDayAdapter.setEnableLoadMore(true);
                break;
            case Constant.REFRESH_UP_LOADMORE:
                currentDayAdapter.loadMoreFail();
                swipeRefresh.setEnabled(true);
                break;
        }
        if (message == null) {
            ToastUtil.shortToast(getActivity(), getText(R.string.request_error).toString());
        } else {
            ToastUtil.shortToast(getActivity(), message);
        }
    }

    @Override
    public void updateUi(HistoryOrderVo data, int flag) {
        switch (flag) {
            case Constant.REFRESH_NORMAL:
                currentDayData.clear();
                isShowLayer(llAvLoadingTransparent44, false);
                break;
            case Constant.REFRESH_DOWN:
                currentDayData.clear();
                swipeRefresh.setRefreshing(false);
                currentDayAdapter.setEnableLoadMore(true);
                break;
            case Constant.REFRESH_UP_LOADMORE:
                currentDayAdapter.loadMoreFail();
                swipeRefresh.setEnabled(true);
                break;
        }
        currentDayData.addAll(data.getList());
        currentDayAdapter.notifyDataSetChanged();
        List<HistoryOrderVo.SellRecord> historySellSum = data.getHistorySellSum();
        llHeader.removeAllViews();
        for (HistoryOrderVo.SellRecord record: historySellSum){
            TextView tv= (TextView) inflater.inflate(R.layout.item_history_record,null,false);
            tv.setText(TextUtils.concat(record.getCategoryName(),getText(R.string.tip),String.valueOf(record.getGoodsSum())));
            llHeader.addView(tv);
        }
        if (currentDayData.size() < 5) {
            currentDayAdapter.loadMoreEnd(true);
        }
    }

    @Override
    public void notifyNoData() {
        ToastUtil.shortToast(getActivity(), getText(R.string.no_data).toString());
//        currentDayAdapter.loadMoreEnd();
//        swipeRefresh.setEnabled(false);
//        currentDayAdapter.setEnableLoadMore(false);
    }

    @Override
    public void updateOrderSum(HistoryOrderSumBo orderSumBo) {
        ((CompleteActivity) getActivity()).updateOrderSum(Constant.ORDER_MONTH, orderSumBo.getHistoryOrderMonthSum());
    }

    @Override
    public void showOrderSumError() {
        ToastUtil.shortToast(getActivity(), getText(R.string.order_sum_error).toString());
    }

    @Override
    public void netError(int flag) {
        switch (flag) {
            case Constant.REFRESH_NORMAL:
                break;
            case Constant.REFRESH_DOWN:
                currentDayAdapter.setEnableLoadMore(true);
                break;
            case Constant.REFRESH_UP_LOADMORE:
                swipeRefresh.setEnabled(true);
                break;
        }
        ToastUtil.shortToast(getActivity(),getText(R.string.net_error).toString());
    }

    public void refreshPage(){
        mPresenter.requestData(Constant.REFRESH_DOWN);
    }
}
