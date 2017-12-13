package com.virgo.financeloan.ui.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.virgo.financeloan.R;

/**
 * 功能说明： 加载View
 *
 * @author: Yiheng Yan
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 16-5-25
 * @Copyright (c) 2016. yanyiheng Inc. All rights reserved.
 **/

public class EmptyView extends RelativeLayout implements View.OnClickListener {
    /** 显示数据状态的view **/
    private ImageView mStateImg;

    /** 加载中旋转动画的view **/
    private ImageView mLoadingImg;

    /** 显示状态的文字 **/
    private TextView mStateTxt;


    /** 绑定的View,当隐藏状态view mStateImg的时候将其隐藏 **/
    private View mBindView;

    /** loading中的父布局 **/
    private FrameLayout mLoadingLayout;

    /** 出错图片资源id **/
    private int mErrorImgResId;

    /** 无数据图片资源id **/
    private int mNoDataImgResId;

    /** 无数据文字资源id **/
    private int mNoDataTxtResId;

    /** 购买更多理财产品的数据等底部按钮上的文字 **/
    private int mBtnTxt;

    private Animation animation;

    public EmptyView(Context context) {
        this(context, null);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_jr_le, this);
        mStateTxt = (TextView) findViewById(R.id.tv_text);
        mStateImg = (ImageView) findViewById(R.id.img_empty);
        mLoadingImg = (ImageView) findViewById(R.id.empty_img_load_out);
        mLoadingLayout = (FrameLayout) findViewById(R.id.empty_loading_layout);

        initAnimView();
    }

    /**
     * 加载动画
     */
    private void initAnimView() {
        animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.RESTART);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(800);
    }

    /**
     * 绑定的界面布局在加载各种状态消失时自动显示界面数据
     * @param view
     */
    public void bindView(View view) {
        mBindView = view;
    }

    /**
     * 开始
     */
    public void onStart() {
        this.setVisibility(View.VISIBLE);
        if (mBindView != null) {
            mBindView.setVisibility(View.GONE);
        }
        mStateTxt.setVisibility(View.GONE);
        mStateImg.setVisibility(View.GONE);
        setOnClickListener(null);

        if (animation == null) {
            initAnimView();
        }
        mLoadingLayout.setVisibility(View.VISIBLE);
        mLoadingImg.startAnimation(animation);
    }

    /**
     * 加载数据失败
     */
    public void onError() {
        this.setVisibility(View.VISIBLE);
        mStateImg.setVisibility(View.VISIBLE);
        if (mBindView != null) {
            mBindView.setVisibility(View.GONE);
        }
        if (mStateImg != null) {
            mStateImg.clearAnimation();
            setOnClickListener(this);
            mStateImg.setImageDrawable(ContextCompat.getDrawable(getContext(), mErrorImgResId == 0 ? R.mipmap.icon_net_error : mErrorImgResId));
        }
        if (mStateTxt != null) {
            mStateTxt.setVisibility(View.VISIBLE);
            mStateTxt.setText(R.string.loading_error);
        }
        mLoadingLayout.setVisibility(View.GONE);
        mLoadingImg.clearAnimation();
    }

    /**
     * 成功后的处理
     */
    public void onSuccess() {
        if (mStateImg != null) {
            mStateImg.clearAnimation();
        }
        this.setVisibility(View.GONE);
        if (mBindView != null) {
            mBindView.setVisibility(View.VISIBLE);
        }
        mLoadingLayout.setVisibility(View.GONE);
        mLoadingImg.clearAnimation();
    }

    /**
     * 没有数据显示的状态
     */
    public void onEmpty() {
        this.setVisibility(View.VISIBLE);
        mStateImg.setVisibility(View.VISIBLE);
        if (mBindView != null) {
            mBindView.setVisibility(View.INVISIBLE);
        }
        if (mStateImg != null) {
            mStateImg.clearAnimation();
            setOnClickListener(this);
            mStateImg.setImageDrawable(ContextCompat.getDrawable(getContext(), mNoDataImgResId == 0 ? R.mipmap.icon_norecord : mNoDataImgResId));
        }
        if (mStateTxt != null) {
            mStateTxt.setVisibility(View.VISIBLE);
            mStateTxt.setText(mNoDataTxtResId == 0 ? R.string.loading_no_record : mNoDataTxtResId);
            mStateTxt.setText(mNoDataTxtResId == 0 ? R.string.loading_no_record : mNoDataTxtResId);
        }
        mLoadingLayout.setVisibility(View.GONE);
        mLoadingImg.clearAnimation();
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            onStart();
            listener.onDataLoadAgain();
        }
    }

    /**
     * 底部等点击按钮显示按钮字体文字资源id
     * @param btnTxt
     * @return
     */
    public EmptyView setBottomBtnTxt(int btnTxt) {
        this.mBtnTxt = btnTxt;
        return this;
    }


//    public LeJrEmptyView setErrorImageView(int errorImgResId) {
//        this.mErrorImgResId = errorImgResId;
//        return this;
//    }

    /**
     * 没有数据的特定显示图标
     * @param noDataImgResId
     * @return
     */
    public EmptyView setNoDataImageView(int noDataImgResId) {
        this.mNoDataImgResId = noDataImgResId;
        return this;
    }

    /**
     * 没有数据的图标下面特定文本显示
     * @param noDataTxtResId
     * @return
     */
    public EmptyView setNoDataTxtResId(int noDataTxtResId) {
        this.mNoDataTxtResId = noDataTxtResId;
        return this;
    }

    private OnDataLoadStatusListener listener;
    private OnGoBuyClickListener clickListener;

    public interface OnDataLoadStatusListener {
        void onDataLoadAgain();
    }

    public interface OnGoBuyClickListener {
        void onGoBuyClick();
    }

    /**
     * 点击屏幕再试事件
     * @param listener
     */
    public void setOnDataLoadStatusListener(OnDataLoadStatusListener listener) {
        this.listener = listener;
    }

    /**
     * 底部去购买更多产品按钮点击事件监听
     * @param clickListener
     */
    public void setOnGoBuyClickListener(OnGoBuyClickListener clickListener) {
        this.clickListener = clickListener;
    }

    /**
     * 资源回收
     */
    @Override
    protected void onDetachedFromWindow() {
        if (mLoadingImg != null) {
            mLoadingImg.clearAnimation();
        }
        super.onDetachedFromWindow();
    }

}
