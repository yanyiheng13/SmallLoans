package com.virgo.financeloan.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.virgo.financeloan.R;
import com.virgo.financeloan.model.responce.LoanRecordVo;
import com.virgo.financeloan.model.responce.LoanVo;
import com.virgo.financeloan.model.responce.TrialData;
import com.virgo.financeloan.model.responce.TrialMainPlanData;
import com.virgo.financeloan.util.CommonUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hulai-10 on 2017/12/20.
 */

public class PlanHeadView extends LinearLayout {
    /**
     * 名字
     */
    @BindView(R.id.order_item_name_tv)
    TextView mName;
    /**
     * 贷款
     */
    @BindView(R.id.daikuan_tv)
    TextView mTvDaiKuan;
    /**
     * 名字
     */
    @BindView(R.id.daozhang_tv)
    TextView mTvDaoZhao;
    /**
     * 还款总额
     */
    @BindView(R.id.huankuan_tv)
    TextView mTvHuanKuan;
    /**
     * 月利率
     */
    @BindView(R.id.yuelilv_tv)
    TextView mTvRate;
    /**
     * 开始日
     */
    @BindView(R.id.kaishi_tv)
    TextView mTvStartTime;
    /**
     * 到期日
     */
    @BindView(R.id.daoqi_tv)
    TextView mTvEndTime;
    /**
     * 科目容器
     */
    @BindView(R.id.contain_ll)
    LinearLayout mLlContain;


    public PlanHeadView(Context context) {
        this(context, null);
    }

    public PlanHeadView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlanHeadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_head_plan, this);
        ButterKnife.bind(this, this);

    }

    public void upData(TrialMainPlanData trialMainPlanData, LoanRecordVo mLoanRecordVo) {
        mName.setText(mLoanRecordVo.getProductBaseName());
        mTvRate.setText(mLoanRecordVo.getMonthRate());
        mTvStartTime.setText(mLoanRecordVo.getLoanApplyDate());
        mTvEndTime.setText(mLoanRecordVo.getLoanEndDate());
        mTvDaiKuan.setText(CommonUtil.formatAmountByAutomation(trialMainPlanData.getContractMoney()));
        mTvDaoZhao.setText(CommonUtil.formatAmountByAutomation(trialMainPlanData.getReceiveMoney()));
        mTvHuanKuan.setText(CommonUtil.formatAmountByAutomation(trialMainPlanData.getExpectedTotalAmount()));
        mLlContain.removeAllViews();
        List<TrialData.RepaymentPlanInfo> planInfoList = trialMainPlanData.getAdvancePaymentPlanInfoList();
        if (planInfoList != null) {
            for (int i = 0; i < planInfoList.size(); i++) {
                TrialData.RepaymentPlanInfo planInfo = planInfoList.get(i);
                if (planInfo == null) {
                    continue;
                }
                View view = LayoutInflater.from(getContext()).inflate(R.layout.view_subject_item, null);
                TextView tvSubject = (TextView) view.findViewById(R.id.subject_tv);
                TextView tvSubjectMoney = (TextView) view.findViewById(R.id.subject_money);
                tvSubject.setText(planInfo.getRepaymentAccountName());
                tvSubjectMoney.setText(CommonUtil.formatAmountByAutomation(planInfo.getRepaymentAmount()));
                mLlContain.addView(view);
            }
        }

    }

    public void upData(TrialData trialData, LoanVo mLoanVo) {
        mName.setText(mLoanVo.getProductBaseName());
        mTvRate.setText(mLoanVo.getRefMonthRate());
        mTvStartTime.setText("2017-12-20");
        mTvEndTime.setText(trialData.getLoanMaturityDate());
        mTvDaiKuan.setText(CommonUtil.formatAmountByAutomation(trialData.getContractMoney()));
        mTvDaoZhao.setText(CommonUtil.formatAmountByAutomation(trialData.getReceiveMoney()));
        mTvHuanKuan.setText(CommonUtil.formatAmountByAutomation(trialData.getExpectedTotalAmount()));
        mLlContain.removeAllViews();
        List<TrialData.RepaymentPlanInfo> planInfoList = trialData.getAdvancePaymentPlanInfoList();
        if (planInfoList != null) {
            for (int i = 0; i < planInfoList.size(); i++) {
                TrialData.RepaymentPlanInfo planInfo = planInfoList.get(i);
                if (planInfo == null) {
                    continue;
                }
                View view = LayoutInflater.from(getContext()).inflate(R.layout.view_subject_item, null);
                TextView tvSubject = (TextView) view.findViewById(R.id.subject_tv);
                TextView tvSubjectMoney = (TextView) view.findViewById(R.id.subject_money);
                tvSubject.setText(planInfo.getRepaymentAccountName());
                tvSubjectMoney.setText(CommonUtil.formatAmountByAutomation(planInfo.getRepaymentAmount()));
                mLlContain.addView(view);
            }
        }

    }

}
