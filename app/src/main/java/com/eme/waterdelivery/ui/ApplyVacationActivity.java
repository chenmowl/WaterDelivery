package com.eme.waterdelivery.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.AppCompatEditText;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.BaseActivity;
import com.eme.waterdelivery.contract.ApplyVacationContract;
import com.eme.waterdelivery.presenter.ApplyVacationPresenter;
import com.eme.waterdelivery.ui.dialog.SelectVacationDayDialog;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by dijiaoliang on 17/4/24.
 */
public class ApplyVacationActivity extends BaseActivity<ApplyVacationPresenter> implements ApplyVacationContract.View, RadioGroup.OnCheckedChangeListener {


    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_start_time)
    TextView btnStartTime;
    @BindView(R.id.btn_end_time)
    TextView btnEndTime;
    @BindView(R.id.rb_sick_leave)
    RadioButton rbSickLeave;
    @BindView(R.id.rb_casual_leave)
    RadioButton rbCasualLeave;
    @BindView(R.id.rb_other_leave)
    RadioButton rbOtherLeave;
    @BindView(R.id.rg_apply_vacation)
    RadioGroup rgApplyVacation;
    @BindView(R.id.et_remark)
    AppCompatEditText etRemark;
    @BindView(R.id.btn_sign)
    Button btnSign;
    @BindView(R.id.ll_av_loading_transparent_44)
    LinearLayout llAvLoadingTransparent44;

    @Override
    protected void initInject() {
        getViewComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_apply_vacation;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        tvTitle.setText(getText(R.string.apply_vacation));
        initLisener();
    }

    private void initLisener() {
        rgApplyVacation.setOnCheckedChangeListener(this);
        RxView.clicks(btnStartTime)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {

                    @Override
                    public void accept(Object o) throws Exception {
                        alertDialog();
                    }
                });
    }

    private void alertDialog() {
        final SelectVacationDayDialog sbDialog=new SelectVacationDayDialog(this);
        sbDialog.setOnBirthChangeListener(new SelectVacationDayDialog.OnBirthChangeListener() {
            @Override
            public void changeBirthday(String birthday) {
                sbDialog.cancel();
            }
        });
        sbDialog.showDialog(this, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch (i) {
            case R.id.rb_sick_leave:
                break;
            case R.id.rb_casual_leave:
                break;
            case R.id.rb_other_leave:
                break;
        }
    }
}
