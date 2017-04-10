package com.eme.waterdelivery.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eme.waterdelivery.App;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.BaseFragment;
import com.eme.waterdelivery.contract.SendingFragContract;
import com.eme.waterdelivery.model.bean.entity.OrderSumBo;
import com.eme.waterdelivery.model.bean.entity.WaitingOrderBo;
import com.eme.waterdelivery.presenter.SendingFragPresenter;
import com.eme.waterdelivery.tools.NetworkUtils;
import com.eme.waterdelivery.tools.ToastUtil;
import com.eme.waterdelivery.ui.HomeActivity;
import com.eme.waterdelivery.ui.SendingDetailActivity;
import com.eme.waterdelivery.ui.adapter.SendingAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dijiaoliang on 17/3/7.
 */
public class SendingFragment extends BaseFragment<SendingFragPresenter> implements SendingFragContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.ll_av_loading_transparent_44)
    LinearLayout llAvLoadingTransparent44;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private List<WaitingOrderBo> sendData;

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
        sendData = new ArrayList<>();
        SendingAdapter sendingAdapter = new SendingAdapter(App.getAppInstance(),sendData);
        sendingAdapter.setOnLoadMoreListener(this,rvContent);
        sendingAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        rvContent.setAdapter(sendingAdapter);
        rvContent.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                if(!NetworkUtils.isConnected(mActivity)){
                    ToastUtil.shortToast(mActivity,getText(R.string.net_error).toString());
                    return;
                }
                WaitingOrderBo bo=sendData.get(position);
                if(bo!=null && bo.getOrderId()!=null){
                    Intent intent=new Intent(mActivity, SendingDetailActivity.class);
                    intent.putExtra(Constant.ORDER_ID,bo.getOrderId());
                    startActivityForResult(intent,Constant.REQUEST_CODE);
                }else {
                    ToastUtil.shortToast(mActivity,getText(R.string.order_info_error).toString());
                }
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
                switch (view.getId()) {
                    case R.id.btn_call:
                        WaitingOrderBo bo=sendData.get(position);
                        if(bo!=null && bo.getMemberPhone()!=null){
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + bo.getMemberPhone()));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else{
                            ToastUtil.shortToast(mActivity,getText(R.string.no_phone).toString());
                        }
                        break;
                    default:
                        break;
                }
            }
        });

        //// TODO: 2017/3/7 RecyclerView添加头布局
//        View v = LayoutInflater.from(mActivity).inflate(R.layout.header_recycler, null);
//        sendingAdapter.addHeaderView(v);
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
    public void onRefresh() {
        ((SendingAdapter)rvContent.getAdapter()).setEnableLoadMore(false);
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
                ((SendingAdapter)rvContent.getAdapter()).setEnableLoadMore(true);
                break;
            case Constant.REFRESH_UP_LOADMORE:
                ((SendingAdapter)rvContent.getAdapter()).loadMoreFail();
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
    public void updateUi(List<WaitingOrderBo> data, int flag) {
        switch (flag) {
            case Constant.REFRESH_NORMAL:
                sendData.clear();
                isShowLayer(llAvLoadingTransparent44, false);
                break;
            case Constant.REFRESH_DOWN:
                sendData.clear();
                swipeRefresh.setRefreshing(false);
                ((SendingAdapter)rvContent.getAdapter()).setEnableLoadMore(true);
                break;
            case Constant.REFRESH_UP_LOADMORE:
                swipeRefresh.setEnabled(true);
                break;
            default:
                break;
        }
        sendData.addAll(data);
        rvContent.getAdapter().notifyDataSetChanged();
        if(Constant.REFRESH_UP_LOADMORE==flag){
            ((SendingAdapter)rvContent.getAdapter()).loadMoreComplete();
        }
    }

    @Override
    public void notifyNoData() {
        ToastUtil.shortToast(mActivity, getText(R.string.no_data).toString());
        swipeRefresh.setEnabled(true);
        ((SendingAdapter)rvContent.getAdapter()).loadMoreEnd();
    }

    @Override
    public void netError(int flag) {
        switch (flag) {
            case Constant.REFRESH_NORMAL:
                break;
            case Constant.REFRESH_DOWN:
                ((SendingAdapter)rvContent.getAdapter()).setEnableLoadMore(true);
                break;
            case Constant.REFRESH_UP_LOADMORE:
                swipeRefresh.setEnabled(true);
                ((SendingAdapter)rvContent.getAdapter()).loadMoreFail();
                break;
            default:
                break;
        }
        ToastUtil.shortToast(mActivity,getText(R.string.net_error).toString());
    }

    @Override
    public void updateOrderSum(OrderSumBo orderSumBo) {
        ((HomeActivity) mActivity).updateOrderSum(Constant.ORDER_SEND, orderSumBo.getDistributingOrderSum());
    }

    @Override
    public void showOrderSumError() {
        ToastUtil.shortToast(mActivity, getText(R.string.order_sum_error).toString());
    }

    public void refreshPage(){
        mPresenter.requestData(Constant.REFRESH_DOWN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(Constant.REQUEST_CODE==requestCode && RESULT_OK==resultCode){
            refreshPage();
            notifyNava();
        }
    }

    public void notifyNava(){
        ((HomeActivity)mActivity).requestCompleteNumber();
    }
}
