package com.eme.waterdelivery.ui.fragment;

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

import com.eme.waterdelivery.App;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.BaseFragment;
import com.eme.waterdelivery.contract.AssessMoneyContract;
import com.eme.waterdelivery.model.bean.entity.AssessMoneyVo;
import com.eme.waterdelivery.presenter.AssessMoneyPresenter;
import com.eme.waterdelivery.tools.NetworkUtils;
import com.eme.waterdelivery.tools.ToastUtil;
import com.eme.waterdelivery.ui.adapter.AssessMoneyAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 应缴金额模块
 * Created by dijiaoliang on 17/6/6.
 */
public class AssessMoneyFragment extends BaseFragment<AssessMoneyPresenter> implements AssessMoneyContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.ll_av_loading_transparent_44)
    LinearLayout llAvLoadingTransparent44;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private LinearLayout llHeader;
    private LayoutInflater inflater;

    private List<AssessMoneyVo.DayStatementsBean> listData;

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
        inflater = LayoutInflater.from(App.getAppInstance());
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setColorSchemeColors(Color.rgb(47, 223, 189));
        rvContent.setLayoutManager(new LinearLayoutManager(App.getAppInstance()));
        listData = new ArrayList<>();
        AssessMoneyAdapter assessMoneyAdapter = new AssessMoneyAdapter(listData);
//        currentDayAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        rvContent.setAdapter(assessMoneyAdapter);

        // TODO: 2017/3/7 RecyclerView添加头布局
        llHeader = (LinearLayout) LayoutInflater.from(mActivity).inflate(R.layout.header_recycler, null);
        assessMoneyAdapter.addHeaderView(llHeader);
        mPresenter.requestData(Constant.REFRESH_NORMAL);
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
        llHeader=null;
        if(rvContent!=null){
            rvContent.removeAllViews();
            rvContent=null;
        }
        super.onDestroy();
    }

    @Override
    public void showProgress(boolean b) {
        isShowLayer(llAvLoadingTransparent44, b);
    }

    @Override
    public void showRequestResult(boolean isSuccess, AssessMoneyVo assessMoneyVo, int flag) {
        if(isSuccess){
            llHeader.removeAllViews();
            TextView tv= (TextView) inflater.inflate(R.layout.item_history_record,null,false);
            tv.setText(TextUtils.concat(getText(R.string.assessment_total_amount), TextUtils.isEmpty(assessMoneyVo.getTotalAmount()) ? Constant.STR_EMPTY : TextUtils.concat(getText(R.string.money_unit), assessMoneyVo.getTotalAmount())));
            llHeader.addView(tv);
            List<AssessMoneyVo.DayStatementsBean> dayStatements = assessMoneyVo.getDayStatements();
            if(dayStatements!=null && dayStatements.size()!=0){
                listData.clear();
                listData.addAll(dayStatements);
                rvContent.getAdapter().notifyDataSetChanged();
            }
        }else{
            ToastUtil.shortToast(mActivity,R.string.request_error);
        }
        switch (flag){
            case Constant.REFRESH_DOWN:
                if(swipeRefresh.isRefreshing()){
                    swipeRefresh.setRefreshing(false);
                }
                break;
            case Constant.REFRESH_NORMAL:
                showProgress(false);
                break;
        }
    }

    @Override
    public void onRefresh() {
        if(NetworkUtils.isConnected(mActivity)){
            mPresenter.requestData(Constant.REFRESH_DOWN);
        }else{
            showNetError();
            if(swipeRefresh.isRefreshing()){
                swipeRefresh.setRefreshing(false);
            }
        }
    }

    public void refreshPage(){
        if(NetworkUtils.isConnected(mActivity)){
            mPresenter.requestData(Constant.REFRESH_NORMAL);
        }else{
            showNetError();
            if(swipeRefresh.isRefreshing()){
                swipeRefresh.setRefreshing(false);
            }
        }
    }
}
