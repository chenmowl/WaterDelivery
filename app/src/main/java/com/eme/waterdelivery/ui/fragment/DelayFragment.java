package com.eme.waterdelivery.ui.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.eme.waterdelivery.contract.DelayFragContract;
import com.eme.waterdelivery.model.bean.entity.OrderSumBo;
import com.eme.waterdelivery.model.bean.entity.WaitingOrderBo;
import com.eme.waterdelivery.presenter.DelayFragPresenter;
import com.eme.waterdelivery.tools.ToastUtil;
import com.eme.waterdelivery.ui.HomeActivity;
import com.eme.waterdelivery.ui.adapter.DelayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dijiaoliang on 17/3/7.
 */
public class DelayFragment extends BaseFragment<DelayFragPresenter> implements DelayFragContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private static final String TAG = DelayFragment.class.getSimpleName();

    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.ll_av_loading_transparent_44)
    LinearLayout llAvLoadingTransparent44;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private DelayAdapter delayAdapter;
    private List<WaitingOrderBo> delayData;

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
        delayData = new ArrayList<>();
        delayAdapter = new DelayAdapter(App.getAppInstance(), delayData);
        delayAdapter.setAutoLoadMoreSize(5);
//        delayAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        rvContent.setAdapter(delayAdapter);
        delayAdapter.setOnLoadMoreListener(this,rvContent);
        rvContent.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                super.onItemChildClick(adapter, view, position);
                switch (view.getId()) {
                    case R.id.btn_receiving:
                        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                        builder.setMessage("是否接单？\n一旦接单将无法取消");
                        builder.setNegativeButton("取消", null);
                        builder.setPositiveButton("接单", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String orderId = delayData.get(position).getOrderId();
                                mPresenter.receiveOrder(orderId);
                            }
                        });
                        builder.show();
                        break;
                    default:
                        break;
                }
            }
        });
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
                delayAdapter.setEnableLoadMore(true);
                break;
            case Constant.REFRESH_UP_LOADMORE:
                delayAdapter.loadMoreFail();
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
                delayData.clear();
                isShowLayer(llAvLoadingTransparent44, false);
                break;
            case Constant.REFRESH_DOWN:
                delayData.clear();
                swipeRefresh.setRefreshing(false);
                delayAdapter.setEnableLoadMore(true);
                break;
            case Constant.REFRESH_UP_LOADMORE:
                swipeRefresh.setEnabled(true);
                break;
            default:
                break;
        }
        delayData.addAll(data);
        delayAdapter.notifyDataSetChanged();
        if(Constant.REFRESH_UP_LOADMORE==flag){
            delayAdapter.loadMoreComplete();
        }
    }

    @Override
    public void notifyNoData() {
        ToastUtil.shortToast(mActivity, getText(R.string.no_data).toString());
        swipeRefresh.setEnabled(true);
        delayAdapter.loadMoreEnd();
    }

    @Override
    public void netError(int flag) {
        switch (flag) {
            case Constant.REFRESH_NORMAL:
                break;
            case Constant.REFRESH_DOWN:
                delayAdapter.setEnableLoadMore(true);
                break;
            case Constant.REFRESH_UP_LOADMORE:
                swipeRefresh.setEnabled(true);
                delayAdapter.loadMoreFail();
                break;
            default:
                break;
        }
        ToastUtil.shortToast(mActivity,getText(R.string.net_error).toString());
    }

    @Override
    public void updateOrderSum(OrderSumBo orderSumBo) {
        ((HomeActivity) mActivity).updateOrderSum(Constant.ORDER_DELAY, orderSumBo.getWaitingOrderSum());
    }

    @Override
    public void showOrderSumError() {
        ToastUtil.shortToast(mActivity, getText(R.string.order_sum_error).toString());
    }

    @Override
    public void showReceiveOrderStatus(String message) {
        if (TextUtils.isEmpty(message)) {
            message = getText(R.string.receive_order_success).toString();
        }
        ToastUtil.shortToast(mActivity, message);
    }

    public void refreshPage(){
        mPresenter.requestData(Constant.REFRESH_DOWN);
    }
}
