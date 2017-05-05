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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eme.waterdelivery.App;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.BaseFragment;
import com.eme.waterdelivery.contract.FixedFragContract;
import com.eme.waterdelivery.model.bean.entity.OrderSumBo;
import com.eme.waterdelivery.model.bean.entity.WaitingOrderBo;
import com.eme.waterdelivery.presenter.FixedFragPresenter;
import com.eme.waterdelivery.tools.NetworkUtils;
import com.eme.waterdelivery.tools.ToastUtil;
import com.eme.waterdelivery.ui.FixedDetailActivity;
import com.eme.waterdelivery.ui.HomeActivity;
import com.eme.waterdelivery.ui.adapter.FixedAdapter;
import com.eme.waterdelivery.ui.dialog.SelectDayDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 固定订单模块
 *
 * Created by dijiaoliang on 17/3/7.
 */
public class FixedFragment extends BaseFragment<FixedFragPresenter> implements FixedFragContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private static final String TAG = FixedFragment.class.getSimpleName();

    public static final int FIXED_FRAG_REQUEST_CODE=1001;

    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.ll_av_loading_transparent_44)
    LinearLayout llAvLoadingTransparent44;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

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
        FixedAdapter fixedAdapter = new FixedAdapter(App.getAppInstance(), delayData);
        fixedAdapter.setAutoLoadMoreSize(5);
//        fixedAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        rvContent.setAdapter(fixedAdapter);
        fixedAdapter.setOnLoadMoreListener(this,rvContent);
        rvContent.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                super.onItemChildClick(adapter, view, position);
                switch (view.getId()) {
                    case R.id.btn_reject:
                        final SelectDayDialog sbDialog=new SelectDayDialog(mActivity);
                        sbDialog.setOnBirthChangeListener(new SelectDayDialog.OnBirthChangeListener() {
                            @Override
                            public void changeBirthday(String birthday) {
                                sbDialog.cancel();
                            }
                        });
                        sbDialog.showDialog(mActivity, null);
                        break;
                    case R.id.btn_order_detail:
                        if(!NetworkUtils.isConnected(mActivity)){
                            ToastUtil.shortToast(mActivity,getText(R.string.net_error).toString());
                            return;
                        }
                        WaitingOrderBo bo=delayData.get(position);
                        if(bo!=null && bo.getOrderId()!=null){
//                    Intent intent=new Intent(mActivity, SendingDetailActivity.class);
                            Intent intent=new Intent(mActivity, FixedDetailActivity.class);
                            intent.putExtra(Constant.ORDER_ID,bo.getOrderId());
                            startActivityForResult(intent,Constant.REQUEST_CODE);
                        }else {
                            ToastUtil.shortToast(mActivity,getText(R.string.order_info_error).toString());
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Constant.REQUEST_CODE &&resultCode==RESULT_OK){
            refreshPage();
        }
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
        ((FixedAdapter)rvContent.getAdapter()).setEnableLoadMore(false);
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
                ((FixedAdapter)rvContent.getAdapter()).setEnableLoadMore(true);
                break;
            case Constant.REFRESH_UP_LOADMORE:
                ((FixedAdapter)rvContent.getAdapter()).loadMoreFail();
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
                ((FixedAdapter)rvContent.getAdapter()).setEnableLoadMore(true);
                break;
            case Constant.REFRESH_UP_LOADMORE:
                swipeRefresh.setEnabled(true);
                break;
            default:
                break;
        }
        delayData.addAll(data);
        rvContent.getAdapter().notifyDataSetChanged();
        if(Constant.REFRESH_UP_LOADMORE==flag){
            ((FixedAdapter)rvContent.getAdapter()).loadMoreComplete();
        }
    }

    @Override
    public void notifyNoData() {
        ToastUtil.shortToast(mActivity, getText(R.string.no_data).toString());
        swipeRefresh.setEnabled(true);
        ((FixedAdapter)rvContent.getAdapter()).loadMoreEnd();
    }

    @Override
    public void netError(int flag) {
        switch (flag) {
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
        ToastUtil.shortToast(mActivity,getText(R.string.net_error).toString());
    }

    @Override
    public void updateOrderSum(OrderSumBo orderSumBo) {
        ((HomeActivity) mActivity).updateOrderSum(Constant.ORDER_FIXED, orderSumBo.getFixedOrderSum());
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
