package com.eme.waterdelivery.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eme.waterdelivery.App;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.BaseActivity;
import com.eme.waterdelivery.contract.CollectWaterContract;
import com.eme.waterdelivery.model.bean.entity.TrafficDetailVo;
import com.eme.waterdelivery.presenter.CollectWaterPresenter;
import com.eme.waterdelivery.tools.ImageLoader;
import com.eme.waterdelivery.tools.ToastUtil;
import com.eme.waterdelivery.ui.adapter.WBaseRecycleAdapter;
import com.eme.waterdelivery.ui.adapter.WBaseRecycleViewHolder;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * 收水确认
 * <p>
 * Created by dijiaoliang on 17/4/24.
 */
public class CollectWaterActivity extends BaseActivity<CollectWaterPresenter> implements CollectWaterContract.View {


    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_goods)
    RecyclerView rvGoods;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.ll_av_loading_transparent_44)
    LinearLayout llAvLoadingTransparent44;

    private List<TrafficDetailVo.GoodsBean> mGoodsData;
    private String traffic_no;

    @Override
    protected void initInject() {
        getViewComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_collect_water;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        tvTitle.setText(getText(R.string.collect_water));
//        如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvGoods.setHasFixedSize(true);
        rvGoods.setNestedScrollingEnabled(false);
        LinearLayoutManager manager = new LinearLayoutManager(App.getAppInstance());
        manager.setAutoMeasureEnabled(true);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvGoods.setLayoutManager(manager);
        mGoodsData = new ArrayList<>();
        WBaseRecycleAdapter<TrafficDetailVo.GoodsBean> mTicketAdapter = new WBaseRecycleAdapter<TrafficDetailVo.GoodsBean>(this, mGoodsData, R.layout.item_collect_water_goods) {
            @Override
            public void onBindViewHolder(WBaseRecycleViewHolder holder, int position, TrafficDetailVo.GoodsBean s) {
                ImageLoader.load(CollectWaterActivity.this, TextUtils.isEmpty(s.getGoodsImage())? Constant.STR_EMPTY:s.getGoodsImage(), (ImageView) holder.getView(R.id.sdv_good));
                holder.setText(R.id.tv_good_name, TextUtils.isEmpty(s.getGoodsName())? Constant.STR_EMPTY:s.getGoodsName());
                holder.setText(R.id.tv_good_size, "规格: " + (TextUtils.isEmpty(s.getSpecName())? Constant.STR_EMPTY:s.getSpecName()));
                holder.setText(R.id.tv_good_count, "x" + s.getGoodsCount());
            }
        };
        rvGoods.setAdapter(mTicketAdapter);
        initListener();
        btnConfirm.setVisibility(View.GONE);
        traffic_no = getIntent().getStringExtra(Constant.TRAFFIC_NO);
        if (!TextUtils.isEmpty(traffic_no)) {
            mPresenter.requestTrafficDetail(traffic_no);
        } else {
            ToastUtil.shortToast(this, getText(R.string.traffic_exception).toString());
        }

    }

    private void initListener() {
        RxView.clicks(back)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {

                    @Override
                    public void accept(Object o) throws Exception {
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
    public void showProgress(boolean isShow) {
        isShowLayer(llAvLoadingTransparent44, isShow);
    }

    @Override
    public void showRequestResult(boolean isSuccess, List<TrafficDetailVo.GoodsBean> data) {
        if (isSuccess) {
            if (data != null && data.size() != 0) {
                mGoodsData.clear();
                mGoodsData.addAll(data);
                rvGoods.getAdapter().notifyDataSetChanged();
                btnConfirm.setVisibility(View.VISIBLE);
                RxView.clicks(btnConfirm)
                        .throttleFirst(1, TimeUnit.SECONDS)
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Object>() {

                            @Override
                            public void accept(Object o) throws Exception {
                                alert();
                            }
                        });
            } else {
                ToastUtil.shortToast(this, getText(R.string.empty_traffic_detail).toString());
            }
        } else {
            ToastUtil.shortToast(this, getText(R.string.get_traffic_detail_failure).toString());
        }
    }

    @Override
    public void showConfirmResult(boolean isSuccess) {
        if(isSuccess){
            setResult(RESULT_OK);
            ToastUtil.shortToast(this,getText(R.string.traffic_confirm_success).toString());
            finish();
        }else{
            ToastUtil.shortToast(this,getText(R.string.traffic_confirm_failure).toString());
        }
    }

    public void alert(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage(getText(R.string.is_confirm_collect_water));
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.handleTraffic(traffic_no);
                if(dialog!=null){
                    dialog.dismiss();
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dialog!=null){
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
}
