package com.eme.waterdelivery.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eme.waterdelivery.App;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.BaseActivity;
import com.eme.waterdelivery.contract.ApplyDetailContract;
import com.eme.waterdelivery.model.bean.entity.ApplyDetailVo;
import com.eme.waterdelivery.model.bean.entity.PurchaseGoodBo;
import com.eme.waterdelivery.presenter.ApplyDetailPresenter;
import com.eme.waterdelivery.tools.NetworkUtils;
import com.eme.waterdelivery.tools.TimeUtils;
import com.eme.waterdelivery.tools.ToastUtil;
import com.eme.waterdelivery.ui.adapter.ApplyDetailMenuAdapter;
import com.jakewharton.rxbinding2.view.RxView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by dijiaoliang on 17/3/13.
 */

public class ApplyDetailActivity extends BaseActivity<ApplyDetailPresenter> implements ApplyDetailContract.View {


    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @BindView(R.id.rv_apply_record)
    RecyclerView rvApplyRecord;
    @BindView(R.id.ll_av_loading_transparent_44)
    LinearLayout llAvLoadingTransparent44;
    @BindView(R.id.tv_handle_station)
    TextView tvHandleStation;
    @BindView(R.id.tv_station_phone)
    TextView tvStationPhone;

    private ApplyDetailMenuAdapter adapter;
    private List<PurchaseGoodBo> data;

    private View headerView;
    private View footerView;

    @Override
    protected void initInject() {
        getViewComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_apply_detail;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        data = new ArrayList<>();
        adapter = new ApplyDetailMenuAdapter(data);
//        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvApplyRecord.setLayoutManager(manager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvApplyRecord.setHasFixedSize(true);
//        rvApplyRecord.addItemDecoration(new SpacesItemDecoration(10));
        rvApplyRecord.setAdapter(adapter);
        headerView = LayoutInflater.from(this).inflate(R.layout.header_apply_detail, null, false);
        footerView = LayoutInflater.from(this).inflate(R.layout.footer_apply_detail, null, false);
        adapter.addHeaderView(headerView);
        adapter.addFooterView(footerView);

        rvApplyRecord.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
            }
        });

        initListener();

        String orderId = getIntent().getStringExtra(Constant.TRAFFIC_NO);
        tvTitle.setText(TextUtils.concat(getText(R.string.apply_order_number), orderId));
        if (!NetworkUtils.isConnected(App.getAppInstance())) {
            showNetError();
        } else {
            mPresenter.requestData(orderId);//请求订单详情数据
        }
    }

    private void initListener() {
        RxView.clicks(back)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mPresenter.unSubscribe();
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
    public void showProgress(boolean b) {
        isShowLayer(llAvLoadingTransparent44, b);
    }

    @Override
    public void updateUi(ApplyDetailVo applyDetailVo) {
        SimpleDateFormat format=new SimpleDateFormat("MM月dd日 HH:mm");
        //更新进度条状态
        switch (applyDetailVo.getStatus()) {
            case Constant.APPLY_RECORD_STATUS_UNHANDLE:
                headerView.findViewById(R.id.iv_line_two).setBackgroundResource(R.color.cline_low);
                headerView.findViewById(R.id.iv_line_three).setBackgroundResource(R.color.cline_low);
                ((TextView) headerView.findViewById(R.id.tv_order_detail_status_two_time)).setText(TimeUtils.milliseconds2String(TimeUtils.string2Milliseconds(applyDetailVo.getCreateTime()),format));
                break;
            case Constant.APPLY_RECORD_STATUS_DELIVERY:
                headerView.findViewById(R.id.iv_line_two).setBackgroundResource(R.color.progress_blue);
                headerView.findViewById(R.id.iv_line_three).setBackgroundResource(R.color.cline_low);
                ((ImageView)headerView.findViewById(R.id.iv_order_detail_point_three)).setImageResource(R.mipmap.order_dian_blue);
                ((TextView) headerView.findViewById(R.id.tv_order_detail_status_two_time)).setText(TimeUtils.milliseconds2String(TimeUtils.string2Milliseconds(applyDetailVo.getCreateTime()),format));
                ((TextView) headerView.findViewById(R.id.tv_order_detail_status_three_time)).setText(TimeUtils.milliseconds2String(TimeUtils.string2Milliseconds(applyDetailVo.getSendTime()),format));
                break;
            case Constant.APPLY_RECORD_STATUS_COMPLETE:
                headerView.findViewById(R.id.iv_line_two).setBackgroundResource(R.color.progress_blue);
                headerView.findViewById(R.id.iv_line_three).setBackgroundResource(R.color.progress_blue);
                ((ImageView)headerView.findViewById(R.id.iv_order_detail_point_three)).setImageResource(R.mipmap.order_dian_blue);
                ((ImageView)headerView.findViewById(R.id.iv_order_detail_point_four)).setImageResource(R.mipmap.order_dian_blue);
                ((TextView) headerView.findViewById(R.id.tv_order_detail_status_two_time)).setText(TimeUtils.milliseconds2String(TimeUtils.string2Milliseconds(applyDetailVo.getCreateTime()),format));
                ((TextView) headerView.findViewById(R.id.tv_order_detail_status_three_time)).setText(TimeUtils.milliseconds2String(TimeUtils.string2Milliseconds(applyDetailVo.getSendTime()),format));
                ((TextView) headerView.findViewById(R.id.tv_order_detail_status_four_time)).setText(TimeUtils.milliseconds2String(TimeUtils.string2Milliseconds(applyDetailVo.getConfirmTime()),format));
                break;
        }
        data.clear();
        adapter.setStatus(applyDetailVo.getStatus());
        data.addAll(applyDetailVo.getPurchaseGoods());
        adapter.notifyDataSetChanged();
        //更新底部信息
        ((TextView) footerView.findViewById(R.id.tv_remark)).setText(TextUtils.concat(getText(R.string.apply_detail_remark_title), applyDetailVo.getCreateMemo()));
        tvHandleStation.setText(applyDetailVo.getStationName());
        tvStationPhone.setText(applyDetailVo.getStationPhone());
    }

    @Override
    public void showRequestMsg(String msg) {
        if (TextUtils.isEmpty(msg)) {
            ToastUtil.shortToast(this, getText(R.string.request_error).toString());
        } else {
            ToastUtil.shortToast(this, msg);
        }
    }
}
