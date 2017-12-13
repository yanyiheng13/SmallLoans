package com.virgo.financeloan.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.virgo.financeloan.R;
import com.virgo.financeloan.ui.view.CustomTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能说明： 交易记录明细
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 17-12-06
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */

public class LoanRecordDetailActivity extends BaseActivity {
    @BindView(R.id.title_view)
    CustomTitleView mTitleView;
    //借款记录单号
//    @BindView(R.id.title_view)
//    TextView mTvNum;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loanrecord_detail);
        ButterKnife.bind(this);
        mTitleView.setTitle("");
    }

}
