package com.eme.waterdelivery.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.BaseFragment;
import com.eme.waterdelivery.contract.DelayContract;
import com.eme.waterdelivery.model.bean.DelayBean;
import com.eme.waterdelivery.presenter.DelayPresenter;
import com.eme.waterdelivery.ui.adapter.DelayAdapter;
import com.eme.waterdelivery.widget.FullyLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dijiaoliang on 17/3/7.
 */
public class DelayFragment extends BaseFragment<DelayPresenter> implements DelayContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.ll_av_loading_transparent_44)
    LinearLayout llAvLoadingTransparent44;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private DelayAdapter delayAdapter;
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
        rvContent.setLayoutManager(new FullyLinearLayoutManager(getActivity()));
        delayData=new ArrayList<>();
        delayAdapter = new DelayAdapter(delayData);
        delayAdapter.setOnLoadMoreListener(this);
        delayAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        rvContent.setAdapter(delayAdapter);
        rvContent.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                Toast.makeText(getActivity(), Integer.toString(position), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
                switch (view.getId()) {
                    case R.id.btn_receiving:
                        Toast.makeText(getActivity(), "订单已接收", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });

        delayData.add(new DelayBean("123",1));
        delayData.add(new DelayBean("销量",1));
        delayData.add(new DelayBean("你哈",1));
        delayData.add(new DelayBean("亚麻",1));
        delayData.add(new DelayBean("司法",1));
        delayData.add(new DelayBean("是个",1));
        delayData.add(new DelayBean("极为",1));
        delayAdapter.notifyDataSetChanged();
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
        delayAdapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(false);
                delayAdapter.setEnableLoadMore(true);
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
//                    delayAdapter.loadMoreEnd(true);
                    delayAdapter.loadMoreComplete();
                } else if (count % 3 == 1) {
                    delayAdapter.loadMoreComplete();
                } else {
                    delayAdapter.loadMoreFail();
                }
                swipeRefresh.setEnabled(true);
                count++;
            }

        }, 1000);
    }
}
