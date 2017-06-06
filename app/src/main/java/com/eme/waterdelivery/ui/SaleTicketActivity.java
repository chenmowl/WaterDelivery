package com.eme.waterdelivery.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eme.waterdelivery.App;
import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.base.BaseActivity;
import com.eme.waterdelivery.contract.SaleTicketContract;
import com.eme.waterdelivery.model.bean.entity.GetActiveInfoByTicketBo;
import com.eme.waterdelivery.model.bean.entity.GetAddressByPhoneBo;
import com.eme.waterdelivery.model.bean.entity.GetTicketInfoBo;
import com.eme.waterdelivery.model.bean.entity.WaterTicketBean;
import com.eme.waterdelivery.presenter.SaleTicketPresenter;
import com.eme.waterdelivery.tools.ImageLoader;
import com.eme.waterdelivery.tools.NetworkUtils;
import com.eme.waterdelivery.tools.ToastUtil;
import com.eme.waterdelivery.tools.ZeroTextWatcher;
import com.eme.waterdelivery.ui.adapter.CommonAddressAdapter;
import com.eme.waterdelivery.ui.adapter.SaleTicketAdapter;
import com.eme.waterdelivery.widget.FullyLinearLayoutManager;
import com.jakewharton.rxbinding2.view.RxView;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

import static com.eme.waterdelivery.R.id.et_invoice;

/**
 * 售票页面
 * <p>
 * Created by dijiaoliang on 17/4/25.
 */
