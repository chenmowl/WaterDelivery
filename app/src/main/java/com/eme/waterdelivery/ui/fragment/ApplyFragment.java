package com.eme.waterdelivery.ui.fragment;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eme.waterdelivery.App;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.BaseFragment;
import com.eme.waterdelivery.contract.ApplyContract;
import com.eme.waterdelivery.model.bean.SubmitGood;
import com.eme.waterdelivery.model.bean.entity.ApplyOneLevelBo;
import com.eme.waterdelivery.model.bean.entity.ApplyTwoLevelGoodBo;
import com.eme.waterdelivery.model.sp.SPBase;
import com.eme.waterdelivery.model.sp.SpConstant;
import com.eme.waterdelivery.presenter.ApplyPresenter;
import com.eme.waterdelivery.tools.KeyboardUtils;
import com.eme.waterdelivery.tools.NetworkUtils;
import com.eme.waterdelivery.tools.ToastUtil;
import com.eme.waterdelivery.tools.ZeroTextWatcher;
import com.eme.waterdelivery.ui.adapter.ApplyMenuAdapter;
import com.eme.waterdelivery.widget.decoration.SpacesItemDecoration;
import com.jakewharton.rxbinding2.view.RxView;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * 申请采购 Fragment
 * <p>
 * Created by dijiaoliang on 17/3/9.
 */
public class ApplyFragment extends BaseFragment<ApplyPresenter> implements ApplyContract.View, View.OnClickListener {

