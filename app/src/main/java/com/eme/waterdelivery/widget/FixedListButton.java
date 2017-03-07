package com.eme.waterdelivery.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

/**
 * Created by zulei on 16/8/16.
 */
public class FixedListButton extends Button {
    public FixedListButton(Context context) {
        super(context);
    }

    public FixedListButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixedListButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setPressed(boolean pressed) {
        if (pressed && getParent() instanceof View
                && ((View) getParent()).isPressed()) {
            return;
        }
        super.setPressed(pressed);
    }
}