public class SaleTicketActivity extends BaseActivity<SaleTicketPresenter> implements SaleTicketContract.View, TextWatcher, View.OnClickListener {

    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_right)
    TextView btnRight;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(et_invoice)
    EditText etInvoice;
    @BindView(R.id.rg_invoice_info_content)
    RadioGroup rgInvoiceInfoContent;
    @BindView(R.id.rv_goods)
    RecyclerView rvGoods;
    @BindView(R.id.tv_pay_info_title)
    TextView tvPayInfoTitle;
    @BindView(R.id.tv_pay_type_title)
    TextView tvPayTypeTitle;
    @BindView(R.id.tv_pay_status_title)
    TextView tvPayStatusTitle;
    @BindView(R.id.sv)
    ScrollView sv;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.ll_av_loading_transparent_44)
    LinearLayout llAvLoadingTransparent44;
    @BindView(R.id.btn_search_address)
    TextView btnSearchAddress;
    @BindView(R.id.rb_invoice_no)
    RadioButton rbInvoiceNo;
    @BindView(R.id.rb_invoice_yes)
    RadioButton rbInvoiceYes;
    @BindView(R.id.rb_paper_ticket)
    RadioButton rbPaperTicket;
    @BindView(R.id.rb_electron_ticket)
    RadioButton rbElectronTicket;
    @BindView(R.id.rg_ticket)
    RadioGroup rgTicket;
    @BindView(R.id.rb_money)
    RadioButton rbMoney;
    @BindView(R.id.rb_wx)
    RadioButton rbWx;
    @BindView(R.id.rg_pay_mode)
    RadioGroup rgPayMode;
    @BindView(R.id.btn_add_ticket)
    Button btnAddTicket;
    @BindView(R.id.tv_amount)
    TextView tvAmount;

    private List<WaterTicketBean> mData;

    private boolean isInvoice;//是否开具发票

    private String ticketsModel;//水票类型

    private String payType;

    private String memberAdressId;//用户地址id

    @Override
    protected void initInject() {
        getViewComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_sale_ticket;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        tvTitle.setText(getText(R.string.sale_ticket));
        btnRight.setText(getText(R.string.sale_ticket_record));
        ticketsModel = Constant.TICKET_TYPE_PAPER;
        payType = Constant.PAY_MODE_MONEY;

        // 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvGoods.setHasFixedSize(true);
        rvGoods.setNestedScrollingEnabled(false);
        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(App.getAppInstance());
        manager.setAutoMeasureEnabled(true);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvGoods.setLayoutManager(manager);
        mData = new ArrayList<>();
        SaleTicketAdapter adapter = new SaleTicketAdapter(this, mData);
        rvGoods.setAdapter(adapter);
        rvGoods.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
                WaterTicketBean bean = mData.get(position);
                int number = bean.getNumber();
                switch (view.getId()) {
                    case R.id.btn_reduce:
                        if (number == Constant.ONE) {
                            ToastUtil.shortToast(SaleTicketActivity.this, R.string.tip_reduce);
                            return;
                        }
                        bean.setNumber(number - 1);
                        adapter.notifyItemChanged(position);
                        break;
                    case R.id.btn_add:
                        bean.setNumber(number + 1);
                        adapter.notifyItemChanged(position);
                        break;
                    case R.id.ll_close:
                        mData.remove(position);
                        adapter.notifyDataSetChanged();
                        break;
                }
                updateAmount();
            }
        });
        tvAmount.setText(getText(R.string.money_unit) + "0");
        initListener();

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
        RxView.clicks(btnRight)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {

                    @Override
                    public void accept(Object o) throws Exception {
                        if (!NetworkUtils.isConnected(SaleTicketActivity.this)){
                            showNetError();
                            return;
                        }
                        startActivity(new Intent(SaleTicketActivity.this, SaleTicketRecordActivity.class));
                    }
                });
        RxView.clicks(btnAddTicket)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {

                    @Override
                    public void accept(Object o) throws Exception {
                        if (NetworkUtils.isConnected(SaleTicketActivity.this)) {
                            mPresenter.requestTicketList(ticketsModel);
                        } else {
                            showNetError();
                        }
                    }
                });
        RxView.clicks(btnSearchAddress)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        String phone = etPhone.getText().toString().trim();
                        if (!TextUtils.isEmpty(phone)) {
                            if (NetworkUtils.isConnected(SaleTicketActivity.this)) {
                                mPresenter.requestAddress(phone);
                            } else {
                                showNetError();
                            }
                        } else {
                            ToastUtil.shortToast(SaleTicketActivity.this, R.string.input_phone_please);
                        }
                    }
                });
        RxView.clicks(btnConfirm)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        gettTicketActivityInfo();
                    }
                });

        etAddress.addTextChangedListener(this);
        rgInvoiceInfoContent.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rb_invoice_no:
                        isInvoice = false;
                        etInvoice.setText(Constant.STR_EMPTY);
                        break;
                    case R.id.rb_invoice_yes:
                        isInvoice = true;
                        break;
                }
            }
        });
        rgTicket.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rb_paper_ticket:
                        ticketsModel = Constant.TICKET_TYPE_PAPER;
                        break;
                    case R.id.rb_electron_ticket:
                        ticketsModel = Constant.TICKET_TYPE_ELECTRON;
                        break;
                }
                mData.clear();
                rvGoods.getAdapter().notifyDataSetChanged();
            }
        });
        rgPayMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rb_wx:
                        payType = Constant.PAY_MODE_WEIXIN;
                        mPresenter.getWXImage();
                        break;
                    case R.id.rb_money:
                        payType = Constant.PAY_MODE_MONEY;
                        break;
                }
            }
        });
        rgPayMode.check(R.id.rb_money);
        rgTicket.check(R.id.rb_paper_ticket);
        rgInvoiceInfoContent.check(R.id.rb_invoice_no);
        isInvoice = false;
    }

    /**
     * 获取水票活动信息
     */
    private void gettTicketActivityInfo() {
        if(TextUtils.isEmpty(etUsername.getText().toString().trim())){
            ToastUtil.shortToast(this,R.string.tip_has_not_username);
            return;
        }
        if(TextUtils.isEmpty(etPhone.getText().toString().trim())){
            ToastUtil.shortToast(this,R.string.tip_has_not_phone);
            return;
        }
        if(TextUtils.isEmpty(etAddress.getText().toString().trim())){
            ToastUtil.shortToast(this,R.string.tip_has_not_address);
            return;
        }
        if(isInvoice && TextUtils.isEmpty(etInvoice.getText().toString().trim())){
            ToastUtil.shortToast(this,R.string.tip_has_not_invoice);
            return;
        }
        if (mData != null && mData.size() == 0) {
            ToastUtil.shortToast(this, R.string.tip_has_not_add_ticket);
            return;
        }
        if (NetworkUtils.isConnected(this)) {
            mPresenter.getTicketActivityInfo(ticketsModel, JSON.toJSONString(mData));
        } else {
            showNetError();
        }
    }

    AlertDialog dialog;

    /**
     * 搜索常用地址
     */
    private void alertDialog(final List<GetAddressByPhoneBo.ListBean> list) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_list_address, null, false);
        RecyclerView recycler = (RecyclerView) view.findViewById(R.id.rv_address);
        recycler.setHasFixedSize(true);
        recycler.setNestedScrollingEnabled(false);
        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(App.getAppInstance());
        manager.setAutoMeasureEnabled(true);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(manager);
        CommonAddressAdapter adapter=new CommonAddressAdapter(this,list);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GetAddressByPhoneBo.ListBean model=list.get(position);
                memberAdressId = model.getId();
                etAddress.setText(model.getAddress());
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        recycler.setAdapter(adapter);
        builder.setView(view);
        dialog = builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        memberAdressId = Constant.STR_EMPTY;
    }

    @Override
    public void showProgress(boolean isShow) {
        isShowLayer(llAvLoadingTransparent44, isShow);
    }

    @Override
    public void getImageFailure(String message) {
        if (TextUtils.isEmpty(message)) {
            ToastUtil.shortToast(this, R.string.get_wx_image_failure);
        } else {
            ToastUtil.shortToast(this, message);
        }
    }

    @Override
    public void showRequestTicketActivityInfoResult(boolean isSuccess, List<GetActiveInfoByTicketBo.ListBean> list, String message) {
        if(isSuccess){
            alertTicketActivityInfoDialog(list);
        }else{
            ToastUtil.shortToast(this,R.string.get_ticket_activity_info_failure);
        }
    }

    @Override
    public void showTicketSubmitResult(boolean isSuccess, String message) {
        if(isSuccess){
            if(TextUtils.isEmpty(message)){
                ToastUtil.shortToast(this,R.string.commit_success);
            }else{
                ToastUtil.shortToast(this,message);
            }
            finish();//提交成功就关闭当前页面
        }else{
            if(TextUtils.isEmpty(message)){
                ToastUtil.shortToast(this,R.string.commit_failure);
            }else{
                ToastUtil.shortToast(this,message);
            }
        }
    }

    @Override
    public void showRequestAddressResult(boolean isSuccess, List<GetAddressByPhoneBo.ListBean> list, String message) {
        if (isSuccess) {
            if (list != null && list.size() > 0) {
                alertDialog(list);
            } else {
                ToastUtil.shortToast(this, R.string.no_address_relate_this_number);
            }
        } else {
            if (!TextUtils.isEmpty(message)) {
                ToastUtil.shortToast(this, message);
            } else {
                ToastUtil.shortToast(this, R.string.search_address_failure);
            }
        }
    }

    @Override
    public void showRequestTicketResult(boolean isSuccess, List<GetTicketInfoBo.ListBean> list, String message) {
        if (isSuccess) {
            if (list != null && list.size() > 0) {
                alertTicketDialog(list);
            } else {
                ToastUtil.shortToast(this, R.string.no_ticket_info);
            }
        } else {
            if (!TextUtils.isEmpty(message)) {
                ToastUtil.shortToast(this, message);
            } else {
                ToastUtil.shortToast(this, R.string.search_ticket_failure);
            }
        }
    }

    AlertDialog dialogActivityInfo;
    /**
     * 加载水票活动的dialog
     * @param list
     */
    private void alertTicketActivityInfoDialog(List<GetActiveInfoByTicketBo.ListBean> list) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View viewWX = LayoutInflater.from(this).inflate(R.layout.dialog_ticket_activity_info, null, false);
        LinearLayout liner= (LinearLayout) viewWX.findViewById(R.id.ll_activity_info);
        liner.removeAllViews();
        if(list!=null && list.size()>0){
            for (GetActiveInfoByTicketBo.ListBean bean:list){
                TextView textview=new TextView(this);
                textview.setTextColor(getResources().getColor(R.color.text_color_bar));
                textview.setTextSize(14);
                textview.setText(TextUtils.concat(bean.getTicketName(),": ",String.valueOf(bean.getTicketNumber()),"张  尊享  ",bean.getActiveName(),"\r\n获赠  ",bean.getGiftGoodsName() ,": ",String.valueOf(bean.getGiftGoodsNumber()),"张"));
                liner.addView(textview);
            }
        }else{
            TextView textview=new TextView(this);
            textview.setTextColor(getResources().getColor(R.color.text_color_bar));
            textview.setTextSize(14);
            textview.setText(getText(R.string.tip_no_activity_info));
            liner.addView(textview);
        }
        viewWX.findViewById(R.id.btn_cancel_activity).setOnClickListener(this);
        viewWX.findViewById(R.id.btn_confirm_activity).setOnClickListener(this);
        builder.setView(viewWX);
        dialogActivityInfo=builder.show();
    }


    AlertDialog dialogWX;
    /**
     * 加载微信二维码弹出框
     *
     * @param imageUrl
     */
    @Override
    public void loadWXImage(String imageUrl) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View viewWX = LayoutInflater.from(this).inflate(R.layout.dialog_image_wx, null, false);
        ImageLoader.load(this, imageUrl, (ImageView) viewWX.findViewById(R.id.iv_wx));
        viewWX.findViewById(R.id.btn_close).setOnClickListener(this);
        builder.setView(viewWX);
        dialogWX=builder.show();
    }

    AlertDialog dialogTicket;
    View dialogView;
    AppCompatEditText etTicketAmount;
    BetterSpinner typeSpinner;
    List<String> tempData;
    List<GetTicketInfoBo.ListBean> ticketList;
    ArrayAdapter<String> adapterType;
    int positionTicket;

    /**
     * 弹出水票选择窗口
     *
     * @param list
     */
    private void alertTicketDialog(List<GetTicketInfoBo.ListBean> list) {
        ticketList = list;
        positionTicket = Constant.MINUS;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_choice_ticket, null, false);
        etTicketAmount = (AppCompatEditText) dialogView.findViewById(R.id.et_amount);
        etTicketAmount.addTextChangedListener(new ZeroTextWatcher());
        typeSpinner = (BetterSpinner) dialogView.findViewById(R.id.ns_type);
        if (tempData != null) {
            tempData.clear();
        } else {
            tempData = new ArrayList<>();
        }
        for (GetTicketInfoBo.ListBean bean : list) {
            tempData.add(bean.getPrice());
        }
        adapterType = new ArrayAdapter(this, R.layout.item_spinner_apply, tempData);
        typeSpinner.setAdapter(adapterType);
        typeSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View viw, int i, long l) {
                positionTicket = i;
            }
        });
        dialogView.findViewById(R.id.btn_cancel).setOnClickListener(this);
        dialogView.findViewById(R.id.btn_confirm).setOnClickListener(this);
        builder.setView(dialogView);
        dialogTicket = builder.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                if (dialogTicket != null)
                    dialogTicket.dismiss();
                dialogTicket = null;
                break;
            case R.id.btn_confirm:
                //todo 把采购项添加进列表
