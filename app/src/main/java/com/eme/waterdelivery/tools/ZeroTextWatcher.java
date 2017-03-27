package com.eme.waterdelivery.tools;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * EditText 过滤器
 * 过滤使其首个数字不能为0
 * Created by dijiaoliang on 17/3/24.
 */
public class ZeroTextWatcher implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String text = editable.toString();
        int len = editable.toString().length();
        if (len == 1 && text.equals("0")) {
            editable.clear();
        }
    }
}
