package com.virgo.financeloan.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.virgo.financeloan.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能说明：上传图片分组头
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/12 10:31
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
public class GroupView extends LinearLayout {
    /**
     * 名字
     */
    @BindView(R.id.group_name_tv)
    TextView mTvName;
    /**
     * 必填点
     */
    @BindView(R.id.group_name_img)
    ImageView mImgDot;
    /**
     * 上传按钮
     */
    @BindView(R.id.group_up_tv)
    TextView mTvUp;
    /**
     * 箭头
     */
    @BindView(R.id.group_arrow_img)
    ImageView mImgArrow;
    /**
     * 上传图片内容容器
     */
    @BindView(R.id.up_pic_content)
    RelativeLayout mRlContent;

    private View mContentView;

    public GroupView(Context context) {
        this(context, null);
    }

    public GroupView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_group, this);
        setOrientation(LinearLayout.VERTICAL);
        ButterKnife.bind(this, this);
    }

    @OnClick({R.id.group_up_tv, R.id.root_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.group_up_tv:
                if (listener != null) {
                    listener.onUpClick(this);
                }
                break;
            case R.id.root_rl:
//                if (listener != null) {
//                    listener.onGroupClick(this);
//                }
                if (mContentView == null) {
                    break;
                }
                if (mImgArrow.isSelected()) {
                    mContentView.setVisibility(View.GONE);
                    mImgArrow.setSelected(false);
                } else {
                    mContentView.setVisibility(View.VISIBLE);
                    mImgArrow.setSelected(true);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 设置上传内容View
     *
     * @param view
     */
    public GroupView setCustomView(View view, boolean isVisible) {
        if (view != null) {
            mContentView = view;
            mRlContent.addView(view, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
            if (isVisible) {
                mContentView.setVisibility(View.VISIBLE);
                mImgArrow.setSelected(true);
            } else {
                mContentView.setVisibility(View.GONE);
                mImgArrow.setSelected(false);
            }
        }
        return this;
    }

    /**
     * 设置组名字
     *
     * @param resourceId
     * @return
     */
    public GroupView setGroupName(int resourceId) {
        mTvName.setText(resourceId);
        return this;
    }

    /**
     * 是否是必须上传项
     *
     * @param isRequire
     * @return
     */
    public GroupView isRequireDot(boolean isRequire) {
        if (isRequire) {
            mImgDot.setVisibility(View.VISIBLE);
        } else {
            mImgDot.setVisibility(View.GONE);
        }
        return this;
    }

    private OnUpViewGroupListener listener;

    public interface OnUpViewGroupListener {
        void onUpClick(View view);

        void onGroupClick(View view);
    }

    public void setOnUpViewGroupListener(OnUpViewGroupListener listener) {
        this.listener = listener;
    }
}
