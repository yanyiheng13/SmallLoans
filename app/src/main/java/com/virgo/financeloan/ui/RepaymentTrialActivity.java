package com.virgo.financeloan.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.virgo.financeloan.AppApplication;
import com.virgo.financeloan.R;
import com.virgo.financeloan.model.request.RecordReqVo;
import com.virgo.financeloan.model.request.TrialByProductNoReqVo;
import com.virgo.financeloan.model.responce.LoanRecordVo;
import com.virgo.financeloan.model.responce.RepayPlanData;
import com.virgo.financeloan.model.responce.RepayRecordData;
import com.virgo.financeloan.model.responce.TrialData;
import com.virgo.financeloan.model.responce.TrialMainPlanData;
import com.virgo.financeloan.model.responce.UserData;
import com.virgo.financeloan.mvp.RepaymentPlanPresenter;
import com.virgo.financeloan.mvp.contract.RepaymentPlanContract;
import com.virgo.financeloan.ui.view.EmptyView;
import com.virgo.financeloan.util.CommonUtil;
import com.virgo.financeloan.util.UniqueKey;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能说明：我的页面-待用户确认-用户贷款确认试算.
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/14 17:19
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */

public class RepaymentTrialActivity extends BaseActivity<RepaymentPlanPresenter> implements RepaymentPlanContract.View, EmptyView.OnDataLoadStatusListener {
    private static final String TAG = "TAG_DATA";
    @BindView(R.id.empty_view)
    EmptyView mEmptyView;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    /**
     * 借款信息 列表传过来的数据
     */
    private LoanRecordVo mLoanRecordVo;

    /**
     * 根据产品小号返回的预算信息
     */
    private TrialMainPlanData mMainPlanData;

    /**
     * 试算信息 是根据产品小号确定的
     */
    private BaseQuickAdapter<TrialData.InstallmentPlanInfo, BaseViewHolder> mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mLoanRecordVo = (LoanRecordVo) getIntent().getSerializableExtra(TAG);
        } else {
            mLoanRecordVo = (LoanRecordVo) savedInstanceState.getSerializable(TAG);
        }
        setContentView(R.layout.activity_repayment_trial);
        ButterKnife.bind(this);
        mEmptyView.setOnDataLoadStatusListener(this);
        mEmptyView.onStart();
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
//                tvPrincipal.setText(CommonUtil.formatAmountByInteger(item.getCurrentTotalAmount()));
                tvRepayments.setText(item.getRepaymentDate());
                tvRate.setText(mLoanRecordVo.getMonthRate());

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
        View viewHeadInfo = LayoutInflater.from(this).inflate(R.layout.view_info_head, null);
        TextView tvName = (TextView) viewHeadInfo.findViewById(R.id.trial_name_tv);//产品名称

        View viewHead = LayoutInflater.from(this).inflate(R.layout.view_trial_head, null);
        mAdapter.addHeaderView(viewHead);
        mRecyclerView.setAdapter(mAdapter);
        UserData userData = AppApplication.getUserData();
        TrialByProductNoReqVo noReqVo = new TrialByProductNoReqVo();
        noReqVo.setContractMoney(mLoanRecordVo.getLoanAmount());//合同金额即实际金额
        noReqVo.setLoanStartDate(mLoanRecordVo.getLoanApplyDate());//借款开始日期
        noReqVo.setPeriodCount(mLoanRecordVo.getPeriod());//借款期数
        noReqVo.setProductNo(mLoanRecordVo.getProductNo());//产品小类编号
        noReqVo.setRepaymentDay(mLoanRecordVo.getRepayDay());//还款日
        getPresenter().repaymentTrial(UniqueKey.VERSION.V1, userData.getToken(), noReqVo);

    }

    /**
     * 还款计划
     */
    @Override
    public void onSuccessRepaymentPlan(List<RepayPlanData> repayPlanData) {

    }

    @Override
    public void onFailureRepaymentPlan(String code, String msg) {

    }

    @Override
    public void onSuccessRepaymentTrial(TrialMainPlanData trialMainPlanData) {
        mMainPlanData = trialMainPlanData;
        if (trialMainPlanData == null) {
            mEmptyView.onError();
        } else {
            mEmptyView.onSuccess();
            mAdapter.setNewData(trialMainPlanData.getInstallmentPlanInfoList());
        }
    }

    @Override
    public void onFailureRepaymentTrial(String code, String msg) {
        mEmptyView.onError();
    }

    public static void newIntent(Context context, LoanRecordVo loanRecordVo) {
        Intent intent = new Intent(context, RepaymentTrialActivity.class);
        intent.putExtra(TAG, loanRecordVo);
        context.startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(TAG, mLoanRecordVo);
    }

    @Override
    public void onDataLoadAgain() {
        UserData userData = AppApplication.getUserData();
        RecordReqVo reqVo = new RecordReqVo();
        reqVo.setLoanApplyNo(mLoanRecordVo.getLoanApplyNo());
        reqVo.setBookedBillId(mLoanRecordVo.getBookedBillId());
        getPresenter().repaymentPlan(UniqueKey.VERSION.V1, userData.getToken(), reqVo);
    }

    @Override
    public void onSuccessRepaymentRecord(List<RepayRecordData> repayRecordData) {

    }

    @Override
    public void onFailureRepaymentRecord(String code, String msg) {

    }
}
