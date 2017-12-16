package com.virgo.financeloan.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.virgo.financeloan.AppApplication;
import com.virgo.financeloan.R;
import com.virgo.financeloan.model.request.RecordReqVo;
import com.virgo.financeloan.model.responce.LoanRecordDetailData;
import com.virgo.financeloan.model.responce.LoanRecordVo;
import com.virgo.financeloan.model.responce.UserData;
import com.virgo.financeloan.mvp.LoanRecordDetailPresenter;
import com.virgo.financeloan.mvp.contract.LoanRecordDetailContract;
import com.virgo.financeloan.ui.view.CustomTitleView;
import com.virgo.financeloan.ui.view.EmptyView;
import com.virgo.financeloan.util.UniqueKey;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能说明： 交易明细
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 17-12-06
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */

public class LoanRecordDetailActivity extends BaseActivity<LoanRecordDetailPresenter> implements LoanRecordDetailContract.View, EmptyView.OnDataLoadStatusListener {
    private static final String TAG = "TAG_DATA";
    @BindView(R.id.title_view)
    CustomTitleView mTitleView;
    @BindView(R.id.empty_view)
    EmptyView mEmptyView;
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
        setContentView(R.layout.activity_loanrecord_detail);
        ButterKnife.bind(this);
//        StepsView mStepsView = (StepsView) findViewById(R.id.stepview);
//
//        mStepsView.setLabels(steps)
//                .setBarColorIndicator(getContext().getResources().getColor(R.color.material_blue_grey_800))
//                .setProgressColorIndicator(getContext().getResources().getColor(R.color.orange))
//                .setLabelColorIndicator(getContext().getResources().getColor(R.color.orange))
//                .setCompletedPosition(0)
//                .drawView();
        mEmptyView.setOnDataLoadStatusListener(this);
        mEmptyView.onStart();
//        onDataLoadAgain();//初始请求页面详细信息
    }

    public static void newIntent(Context context, LoanRecordVo loanRecordVo) {
        Intent intent = new Intent(context, LoanRecordDetailActivity.class);
        intent.putExtra(TAG, loanRecordVo);
        context.startActivity(intent);
    }

    /**
     * 详情数据
     *
     * @param detailData
     */
    @Override
    public void onSuccessDetail(LoanRecordDetailData detailData) {

    }

    @Override
    public void onFailureDetail(String code, String msg) {

    }

    @Override
    public void onDataLoadAgain() {
        UserData userData = AppApplication.getUserData();
        RecordReqVo reqVo = new RecordReqVo();
        reqVo.setLoanApplyNo(mLoanRecordVo.getLoanApplyNo());
        reqVo.setBookedBillId(mLoanRecordVo.getBookedBillId());
        getPresenter().loadRecordDetail(UniqueKey.VERSION.V1, userData.token, reqVo);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(TAG, mLoanRecordVo);
    }
}
