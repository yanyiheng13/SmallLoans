package com.virgo.financeloan.user.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

/**
 * 功能说明：自定义安全键盘
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2018/1/24 11:32
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */

public class SafeKeyboardView extends KeyboardView {

    public SafeKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SafeKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public SafeKeyboardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
