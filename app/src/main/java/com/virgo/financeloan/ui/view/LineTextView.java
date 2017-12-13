package com.virgo.financeloan.ui.view;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * 功能说明： 文字添加下划线
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/11 17:07
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */

public class LineTextView extends android.support.v7.widget.AppCompatTextView {

    public LineTextView(Context context) {
        this(context, null);
    }

    public LineTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//添加下划线
        getPaint().setAntiAlias(true);//抗锯齿
    }
}
