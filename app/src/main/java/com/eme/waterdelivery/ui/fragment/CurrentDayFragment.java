package com.eme.waterdelivery.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.BaseFragment;
import com.eme.waterdelivery.contract.CurrentDayContract;
import com.eme.waterdelivery.model.bean.DelayBean;
import com.eme.waterdelivery.presenter.CurrentDayPresenter;
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
public class CurrentDayFragment extends BaseFragment<CurrentDayPresenter> implements CurrentDayContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.ll_av_loading_transparent_44)
    LinearLayout llAvLoadingTransparent44;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private CurrentDayAdapter currentDayAdapter;
    private List<DelayBean> delayData;

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
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setColorSchemeColors(Color.rgb(47, 223, 189));
        rvContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        delayData = new ArrayList<>();
        currentDayAdapter = new CurrentDayAdapter(delayData);
        currentDayAdapter.setOnLoadMoreListener(this);
//        currentDayAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        rvContent.setAdapter(currentDayAdapter);
        rvContent.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
//                Toast.makeText(getActivity(), Integer.toString(position), Toast.LENGTH_LONG).show();
                startActivity(new Intent(getActivity(), CompleteDetailActivity.class));
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
            }
        });

        delayData.add(new DelayBean("123", 1));
        delayData.add(new DelayBean("销量", 1));
        delayData.add(new DelayBean("你哈", 1));
        delayData.add(new DelayBean("亚麻", 1));
        delayData.add(new DelayBean("司法", 1));
        delayData.add(new DelayBean("是个", 1));
        delayData.add(new DelayBean("极为", 1));
        currentDayAdapter.notifyDataSetChanged();

        //// TODO: 2017/3/7 RecyclerView添加头布局
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.header_recycler, null);
        currentDayAdapter.addHeaderView(v);
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(false);
                currentDayAdapter.setEnableLoadMore(true);
            }
        }, 1000);
    }

    int count;

    @Override
    public void onLoadMoreRequested() {
        swipeRefresh.setEnabled(false);
        rvContent.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (count % 3 == 0) {
//                    currentDayAdapter.loadMoreEnd(true);
                    currentDayAdapter.loadMoreComplete();
                } else if (count % 3 == 1) {
                    currentDayAdapter.loadMoreComplete();
                } else {
                    currentDayAdapter.loadMoreFail();
                }
                swipeRefresh.setEnabled(true);
                count++;
            }

        }, 1000);
    }
}