package com.virgo.financeloan.ui.view;

import android.content.Context;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;

import com.virgo.financeloan.R;

/**
 * 功能说明： 协议列表
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/11 17:07
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
public class ClickMoreTextView extends android.support.v7.widget.AppCompatTextView {

    public ClickMoreTextView(Context context) {
        this(context, null);
    }

    public ClickMoreTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClickMoreTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setLineSpacing(0f, 1.2f);
    }

    /**
     * 重组显示文本
     * @param textArray  显示文本数组
     */
    public void splitCharSequence(String[] textArray, boolean isShowHeadTail) {
        if (textArray == null || textArray.length <= 0) {
            new Throwable("textArray must hava");
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < textArray.length; i ++) {
            stringBuilder.append(textArray[i]);
        }
        String showText = stringBuilder.toString();
        setMovementMethod(LinkMovementMethod.getInstance());
        setText(addPartClickListener(showText, textArray, isShowHeadTail), BufferType.SPANNABLE);
    }

    private SpannableStringBuilder addPartClickListener(String showText, String[] textArray, boolean isShowHeadTail) {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        SpannableString head = new SpannableString(getContext().getString(R.string.agreement_prefix));
        if (isShowHeadTail) {
            head.setSpan(new ForegroundColorSpan(0xFF999999), 0, head.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb.append(head);
        }
        ssb.append(showText) ;
        SpannableString tail = new SpannableString(getContext().getString(R.string.agreement_suffix));
        if (isShowHeadTail) {
            tail.setSpan(new ForegroundColorSpan(0xFF999999), 0, tail.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb.append(tail);
        }

        for (int i = 0; i < textArray.length; i++) {
            int start = 0;
            if (isShowHeadTail) {
                start = head.length();
            }
            if (i > 0) {
                for (int j = 0; j < i; j++) {
                    start = start + textArray[j].length();
                }
            }
            final int finalI = i;
            ssb.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onPositionClick(finalI);
                    }
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(0xFF1F92f9);
                    ds.setUnderlineText(false);//去掉下划线
                }
            }, start, start + textArray[i].length(), 0);
        }
        return ssb;
    }

    private OnPositionClickListener listener;
    public interface OnPositionClickListener {
        void onPositionClick(int position);
    }
    public void setOnPositionClickListener(OnPositionClickListener listener) {
        this.listener = listener;
    }


}
