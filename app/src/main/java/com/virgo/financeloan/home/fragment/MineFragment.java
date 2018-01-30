package com.virgo.financeloan.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.virgo.financeloan.AppApplication;
import com.virgo.financeloan.R;
import com.virgo.financeloan.ui.BaseFragment;
import com.virgo.financeloan.loan.activity.LoanRecordListActivity;
import com.virgo.financeloan.ui.SettingActivity;
import com.virgo.financeloan.ui.view.CircleImageView;
import com.virgo.financeloan.ui.view.CustomTitleView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 功能说明：
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-9-7
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
public class MineFragment extends BaseFragment {
    @BindView(R.id.title_view)
    CustomTitleView mTitleView;
    @BindView(R.id.user_head_img)
    CircleImageView mImageHead;
    @BindView(R.id.txt_user_name)
    TextView mTvName;

    @Override
    public int inflateId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTitleView.setLeftGone();
        mTvName.setText(AppApplication.getUserData().getUserPhone());
    }

    @OnClick({R.id.my_order_rl, R.id.my_order_sure_rl, R.id.my_setting_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_order_rl://我的订单
                LoanRecordListActivity.newIntent(getContext(), 1);
                break;
            case R.id.my_order_sure_rl://待确认
                LoanRecordListActivity.newIntent(getContext(), 2);
                break;
            case R.id.my_setting_rl://设置界面
                SettingActivity.newIntent(getContext());
                break;
            default:
                break;
        }
    }
}
