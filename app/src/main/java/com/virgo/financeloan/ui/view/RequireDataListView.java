package com.virgo.financeloan.ui.view;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.virgo.financeloan.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.Getter;
import lombok.Setter;

/**
 * 功能说明：需要提交的资料
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/11 16:08
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */

public class RequireDataListView extends LinearLayout {
    @Setter
    @Getter
    private String orderNum;
    @BindView(R.id.data_person_submit_tv)
    TextView mTv1;
    @BindView(R.id.data_enterprise_submit_tv)
    TextView mTv2;
    @BindView(R.id.data_family_submit_tv)
    TextView mTv3;
    @BindView(R.id.data_bank_submit_tv)
    TextView mTv4;

    public RequireDataListView(Context context) {
        this(context, null);
    }

    public RequireDataListView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RequireDataListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_require_data, this);
        ButterKnife.bind(this);
        setLine(mTv1);
        setLine(mTv2);
        setLine(mTv3);
        setLine(mTv4);
    }

    @OnClick({R.id.data_person_submit_tv, R.id.data_enterprise_submit_tv, R.id.data_family_submit_tv, R.id.data_bank_submit_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            //个人资料去提交
            case R.id.data_person_submit_tv:
                if (listener != null) {
                    listener.onTabClick(0);
                }
                break;
            //企业资料
            case R.id.data_enterprise_submit_tv:
                if (listener != null) {
                    listener.onTabClick(1);
                }
                break;
            //家庭财产信息
            case R.id.data_family_submit_tv:
                if (listener != null) {
                    listener.onTabClick(2);
                }
                break;
            //银行流水
            case R.id.data_bank_submit_tv:
                if (listener != null) {
                    listener.onTabClick(3);
                }
                break;
            default:
                break;
        }
    }

    private void setLine(TextView textView) {
        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        textView.getPaint().setAntiAlias(true);//抗锯齿
    }

    private OnTabClickListener listener;

    public interface OnTabClickListener {
        void onTabClick(int tab);
    }

    public void setOnTabClickListener(OnTabClickListener listener) {
        this.listener = listener;
    }


}