//                KeyboardUtils.forceCloseSoftInput(App.getAppInstance(), dialog);
                if (positionTicket == Constant.MINUS) {
                    ToastUtil.shortToast(this, R.string.no_select_ticket_type);
                    dialogTicket.dismiss();
                    dialogTicket = null;
                    return;
                }
                String count = etTicketAmount.getText().toString();
                if (!TextUtils.isEmpty(count)) {
                    for (WaterTicketBean bean : mData) {
                        if (bean.getId().equals(ticketList.get(positionTicket).getId())) {
                            bean.setNumber(bean.getNumber() + Integer.parseInt(count));
                            rvGoods.getAdapter().notifyDataSetChanged();
                            ToastUtil.shortToast(this, R.string.add_ticket_success);
                            dialogTicket.dismiss();
                            dialogTicket = null;
                            updateAmount();
                            return;
                        }
                    }
                    WaterTicketBean waterTicketBean = new WaterTicketBean();
                    waterTicketBean.setId(ticketList.get(positionTicket).getId());
                    waterTicketBean.setNumber(Integer.parseInt(count));
                    waterTicketBean.setPrice(ticketList.get(positionTicket).getPrice());
                    mData.add(waterTicketBean);
                    rvGoods.getAdapter().notifyDataSetChanged();
                    ToastUtil.shortToast(this, R.string.add_ticket_success);
                } else {
                    ToastUtil.shortToast(this, R.string.no_input_ticket_count);
                }
                updateAmount();
                dialogTicket.dismiss();
                dialogTicket = null;
                break;
            case R.id.btn_close:
                if (dialogWX != null)
                dialogWX.dismiss();
                dialogWX = null;
                break;
            case R.id.btn_cancel_activity:
                if (dialogActivityInfo != null)
                    dialogActivityInfo.dismiss();
                dialogActivityInfo = null;
                break;
            case R.id.btn_confirm_activity:

                //这里进行售票签收
                if(NetworkUtils.isConnected(this)){
                    String username=etUsername.getText().toString().trim();
                    if(TextUtils.isEmpty(username)){
                        ToastUtil.shortToast(this,R.string.tip_has_not_username);
                        return;
                    }
                    String phone=etPhone.getText().toString().trim();
                    if(TextUtils.isEmpty(phone)){
                        ToastUtil.shortToast(this,R.string.tip_has_not_phone);
                        return;
                    }
                    String address=etAddress.getText().toString().trim();
                    if(TextUtils.isEmpty(address)){
                        ToastUtil.shortToast(this,R.string.tip_has_not_address);
                        return;
                    }
                    String invoiceTitle=etInvoice.getText().toString().trim();
                    if(isInvoice && TextUtils.isEmpty(invoiceTitle)){
                        ToastUtil.shortToast(this,R.string.tip_has_not_invoice);
                        return;
                    }
                    if (mData != null && mData.size() == 0) {
                        ToastUtil.shortToast(this, R.string.tip_has_not_add_ticket);
                        return;
                    }
                    mPresenter.sellTicketSubmit(username,phone,address,invoiceTitle,ticketsModel,payType,memberAdressId,JSON.toJSONString(mData));
                }else{
                    showNetError();
                }
                if (dialogActivityInfo != null)
                    dialogActivityInfo.dismiss();
                dialogActivityInfo = null;
                break;
            default:
                break;
        }
    }

    /**
     * 刷新价格
     */
    public void updateAmount() {
        BigDecimal tempBigDecimal = new BigDecimal(0f);
        for (WaterTicketBean bean : mData) {
            tempBigDecimal = tempBigDecimal.add(new BigDecimal(bean.getPrice()).multiply(new BigDecimal(bean.getNumber())));
        }
        tvAmount.setText(getText(R.string.money_unit) + String.valueOf(tempBigDecimal));
    }

    @Override
    public int[] hideSoftByEditViewIds() {
        int[] ids = {R.id.et_username, R.id.et_phone, R.id.et_address, R.id.et_invoice};
        return ids;
    }

    @Override
    public View[] filterViewByIds() {
        View[] views = {etUsername, etPhone,etInvoice,etAddress};
        return views;
    }
}
