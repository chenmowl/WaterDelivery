package com.eme.waterdelivery.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.BaseFragment;
import com.eme.waterdelivery.contract.ApplyContract;
import com.eme.waterdelivery.presenter.ApplyPresenter;
import com.eme.waterdelivery.tools.KeyboardUtils;
import com.eme.waterdelivery.ui.adapter.ApplyMenuAdapter;
import com.eme.waterdelivery.widget.decoration.SpacesItemDecoration;
import com.jakewharton.rxbinding.view.RxView;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * 申请采购 Fragment
 *
 * Created by dijiaoliang on 17/3/9.
 */
public class ApplyFragment extends BaseFragment<ApplyPresenter> implements ApplyContract.View, View.OnClickListener {

    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.rv_apply)
    RecyclerView rvApply;
    @BindView(R.id.rl_add)
    RelativeLayout rlAdd;

    private ApplyMenuAdapter adapter;
    private List<String> data;

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
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        adapter = new ApplyMenuAdapter(data);
//        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvApply.setLayoutManager(manager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvApply.setHasFixedSize(true);
        rvApply.addItemDecoration(new SpacesItemDecoration(4));
        rvApply.setAdapter(adapter);
        headerView = LayoutInflater.from(getActivity()).inflate(R.layout.header_recycler_apply, null, false);
        footerView = LayoutInflater.from(getActivity()).inflate(R.layout.footer_recycler_apply, null, false);
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
                        adapter.remove(position);
                        if (data.size() == 0) {
                            rlAdd.setVisibility(View.VISIBLE);
                        }
                        break;
                }
            }
        });

        initListener();

    }

    private void initListener() {
        RxView.clicks(headerView.findViewById(R.id.tv_add))
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        alertApplyDialog();
                    }
                });
        RxView.clicks(tvAdd)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        alertApplyDialog();
                    }
                });
    }


    android.support.v7.app.AlertDialog dialog;

    /**
     * 弹出采购项的dialog
     */
    private void alertApplyDialog() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_apply, null, false);
        BetterSpinner typeSpinner = (BetterSpinner) view.findViewById(R.id.ns_type);
        BetterSpinner nameSpinner = (BetterSpinner) view.findViewById(R.id.ns_name);
        BetterSpinner sizeSpinner = (BetterSpinner) view.findViewById(R.id.ns_size);
        ArrayAdapter<String> adapterType = new ArrayAdapter(getActivity(), R.layout.item_spinner_apply, getResources().getStringArray(R.array.product_types));
        ArrayAdapter<String> adapterName = new ArrayAdapter(getActivity(), R.layout.item_spinner_apply, getResources().getStringArray(R.array.product_names));
        ArrayAdapter<String> adapterSize = new ArrayAdapter(getActivity(), R.layout.item_spinner_apply, getResources().getStringArray(R.array.product_sizes));
        typeSpinner.setAdapter(adapterType);
        nameSpinner.setAdapter(adapterName);
        sizeSpinner.setAdapter(adapterSize);

        view.findViewById(R.id.btn_cancel).setOnClickListener(this);
        view.findViewById(R.id.btn_confirm).setOnClickListener(this);
        builder.setView(view);
        dialog=builder.show();
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
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_cancel:
                dialog.dismiss();
                dialog=null;
                break;
            case R.id.btn_confirm:
                //todo 把采购项添加进列表
                KeyboardUtils.forceCloseSoftInput(getActivity(),dialog);
                dialog.dismiss();
                dialog=null;
                data.add("2");
                adapter.notifyDataSetChanged();
                break;
        }
    }


}
