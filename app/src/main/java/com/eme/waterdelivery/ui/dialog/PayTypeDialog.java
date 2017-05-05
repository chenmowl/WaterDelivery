package com.eme.waterdelivery.ui.dialog;

import android.view.View;

import com.eme.waterdelivery.Constant;
import com.eme.waterdelivery.R;
import com.eme.waterdelivery.tools.Util;


/**
 * Created by zulei on 16/8/4.
 */
public class PayTypeDialog extends BaseDialog {

    /**
     * 构造函数
     */
    public PayTypeDialog() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_change_pay_type;
    }

    @Override
    protected void showView(View view) {
        view.findViewById(R.id.dialog_cs_bg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        //TODO 这里对控件事件进行控制
        view.findViewById(R.id.tv_order_pay_mode_money).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null) mListener.chooseType(Constant.PAY_TYPE_MONEY);
            }
        });
        view.findViewById(R.id.tv_order_pay_mode_weixin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null) mListener.chooseType(Constant.PAY_TYPE_WEIXIN);
            }
        });
        view.findViewById(R.id.tv_order_pay_mode_debt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null) mListener.chooseType(Constant.PAY_TYPE_DEBT);
            }
        });
    }

    @Override
    protected int getAnimStyle() {
        return R.style.AnimDialogBottomEntry;
    }

    @Override
    protected int getWidth() {
        return Util.getScreenWidth();
    }

    @Override
    protected int getHeight() {
        return Util.getScreenHeight()-Util.getStatusBarHeight();
    }

    private OnChoosePayTypeListener mListener;

    public void setOnChoosePayTypeListener(OnChoosePayTypeListener listener){
        mListener=listener;
    }

    public interface OnChoosePayTypeListener{

        void chooseType(String flag);
    }
}
