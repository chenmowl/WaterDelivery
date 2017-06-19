package com.eme.waterdelivery.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eme.waterdelivery.App;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.BaseFragment;
import com.eme.waterdelivery.contract.AssessMoneyRecordContract;
import com.eme.waterdelivery.model.bean.entity.AssessMoneyRecordVo;
import com.eme.waterdelivery.presenter.AssessMoneyRecordPresenter;
import com.eme.waterdelivery.tools.ToastUtil;
import com.eme.waterdelivery.ui.adapter.AssessMoneyRecordAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 应缴金额记录
 * Created by dijiaoliang on 17/6/7.
 */
public class AssessMoneyRecordFragment extends BaseFragment<AssessMoneyRecordPresenter> implements AssessMoneyRecordContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.ll_av_loading_transparent_44)
    LinearLayout llAvLoadingTransparent44;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private List<AssessMoneyRecordVo.ResultsBean> listData;

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
        rvContent.setLayoutManager(new LinearLayoutManager(App.getAppInstance()));
        listData = new ArrayList<>();
        AssessMoneyRecordAdapter fixedAdapter = new AssessMoneyRecordAdapter(listData);
        fixedAdapter.setAutoLoadMoreSize(5);
//        fixedAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        rvContent.setAdapter(fixedAdapter);
        fixedAdapter.setOnLoadMoreListener(this,rvContent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroy() {
        if(rvContent!=null){
            rvContent.removeAllViews();
            rvContent=null;
        }
        super.onDestroy();
    }

    @Override
    public void showProgress(boolean b) {
        isShowLayer(llAvLoadingTransparent44,b);
    }

    @Override
    public void requestFailure(int flag) {
        switch (flag) {
            case Constant.REFRESH_NORMAL:
                isShowLayer(llAvLoadingTransparent44, false);
                break;
            case Constant.REFRESH_DOWN:
                swipeRefresh.setRefreshing(false);
                ((AssessMoneyRecordAdapter)rvContent.getAdapter()).setEnableLoadMore(true);
                break;
            case Constant.REFRESH_UP_LOADMORE:
                ((AssessMoneyRecordAdapter)rvContent.getAdapter()).loadMoreFail();
                swipeRefresh.setEnabled(true);
                break;
            default:
                break;
        }
        ToastUtil.shortToast(mActivity, getText(R.string.request_error).toString());
    }

    @Override
    public void updateUi(List<AssessMoneyRecordVo.ResultsBean> data, int flag) {
        switch (flag) {
            case Constant.REFRESH_NORMAL:
                listData.clear();
                isShowLayer(llAvLoadingTransparent44, false);
                break;
            case Constant.REFRESH_DOWN:
                listData.clear();
                swipeRefresh.setRefreshing(false);
                ((AssessMoneyRecordAdapter)rvContent.getAdapter()).setEnableLoadMore(true);
                break;
            case Constant.REFRESH_UP_LOADMORE:
                swipeRefresh.setEnabled(true);
                break;
            default:
                break;
        }
        listData.addAll(data);
        rvContent.getAdapter().notifyDataSetChanged();
        if(listData.size()==0){
            ToastUtil.shortToast(mActivity,R.string.empty_data);
        }
        if(Constant.REFRESH_UP_LOADMORE==flag){
            ((AssessMoneyRecordAdapter)rvContent.getAdapter()).loadMoreComplete();
        }
    }

    @Override
    public void notifyNoData() {
        ToastUtil.shortToast(mActivity, getText(R.string.no_data).toString());
        swipeRefresh.setEnabled(true);
        ((AssessMoneyRecordAdapter)rvContent.getAdapter()).loadMoreEnd();
    }

    @Override
    public void netError(int flag) {
        switch (flag) {
            case Constant.REFRESH_NORMAL:
                break;
            case Constant.REFRESH_DOWN:
                ((AssessMoneyRecordAdapter)rvContent.getAdapter()).setEnableLoadMore(true);
                break;
            case Constant.REFRESH_UP_LOADMORE:
                swipeRefresh.setEnabled(true);
                ((AssessMoneyRecordAdapter)rvContent.getAdapter()).loadMoreFail();
                break;
            default:
                break;
        }
        ToastUtil.shortToast(mActivity,getText(R.string.net_error).toString());
    }

    @Override
    public void onRefresh() {
        ((AssessMoneyRecordAdapter)rvContent.getAdapter()).setEnableLoadMore(false);
        mPresenter.requestData(Constant.REFRESH_DOWN);
    }

    @Override
    public void onLoadMoreRequested() {
        swipeRefresh.setEnabled(false);
        mPresenter.requestData(Constant.REFRESH_UP_LOADMORE);
    }

    public void refreshPage(){
        mPresenter.requestData(Constant.REFRESH_NORMAL);
    }
}
