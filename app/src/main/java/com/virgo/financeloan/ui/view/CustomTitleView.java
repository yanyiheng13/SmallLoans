package com.virgo.financeloan.ui.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.virgo.financeloan.R;
import com.virgo.financeloan.util.UIUtil;
import com.virgo.financeloan.util.ViewUtil;

/**
 * 自定义标题栏
 */
public class CustomTitleView extends RelativeLayout {
    private int mColor;
    private String mTitle;

    private TextView mTextView, mTvLeftText;

    private TextView mRightText;
    private ImageView imageViewRight, imageViewLeft;
    private RelativeLayout relaRight, relaLeft;


    public CustomTitleView(Context context) {
        this(context, null);
    }

    public CustomTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomTitleView);
        mColor = array.getColor(R.styleable.CustomTitleView_default_color, Color.WHITE);
        mTitle = array.getString(R.styleable.CustomTitleView_default_title);
        array.recycle();
        inflate(context, R.layout.view_custom_title, this);

        mRightText = (TextView) this.findViewById(R.id.tv_right_text);
        mTextView = (TextView) this.findViewById(R.id.view_title_text);
        mTvLeftText = (TextView) this.findViewById(R.id.tv_left_text);
        relaRight = (RelativeLayout) this.findViewById(R.id.view_right_contain);
        relaLeft = (RelativeLayout) findViewById(R.id.view_back_contain);
        imageViewRight = (ImageView) this.findViewById(R.id.view_title_right);
        imageViewLeft = (ImageView) this.findViewById(R.id.view_title_back);

        relaLeft.setOnClickListener(onBackListener);

        if (!TextUtils.isEmpty(mTitle)) {
            mTextView.setText(mTitle);
        }
        mTextView.setTextColor(mColor);
    }

    public void setShowLeftText(String text) {
        if (mTvLeftText != null) {
            mTvLeftText.setVisibility(VISIBLE);
            mTvLeftText.setText(text);
        }
        if (imageViewLeft != null) {
            imageViewLeft.setVisibility(GONE);
        }
    }

    /**
     * 取消按钮点击事件
     *
     * @param listener
     */
    public void setCancelTextClickListener(OnClickListener listener) {
        if (mTvLeftText != null) {
            relaLeft.setOnClickListener(listener);
        }
    }

    public void setTitle(String title) {
        mTextView.setText(title);
    }
    public String getTitle() {
        if (mTextView.getText() != null) {
            return  mTextView.getText().toString();
        }
        return null;
    }

    public void setTitle(int stringId) {
        mTextView.setText(stringId);
    }

    public void setLeftGone() {
        if (imageViewLeft != null) {
            imageViewLeft.setVisibility(View.GONE);
            relaLeft.setOnClickListener(null);
        }
    }

    public void setRightImage(OnClickListener listener, int drawableId) {
        imageViewRight.setBackgroundResource(drawableId);
        relaRight.setOnClickListener(listener);
        relaRight.setVisibility(View.VISIBLE);
    }

    /**
     * 右边文字及点击事件
     */
    public void setRightText(OnClickListener listener, String text) {
        mRightText.setText(text);
        mRightText.setTextColor(getResources().getColor(R.color.white));
        mRightText.setOnClickListener(listener);
        mRightText.setVisibility(View.VISIBLE);

    }
    /**
     * 右边文字及点击事件
     * @param listener
     * @param text
     * @param drawable
     */
    public void setRightText(OnClickListener listener, String text, Drawable drawable) {
        mRightText.setText(text);
        mRightText.setTextColor(getResources().getColor(R.color.white));
        mRightText.setOnClickListener(listener);
        mRightText.setVisibility(View.VISIBLE);
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth()+ UIUtil.dip2px(getContext(), 5), drawable.getMinimumHeight());
            mRightText.setCompoundDrawables(drawable,null,null,null);
        }
    }

    public void setLeftBackText(String text) {
        ViewUtil.visible(relaLeft, mTvLeftText);
        ViewUtil.gone(imageViewLeft);
        if (!TextUtils.isEmpty(text)) {
            mTvLeftText.setText(text);
        }
    }

    public void setLeftImageInvisible() {
        relaLeft.setEnabled(false);
        relaLeft.setVisibility(View.INVISIBLE);
    }

    public void setBackgroundColors(int color) {
        this.setBackgroundColor(color);
    }

    public void setLeftReturn(OnClickListener listener, int drawableId) {
        imageViewLeft.setBackgroundResource(drawableId);
        relaLeft.setOnClickListener(listener);
    }

    public void setLeftImageResource(int drawableId) {
        imageViewLeft.setBackgroundResource(drawableId);
    }

    public void setLeftReturn(OnClickListener listener) {
        relaLeft.setOnClickListener(listener);
    }

    OnClickListener onBackListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            ((Activity) getContext()).finish();
        }
    };

    public OnBackEventListener listener;
    public interface OnBackEventListener {
        void onBackEvent();
    }

    public void setOnBackEventListener(OnBackEventListener listener) {
        this.listener = listener;
    }

}