    private static final String TAG = ApplyFragment.class.getSimpleName();

    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.rv_apply)
    RecyclerView rvApply;
    @BindView(R.id.rl_add)
    RelativeLayout rlAdd;
    @BindView(R.id.ll_av_loading_transparent_44)
    LinearLayout llAvLoadingTransparent44;

    AppCompatEditText etRemark;

    private ApplyMenuAdapter adapter;
    private List<ApplyTwoLevelGoodBo.ListBean> data;

    private View headerView;
    private View footerView;

    @Override
    protected void initInject() {
        getViewComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_apply;
    }

    @Override
    protected void initEventAndData() {
        data = new ArrayList<>();
        adapter = new ApplyMenuAdapter(data);
//        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        LinearLayoutManager manager = new LinearLayoutManager(App.getAppInstance());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvApply.setLayoutManager(manager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvApply.setHasFixedSize(true);
        rvApply.addItemDecoration(new SpacesItemDecoration(4));
        rvApply.setAdapter(adapter);
        headerView = LayoutInflater.from(mActivity).inflate(R.layout.header_recycler_apply, null, false);
        footerView = LayoutInflater.from(mActivity).inflate(R.layout.footer_recycler_apply, null, false);
        etRemark = (AppCompatEditText) footerView.findViewById(R.id.et_remark);
        adapter.addHeaderView(headerView);
        adapter.addFooterView(footerView);

        rvApply.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
                switch (view.getId()) {
                    case R.id.rl_delete:
                        data.remove(position);
                        adapter.notifyDataSetChanged();
                        checkHasApplyItem();
                        break;
                    default:
                        break;
                }
            }
        });
        initListener();
        checkHasApplyItem();
    }

    private void initListener() {
        RxView.clicks(headerView.findViewById(R.id.tv_add))
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if (!NetworkUtils.isConnected(App.getAppInstance())) {
                            showNetError();
                            return;
                        }
                        mPresenter.requestApplyOneLevel();
                    }
                });
        RxView.clicks(tvAdd)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if (!NetworkUtils.isConnected(App.getAppInstance())) {
                            showNetError();
                            return;
                        }
                        mPresenter.requestApplyOneLevel();
                    }
                });
        RxView.clicks(footerView.findViewById(R.id.btn_call))
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if (!NetworkUtils.isConnected(App.getAppInstance())) {
                            showNetError();
                            return;
                        }
                        submitApplications();
                    }
                });
    }

    /**
     * 提交订购单
     */
    private void submitApplications() {
        String remark = etRemark.getText().toString();
        List<SubmitGood> purchaseGoods = new ArrayList<>();
        for (ApplyTwoLevelGoodBo.ListBean listBean : data) {
            SubmitGood submitGood = new SubmitGood();
            submitGood.setGoodsId(listBean.getGoodsId());
            submitGood.setPreChangeAmount(listBean.getCount());
            purchaseGoods.add(submitGood);
        }
        mPresenter.submitApplications(SPBase.getContent(App.getAppInstance(), SpConstant.USER_FILE_NAME, SpConstant.USER_STORE_ID), TextUtils.isEmpty(remark) ? Constant.STR_EMPTY : remark, JSON.toJSONString(purchaseGoods));
    }

    AlertDialog dialog;
    View dialogView;
    AppCompatEditText etAmount;
    BetterSpinner typeSpinner;
    BetterSpinner sizeSpinner;
    BetterSpinner goodSpinner;
    List<String> tempData;
    List<String> tempSize;
    List<String> tempGood;
    List<ApplyTwoLevelGoodBo.ListBean> goodListBeen;
    ArrayAdapter<String> adapterType;
    ArrayAdapter<String> adapterSize;
    ArrayAdapter<String> adapterGood;
    int goodPosition = -1;

    /**
     * 弹出采购项的dialog
     */
    private void alertApplyDialog(final List<ApplyOneLevelBo.ListBean> listBeen) {
        goodPosition = -1;
        if(goodListBeen==null){
            goodListBeen=new ArrayList<>();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        dialogView = LayoutInflater.from(mActivity).inflate(R.layout.dialog_apply, null, false);
        etAmount = (AppCompatEditText) dialogView.findViewById(R.id.et_amount);
        etAmount.addTextChangedListener(new ZeroTextWatcher());
        typeSpinner = (BetterSpinner) dialogView.findViewById(R.id.ns_type);
        sizeSpinner = (BetterSpinner) dialogView.findViewById(R.id.ns_size);
        goodSpinner = (BetterSpinner) dialogView.findViewById(R.id.ns_good);
        if(tempData!=null){
            tempData.clear();
        }else{
            tempData = new ArrayList<>();
        }
        if(tempSize!=null){
            tempSize.clear();
        }else{
            tempSize = new ArrayList<>();
        }
        if(tempGood!=null){
            tempGood.clear();
        }else{
            tempGood = new ArrayList<>();
        }
        for (ApplyOneLevelBo.ListBean bean : listBeen) {
            tempData.add(bean.getName());
        }
        adapterType = new ArrayAdapter(mActivity, R.layout.item_spinner_apply, tempData);
        adapterSize = new ArrayAdapter(mActivity, R.layout.item_spinner_apply, tempSize);
        adapterGood = new ArrayAdapter(mActivity, R.layout.item_spinner_apply, tempGood);
        typeSpinner.setAdapter(adapterType);
        sizeSpinner.setAdapter(adapterSize);
        goodSpinner.setAdapter(adapterGood);
        typeSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View viw, int i, long l) {
                if (!NetworkUtils.isConnected(App.getAppInstance())) {
                    showNetError();
                    return;
                }
                goodListBeen.clear();
                goodPosition = -1;
                sizeSpinner.setText(Constant.STR_EMPTY);
                goodSpinner.setText(Constant.STR_EMPTY);
                sizeSpinner.setOnItemClickListener(null);
                goodSpinner.setOnItemClickListener(null);
                tempSize.clear();
                tempGood.clear();
                adapterSize = new ArrayAdapter(mActivity, R.layout.item_spinner_apply, tempSize);
                adapterGood = new ArrayAdapter(mActivity, R.layout.item_spinner_apply, tempGood);
                sizeSpinner.setAdapter(adapterSize);
                goodSpinner.setAdapter(adapterGood);
                mPresenter.requestApplyTwoLevel(listBeen.get(i).getId());
            }
        });
        sizeSpinner.setOnItemClickListener(null);
        goodSpinner.setOnItemClickListener(null);

        dialogView.findViewById(R.id.btn_cancel).setOnClickListener(this);
        dialogView.findViewById(R.id.btn_confirm).setOnClickListener(this);
        builder.setView(dialogView);
        dialog = builder.show();
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
        tempData=null;
        tempSize=null;
        tempGood=null;
        goodListBeen=null;
        adapterType=null;
        adapterSize=null;
        adapterGood=null;
        etAmount=null;
        dialogView=null;
        super.onDestroy();
    }

    public void checkHasApplyItem() {
        if (data.size() == 0) {
            rlAdd.setVisibility(View.VISIBLE);
        } else {
            rlAdd.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                goodPosition = -1;
                dialog.dismiss();
                dialog = null;
                break;
            case R.id.btn_confirm:
                //todo 把采购项添加进列表
                KeyboardUtils.forceCloseSoftInput(App.getAppInstance(), dialog);
                dialog.dismiss();
                dialog = null;
                if (goodPosition != -1) {
                    String count = etAmount.getText().toString();
                    if (!TextUtils.isEmpty(count)) {
                        boolean hasRepeat=false;//标识符，代表列表是否有相同的商品
                        ApplyTwoLevelGoodBo.ListBean listBean = goodListBeen.get(goodPosition);
                        for(ApplyTwoLevelGoodBo.ListBean bean: data){
                            if(bean.getGoodsId().equals(listBean.getGoodsId())){
                                bean.setCount(bean.getCount()+Integer.parseInt(count));
                                hasRepeat=true;
                            }
                        }
                        if(!hasRepeat){
                            listBean.setCount(Integer.parseInt(count));
                            data.add(listBean);
                        }
                        adapter.notifyDataSetChanged();
                        ToastUtil.shortToast(mActivity, getText(R.string.apply_add_success).toString());
                    } else {
                        ToastUtil.shortToast(mActivity, getText(R.string.apply_no_input_sum).toString());
                    }
                } else {
                    ToastUtil.shortToast(mActivity, getText(R.string.apply_choice_good).toString());
                }
                checkHasApplyItem();
                break;
            default:
                break;
        }
    }


    @Override
    public void updateOneLevel(List<ApplyOneLevelBo.ListBean> listBeen) {
        alertApplyDialog(listBeen);
    }

    @Override
    public void updateTwoLevel(final List<ApplyOneLevelBo.ListBean> listBeen) {
        tempSize.clear();
        for (ApplyOneLevelBo.ListBean bean : listBeen) {
            tempSize.add(bean.getName());
        }
        adapterSize.notifyDataSetChanged();
        sizeSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!NetworkUtils.isConnected(App.getAppInstance())) {
                    showNetError();
                    return;
                }
                mPresenter.requestApplyTwoLevelGoods(listBeen.get(i).getId());
            }
        });
    }

    @Override
    public void updateTwoLevelGoods(List<ApplyTwoLevelGoodBo.ListBean> listBeen) {
//        this.goodListBeen = listBeen;
        if(goodListBeen==null){
            goodListBeen=new ArrayList<>();
        }else{
            goodListBeen.clear();
        }
        goodListBeen.addAll(listBeen);
        tempGood.clear();
        for (ApplyTwoLevelGoodBo.ListBean bean : listBeen) {
            tempGood.add(bean.getGoodsName());
        }
        adapterGood.notifyDataSetChanged();
        goodSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                goodPosition = i;
            }
        });
    }

    @Override
    public void requestError() {
        ToastUtil.shortToast(mActivity, getText(R.string.request_error).toString());
    }

    @Override
    public void showProgress(boolean b) {
        isShowLayer(llAvLoadingTransparent44,b);
    }

    @Override
    public void showSubmitResult(boolean isSuccess, String msg) {
        if(isSuccess){
            //提交成功的话清空当前界面数据，然后toast
            data.clear();
            adapter.notifyDataSetChanged();
            checkHasApplyItem();
            ToastUtil.shortToast(mActivity,msg);
        }else{
            if(TextUtils.isEmpty(msg)){
                ToastUtil.shortToast(mActivity,getText(R.string.submit_application_failure).toString());
            }else{
                ToastUtil.shortToast(mActivity,msg);
            }
        }
        isShowLayer(llAvLoadingTransparent44,false);
    }
}
