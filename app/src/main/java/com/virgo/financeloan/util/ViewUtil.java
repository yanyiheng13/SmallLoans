package com.virgo.financeloan.util;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.webkit.WebView;

import java.lang.reflect.Field;


/**
 * @author: huajie
 * @version: 1.0
 * @date: 2016/7/15
 * @email: huajie@le.com
 * @Copyright (c) 2016. le.com Inc. All rights reserved.
 */
public class ViewUtil {

    public static <V extends View> V findView(View view, int id) {
        return (V) view.findViewById(id);
    }

    public static void visible(View... views) {
        setVisibility(View.VISIBLE, views);
    }

    public static void inVisible(View... views) {
        setVisibility(View.INVISIBLE, views);
    }

    public static void gone(View... views) {
        setVisibility(View.GONE, views);
    }

    public static void setVisibility(int visibility, View... views) {

        for (View view : views) {
            if (view.getVisibility() != visibility) {
                view.setVisibility(visibility);
            }
        }
    }


    public static void loadWebView(WebView webView, String data) {
        webView.loadDataWithBaseURL(null, data, "text/html", "utf-8", null);
    }

    public static void enableView(View view, boolean enable) {
        view.setEnabled(enable);
    }

    public static void openBrowser(Context context, String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        context.startActivity(intent);
    }

    public static void zoomInView(final View view) {
        if (view == null) {
            return;
        }
        if (view.getVisibility() != View.VISIBLE) {
            ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1f, 0, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            scaleAnimation.setDuration(150);
//            scaleAnimation.setInterpolator(MaterialInterpolator.getInstance());
            view.clearAnimation();
            view.startAnimation(scaleAnimation);
            view.setVisibility(View.VISIBLE);
        }

    }

    public static void zoomOutView(final View view) {
        if (view == null) {
            return;
        }
        if (view.getVisibility() != View.INVISIBLE) {
            ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 0, 1f, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            scaleAnimation.setDuration(150);
//            scaleAnimation.setInterpolator(MaterialInterpolator.getInstance());
            view.clearAnimation();
            view.startAnimation(scaleAnimation);
            view.setVisibility(View.INVISIBLE);
        }
    }

    public static int getStatusBarHeight(Application context) {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {

        }
        return 0;
    }

    public static void setBuyLayoutAnimator(final View view, boolean isShowAnimal) {
        if (view == null ) {
            return;
        }
        if (!isShowAnimal) {
            view.setVisibility(View.VISIBLE);
            return;
        }
        final AnimatorSet mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playTogether(ObjectAnimator.ofFloat(view, "rotationX", 90, -15, 15, 0), ObjectAnimator.ofFloat(view, "alpha", 0.25f, 0.5f, 0.75f, 1));
        mAnimatorSet.setDuration(800);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                view.setVisibility(View.VISIBLE);
                mAnimatorSet.start();
            }
        }, 150);
    }
}
