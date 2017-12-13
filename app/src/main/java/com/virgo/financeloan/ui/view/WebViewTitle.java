package com.virgo.financeloan.ui.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.virgo.financeloan.R;

/**
 * 功能说明： </br>
 * webView的title
 * 扩展此类时,将xml里merge标签改为RelativeLayout,打开注释部分,即可方便修改
 * @author:
 * @version: 1.0
 * @date: 2016/12/7
 * @Copyright (c) 2016. Administrator Inc. All rights reserved.
 */

public class WebViewTitle extends RelativeLayout {
    private TextView mLeftText;
    private ImageView mLeftImg;
    private TextView mRightText;
    private TextView mRightSecondText;
    private ImageView mRightImg;
    private ImageView mRightSecondImg;
    private TextView mTitleText;
    private RelativeLayout mRightLayout;
    private Drawable mDrawable;


    public WebViewTitle(Context context) {
        this(context, null);
    }

    public WebViewTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.view_web_title, this);
        mLeftText = (TextView) this.findViewById(R.id.tv_left_text);
        mLeftImg = (ImageView) this.findViewById(R.id.iv_left_img);
        mRightLayout = (RelativeLayout) this.findViewById(R.id.rl_right_custom);
        mRightText = (TextView) this.findViewById(R.id.tv_right_text);
        mRightSecondText = (TextView) this.findViewById(R.id.tv_right_text_second);
        mRightImg = (ImageView) this.findViewById(R.id.iv_right_img);
        mRightSecondImg = (ImageView) this.findViewById(R.id.iv_right_img_second);
        mTitleText = (TextView) this.findViewById(R.id.view_title_text);
        initConfig();
    }

    /** 默认配置*/
    private void initConfig() {
        WebViewTitle.this.setBackgroundColor(getResources().getColor(R.color.blue_main));
        //下面两行代码顺序不能错,不然会导致onclick事件为null
        setLeftImageResource(R.drawable.icon_back);
        mLeftText.setOnClickListener(mOnClickListener);
        setTitleColor(0xFFFFFFFF);
        // setLeftText("AAA");
    }

    OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            ((Activity) getContext()).finish();
        }
    };

    /**设置标题*/
    public void setTitle(String title) {
        mTitleText.setText(title);
    }

    /** 标题颜色*/
    public void setTitleColor(int color) {
        mTitleText.setTextColor(color);
    }

    public void setTitle(int stringId) {
        mTitleText.setText(stringId);
    }

    public void setViewBackgroundColor(int color) {
        WebViewTitle.this.setBackgroundColor(color);
    }

    /** 左边文字*/
    public void setLeftText(String text) {
        mLeftText.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        setLeftText(text, null);
    }

    /** 左边文字*/
    public void setLeftText(String text, View.OnClickListener onClickListener) {
        mLeftText.setOnClickListener(onClickListener);
        mLeftText.setText(text);
    }

    public void setLeftTextSize() {

    }

    /*** 左边图片*/
    public void setLeftImageResource(int drawableId) {
        setLeftImageResource(drawableId, null);
    }

    /*** 左边图片*/
    public void setLeftImageResource(int drawableId, View.OnClickListener onClickListener) {
        Drawable drawable = getResources().getDrawable(drawableId);
        mLeftText.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        mLeftText.setOnClickListener(onClickListener);
    }

    /** 左边无内容*/
    public void setLeftGone() {
        leftHide();
    }
    /** 左边显示*/
    public void setLeftShow() {
        leftShow();
    }

    private void leftHide() {
        mLeftText.setOnClickListener(null);
        if (mLeftText.getVisibility() != View.GONE) mLeftText.setVisibility(View.GONE);
    }
    private void leftShow() {
        mLeftText.setOnClickListener(mOnClickListener);
        if (mLeftText.getVisibility() != View.VISIBLE) mLeftText.setVisibility(View.VISIBLE);
    }

    /** 左边返回点击*/
    public void setLeftReturn(View.OnClickListener listener) {
        mLeftText.setOnClickListener(listener);
    }

    /** 右边文字*/
    public void setRightText(String text) {
        setRightText(null, text);

    }

    /** 右边文字*/
    public void setRightText(View.OnClickListener onClickListener, String text) {
        rightTextShow();
        mRightText.setOnClickListener(onClickListener);
        mRightText.setText(text);
    }

    /** 右边第二个文字*/
    public void setRightSecondText(String text) {
        setRightSecondText(text, null);
    }

    /** 右边第二个文字*/
    public void setRightSecondText(String text, View.OnClickListener onClickListener) {
        rightSecondTextShow();
        mRightSecondText.setOnClickListener(onClickListener);
        mRightSecondText.setText(text);
    }


    /** 右边第一个无内容*/
    public void setRightGone() {
        rightTextHide();
        rightImgHide();
    }

    /**
     * 右边第二个无内容
     */
    public void setRightSecondGone() {
        rightSecondTextHide();
        rightSecondImgHide();
    }

    /** 右边图片*/
    public void setRightImage(int drawableId) {
        setRightImage(null, drawableId);
    }

    /** 右边图片*/
    public void setRightImage(View.OnClickListener onClickListener, int drawableId) {
        rightImgShow();
        mRightImg.setBackgroundResource(drawableId);
//        mRightImg.setImageResource(drawableId);
        mRightImg.setOnClickListener(onClickListener);
    }
    /** 右边图片*/
    public void setRightImage(View.OnClickListener onClickListener, Drawable drawable) {
        rightImgShow();
        mRightImg.setImageDrawable(drawable);
        mRightImg.setOnClickListener(onClickListener);
    }
    /** 右边网络图片*/
    public void setRightImageURL(String url) {
        setRightImageURL(url,null);
    }
    /** 右边网络图片*/
    public void setRightImageURL(String url, View.OnClickListener onClickListener) {
        rightImgShow();
//        Glide.with(getContext().getApplicationContext()).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                mRightImg.setImageBitmap(resource);
//            }
//        });
//        Glide.with(getContext().getApplicationContext()).load(url).crossFade(1000).centerCrop().into(mRightImg);
        mRightImg.setOnClickListener(onClickListener);
    }
    /**
     * 缩放图片
     *
     * @param bm
     * @param newWidth
     * @param newHeight
     * @return
     */
    private Bitmap getScaleBitmap(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = (float) newWidth / width;
        float scaleHeight = (float) newHeight / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    }

    /** 第二个右边图片*/
    public void setRightSecondImageResource(int drawableId) {
        setRightSecondImageResource(drawableId, null);
    }

    /** 第二个右边图片*/
    public void setRightSecondImageResource(int drawableId, View.OnClickListener onClickListener) {
        rightSecondImgShow();
        mRightSecondImg.setImageResource(drawableId);
        mRightSecondImg.setOnClickListener(onClickListener);
    }

    /** 第二个右边网络图片*/
    public void setRightSecondImageURL(String url) {
        setRightSecondImageURL(url,null);
    }

    /** 第二个右边网络图片*/
    public void setRightSecondImageURL(String url, View.OnClickListener onClickListener) {
        rightSecondImgShow();
//        Glide.with(getContext().getApplicationContext()).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                mRightSecondImg.setImageBitmap(resource);
//            }
//        });
        mRightSecondImg.setOnClickListener(onClickListener);
    }

    /** 右边自定义布局*/
    public void setRightCustomView(View view){
       setRightCustomView(view,null);
    }
    /** 右边自定义布局*/
    public void setRightCustomView(View view, OnClickListener onClickListener){
        rightTextHide();
        rightSecondTextHide();
        rightSecondImgHide();
        mRightLayout.removeAllViews();
        mRightLayout.addView(view);
        mRightLayout.setOnClickListener(onClickListener);
    }
    /** 右边自定义布局*/
    public void setRightCustomView(int id){
        rightTextHide();
        rightSecondTextHide();
        rightSecondImgHide();
        mRightLayout.removeAllViews();
        View view =inflate(getContext(), id, null);
        mRightLayout.addView(view);
    }

    private void rightTextHide() {
        mRightText.setOnClickListener(null);
        if (mRightText.getVisibility() != View.INVISIBLE) mRightText.setVisibility(View.INVISIBLE);
    }
    private void rightTextShow() {
        rightImgHide();
        if (mRightText.getVisibility() != View.VISIBLE) mRightText.setVisibility(View.VISIBLE);
    }
    private void rightImgHide() {
        mRightImg.setOnClickListener(null);
        if (mRightImg.getVisibility() != View.INVISIBLE) mRightImg.setVisibility(View.INVISIBLE);
    }
    private void rightImgShow() {
        rightTextHide();
        if (mRightImg.getVisibility() != View.VISIBLE) mRightImg.setVisibility(View.VISIBLE);
    }


    private void rightSecondTextHide() {
        mRightSecondText.setOnClickListener(null);
        if (mRightSecondText.getVisibility() != View.GONE)
            mRightSecondText.setVisibility(View.GONE);
    }
    private void rightSecondTextShow(){
        rightSecondImgHide();
        if (mRightSecondText.getVisibility() != View.VISIBLE)
            mRightSecondText.setVisibility(View.VISIBLE);
    }
    private void rightSecondImgHide(){
        mRightSecondImg.setOnClickListener(null);
        if (mRightSecondImg.getVisibility() != View.INVISIBLE)
            mRightSecondImg.setVisibility(View.INVISIBLE);
    }
    private void rightSecondImgShow(){
        rightSecondTextHide();
        if (mRightSecondImg.getVisibility() != View.VISIBLE)
            mRightSecondImg.setVisibility(View.VISIBLE);
    }
}
