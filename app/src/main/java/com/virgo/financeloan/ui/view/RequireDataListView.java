package com.virgo.financeloan.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.virgo.financeloan.R;
import com.virgo.financeloan.ui.BankFlowActivity;
import com.virgo.financeloan.ui.EnterpriseDataActivity;
import com.virgo.financeloan.ui.FamilyDataActivity;
import com.virgo.financeloan.ui.PersonDataActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

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
    }

    @OnClick({R.id.data_person_submit_tv, R.id.data_enterprise_submit_tv, R.id.data_family_submit_tv, R.id.data_bank_submit_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            //个人资料去提交
            case R.id.data_person_submit_tv:
                PersonDataActivity.newIntent(getContext());
                break;
            //企业资料
            case R.id.data_enterprise_submit_tv:
                EnterpriseDataActivity.newIntent(getContext());
                break;
            //家庭财产信息
            case R.id.data_family_submit_tv:
                FamilyDataActivity.newIntent(getContext());
                break;
            //银行流水
            case R.id.data_bank_submit_tv:
                BankFlowActivity.newIntent(getContext());
                break;
            default:
                break;
        }
    }

}
