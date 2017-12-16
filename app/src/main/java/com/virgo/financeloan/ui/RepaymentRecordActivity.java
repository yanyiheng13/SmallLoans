package com.virgo.financeloan.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.virgo.financeloan.R;
import com.virgo.financeloan.model.responce.LoanRecordVo;

/**
 * 功能说明：还款记录界面
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/14 17:20
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */

public class RepaymentRecordActivity extends BaseActivity {
    private static final String TAG = "TAG_DATA";

    /**
     *  借款信息 列表传过来的数据
     */
    private LoanRecordVo mLoanRecordVo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mLoanRecordVo = (LoanRecordVo) getIntent().getSerializableExtra(TAG);
        } else {
            mLoanRecordVo = (LoanRecordVo) savedInstanceState.getSerializable(TAG);
        }
        setContentView(R.layout.activity_repayment_record);
    }


    public static void newIntent(Context context, LoanRecordVo loanRecordVo) {
        Intent intent = new Intent(context, RepaymentRecordActivity.class);
        intent.putExtra(TAG, loanRecordVo);
        context.startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(TAG, mLoanRecordVo);
    }
}
