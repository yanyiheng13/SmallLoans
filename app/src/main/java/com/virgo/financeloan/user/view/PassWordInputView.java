package com.virgo.financeloan.user.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.virgo.financeloan.R;

import butterknife.ButterKnife;

/**
 * 功能说明：密码输入框
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2018/1/24 11:43
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */

public class PassWordInputView extends RelativeLayout {

    public PassWordInputView(Context context) {
        super(context);
        init();
    }

    public PassWordInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PassWordInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public PassWordInputView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_password_input, this);
        ButterKnife.bind(this, this);
    }
}
