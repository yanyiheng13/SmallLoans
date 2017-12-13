package com.virgo.financeloan.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.virgo.financeloan.R;
import com.virgo.financeloan.util.UIUtil;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * 功能说明： 刷新头部
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/11/20
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class LoanRefreshLayout extends PtrFrameLayout {
    private PtrFrameLayout mPtrFrameLayout;
    public LoanRefreshLayout(Context context) {
        this(context, null);
    }

    public LoanRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoanRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        StoreHouseHeader header = new StoreHouseHeader(getContext()).setTextColor(getContext().getResources().getColor(R.color.blue_main));
        header.initWithString("YUECAI-JINFU");
        header.setPadding(0, UIUtil.dip2px(getContext(), 20), 0, UIUtil.dip2px(getContext(), 20));

        setHeaderView(header);
        addPtrUIHandler(header);
        setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPtrFrameLayout = frame;
               if (mListener != null) {
                   mListener.onRefreshStart(frame);
               }
            }
        });
        setResistance(1.7f);
        setRatioOfHeaderHeightToRefresh(1.2f);
        setDurationToClose(300);
        setDurationToCloseHeader(2000);
        setKeepScreenOn(false);
        setKeepHeaderWhenRefresh(true);
//        set
    }

    public OnRefreshListener mListener;
    public void setOnRefreshListener(OnRefreshListener listener) {
        mListener = listener;
    }
    public interface OnRefreshListener {
        void onRefreshStart(PtrFrameLayout frame);
    }

    public void setPtrFrameLayoutRefreshSuccess() {
        if (mPtrFrameLayout != null) {
            mPtrFrameLayout.refreshComplete();
        }
    }
}
