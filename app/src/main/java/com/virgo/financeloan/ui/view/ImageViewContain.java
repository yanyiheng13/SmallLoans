package com.virgo.financeloan.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 功能说明：
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/18 15:13
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */

public class ImageViewContain extends LinearLayout {

    public ImageViewContain(Context context) {
        super(context);
    }

    public ImageViewContain(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageViewContain(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.VERTICAL);
    }
}
