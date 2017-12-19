package com.virgo.financeloan.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.virgo.financeloan.R;
import com.virgo.financeloan.model.request.TrialReqVo;
import com.virgo.financeloan.model.responce.LoanVo;
import com.virgo.financeloan.model.responce.TrialData;
import com.virgo.financeloan.mvp.TrialPresent;
import com.virgo.financeloan.mvp.contract.TrialContract;
import com.virgo.financeloan.ui.view.CustomTitleView;
import com.virgo.financeloan.ui.view.EmptyView;
import com.virgo.financeloan.ui.view.PlanHeadView;
import com.virgo.financeloan.util.CommonUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能说明：试算列表
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-11-22 上午12:27
 * @Copyright (c) 2017. Inc. All rights reserved.
 */
public class TrialListActivity extends BaseActivity<TrialPresent> implements TrialContract.View, EmptyView.OnDataLoadStatusListener {
    @BindView(R.id.title_view)
    CustomTitleView mTitleView;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.empty_view)
    EmptyView mEmptyView;
    private TrialReqVo mTrialReqVo;
    private LoanVo mLoanVo;
    /**
     * 头部View
     */
    private PlanHeadView mPlanView;

    private BaseQuickAdapter<TrialData.InstallmentPlanInfo, BaseViewHolder> mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mTrialReqVo = (TrialReqVo) getIntent().getSerializableExtra("trialReqVo");
            mLoanVo = (LoanVo) getIntent().getSerializableExtra("mLoanVo");
        } else {
            mTrialReqVo = (TrialReqVo) savedInstanceState.getSerializable("trialReqVo");
            mLoanVo = (LoanVo) savedInstanceState.getSerializable("mLoanVo");
        }
        setContentView(R.layout.activity_trial_list);
        ButterKnife.bind(this);
        mEmptyView.setOnDataLoadStatusListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BaseQuickAdapter<TrialData.InstallmentPlanInfo, BaseViewHolder>(R.layout.view_item_trial) {
            @Override
            protected void convert(BaseViewHolder helper, TrialData.InstallmentPlanInfo item) {
                TextView tvCount = helper.getView(R.id.trial_count_tv);//期数
                TextView tvPrincipal = helper.getView(R.id.principal_amount_tv);//本金
                TextView tvRate = helper.getView(R.id.rate_tv);//利率
                TextView tvInterest = helper.getView(R.id.interest_amount_tv);//利息
                TextView tvRepayments = helper.getView(R.id.repayments_time_tv);//还款时间

                tvCount.setText(item.getPeriod());
                tvRate.setText(CommonUtil.formatAmountByInteger(item.getCurrentTotalAmount()));
                tvRepayments.setText(item.getRepaymentDate());

                List<TrialData.RepaymentPlanInfo> repaymentPlanInfos = item.getRepaymentPlanInfoList();
                if (repaymentPlanInfos != null && repaymentPlanInfos.size() >= 2) {
                    TrialData.RepaymentPlanInfo repaymentPlanInfo1 = repaymentPlanInfos.get(0);
                    TrialData.RepaymentPlanInfo repaymentPlanInfo2 = repaymentPlanInfos.get(1);
                    if (repaymentPlanInfo1 != null) {
                        if ("PRI".equals(repaymentPlanInfo1.getRepaymentAccount())) {
                            tvPrincipal.setText(CommonUtil.formatAmountByKeepTwo(repaymentPlanInfo1.getRepaymentAmount()));
                        } else if ("INT".equals(repaymentPlanInfo1.getRepaymentAccount())) {
                            tvInterest.setText(CommonUtil.formatAmountByKeepTwo(repaymentPlanInfo1.getRepaymentAmount()));
                        }
                    }
                    if (repaymentPlanInfo2 != null) {
                        if ("PRI".equals(repaymentPlanInfo2.getRepaymentAccount())) {
                            tvPrincipal.setText(CommonUtil.formatAmountByKeepTwo(repaymentPlanInfo2.getRepaymentAmount()));
                        } else if ("INT".equals(repaymentPlanInfo2.getRepaymentAccount())) {
                            tvInterest.setText(CommonUtil.formatAmountByKeepTwo(repaymentPlanInfo2.getRepaymentAmount()));
                        }
                    }
                }
            }
        };
        mPlanView = new PlanHeadView(this);
        mAdapter.addHeaderView(mPlanView);

        View viewHead = LayoutInflater.from(this).inflate(R.layout.view_trial_head, null);
        mAdapter.addHeaderView(viewHead);

        mRecyclerView.setAdapter(mAdapter);
        mEmptyView.onStart();
        getPresenter().trialList(mTrialReqVo, "v1");
    }

    public static void newIntent(Context context, TrialReqVo trialReqVo, LoanVo mLoanVo) {
        Intent intent = new Intent(context, TrialListActivity.class);
        intent.putExtra("trialReqVo", trialReqVo);
        intent.putExtra("mLoanVo", mLoanVo);
        context.startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("mLoanVo", mLoanVo);
        outState.putSerializable("trialReqVo", mTrialReqVo);
    }

    @Override
    public void onSuccessTrail(TrialData trialData) {
        mEmptyView.onSuccess();
        mAdapter.setNewData(trialData.getInstallmentPlanInfoList());
        mPlanView.upData(trialData, mLoanVo);
    }

    @Override
    public void onFailureTrial(String code, String msg) {
        mEmptyView.onError();
    }

    @Override
    public void onDataLoadAgain() {
        getPresenter().trialList(mTrialReqVo, "v1");
    }
}
