package com.virgo.financeloan.loan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.virgo.financeloan.AppApplication;
import com.virgo.financeloan.R;
import com.virgo.financeloan.model.request.RecordReqVo;
import com.virgo.financeloan.model.responce.BaseBean;
import com.virgo.financeloan.model.responce.LoanRecordVo;
import com.virgo.financeloan.model.responce.RepayPlanData;
import com.virgo.financeloan.model.responce.RepayRecordData;
import com.virgo.financeloan.model.responce.RepayRecordVo;
import com.virgo.financeloan.model.responce.TrialData;
import com.virgo.financeloan.model.responce.UserData;
import com.virgo.financeloan.mvp.RepaymentPlanPresenter;
import com.virgo.financeloan.mvp.contract.RepaymentPlanContract;
import com.virgo.financeloan.ui.BaseActivity;
import com.virgo.financeloan.ui.view.CustomTitleView;
import com.virgo.financeloan.ui.view.EmptyView;
import com.virgo.financeloan.util.CommonUtil;
import com.virgo.financeloan.util.UniqueKey;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能说明：还款记录界面
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/14 17:20
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */

public class RepaymentRecordListActivity extends BaseActivity<RepaymentPlanPresenter> implements EmptyView.OnDataLoadStatusListener, RepaymentPlanContract.View  {
    private static final String TAG = "tag_data";
    @BindView(R.id.title_view)
    CustomTitleView mTitleView;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.empty_view)
    EmptyView mEmptyView;

    /**
     *  借款信息 列表传过来的数据
     */
    private LoanRecordVo mLoanRecordVo;
    private BaseQuickAdapter<RepayRecordData, BaseViewHolder> mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mLoanRecordVo = (LoanRecordVo) getIntent().getSerializableExtra(TAG);
        } else {
            mLoanRecordVo = (LoanRecordVo) savedInstanceState.getSerializable(TAG);
        }
        setContentView(R.layout.activity_repayment_record);
        ButterKnife.bind(this);
        mTitleView.setTitle(R.string.title_repayment_record);
        mEmptyView.setOnDataLoadStatusListener(this);
        mEmptyView.setNoDataTxtResId(R.string.no_repayment_record);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new BaseQuickAdapter<RepayRecordData, BaseViewHolder>(R.layout.view_item_record) {
            @Override
            protected void convert(BaseViewHolder helper, final RepayRecordData item) {
                TextView mTvTime = helper.getView(R.id.plan_time_tv);//time
                TextView mTvRepayment = helper.getView(R.id.plan_amount_tv);//本次还款金额
                TextView mTvAmount1 = helper.getView(R.id.plan_amount_bottom_tv);//本次还款金额
                TextView mTvAmount2 = helper.getView(R.id.plan_amount_rate_tv);//利息
                TextView mTvAmount3 = helper.getView(R.id.plan_fa_amount_rate_tv);//罚息

                TextView mTvLeft1 = helper.getView(R.id.subject_left_tv1);
                TextView mTvLeft2 = helper.getView(R.id.subject_left_tv2);
                TextView mTvLeft3 = helper.getView(R.id.subject_left_tv3);

                RelativeLayout mRlFa = helper.getView(R.id.plan_fa_amount_rate_rl);//罚息父布局

                mTvTime.setText(item.getRepayDate());
                mTvRepayment.setText("还款金额" + CommonUtil.formatAmountByKeepTwo(item.getRepayAmount()));

                List<RepayRecordVo> list = item.getSubjectList();
                if (list != null || list.size() > 0) {
                    for (int i = 0; i < list.size(); i ++) {
                        RepayRecordVo repayRecordVo = list.get(i);
                        if (repayRecordVo == null) {continue;}
                        if (i == 0) {
                            mTvAmount1.setText(CommonUtil.formatAmountByKeepTwo(repayRecordVo.getSubjectAmount()));
                            mTvLeft1.setText(repayRecordVo.getSubjectDesc());
                        } else if (i == 1) {
                            mTvAmount2.setText(CommonUtil.formatAmountByKeepTwo(repayRecordVo.getSubjectAmount()));
                            mTvLeft2.setText(repayRecordVo.getSubjectDesc());
                        } else if (i == 2) {
                            mTvAmount3.setText(CommonUtil.formatAmountByKeepTwo(repayRecordVo.getSubjectAmount()));
                            mTvLeft3.setText(repayRecordVo.getSubjectDesc());
                        }
                    }
                }
                if (list != null) {
                    if (list.size() == 2) {
                        mRlFa.setVisibility(View.GONE);
                    } else if (list.size() > 2) {
                        mRlFa.setVisibility(View.VISIBLE);
                    }
                }

            }
        };
        mRecyclerView.setAdapter(mAdapter);
        mEmptyView.onStart();
        onDataLoadAgain();
    }

    @Override
    public void onDataLoadAgain() {
        UserData userData = AppApplication.getUserData();
        RecordReqVo reqVo = new RecordReqVo();
        reqVo.setLoanApplyNo(mLoanRecordVo.getLoanApplyNo());
        reqVo.setBookedBillId(mLoanRecordVo.getBookedBillId());
        getPresenter().repaymentRecord(UniqueKey.VERSION.V1, userData.token, reqVo);
    }

    public static void newIntent(Context context, LoanRecordVo loanRecordVo) {
        Intent intent = new Intent(context, RepaymentRecordListActivity.class);
        intent.putExtra(TAG, loanRecordVo);
        context.startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(TAG, mLoanRecordVo);
    }

    @Override
    public void onSuccessRepaymentRecord(List<RepayRecordData> repayRecordData) {
        if (repayRecordData == null || repayRecordData.size() == 0) {
            mEmptyView.onEmpty();
            return;
        }
        mAdapter.setNewData(repayRecordData);
        mEmptyView.onSuccess();
    }

    @Override
    public void onFailureRepaymentRecord(String code, String msg) {
        checkToken(code);
        mEmptyView.onError();
    }

    @Override
    public void onSuccessRepaymentPlan(List<RepayPlanData> repayPlanData) {

    }

    @Override
    public void onFailureRepaymentPlan(String code, String msg) {

    }

    @Override
    public void onSuccessRepaymentTrial(TrialData trialData) {

    }

    @Override
    public void onFailureRepaymentTrial(String code, String msg) {

    }

    @Override
    public void onSuccessConfirm(BaseBean baseBean) {

    }

    @Override
    public void onFailureConfirm(String code, String msg) {

    }


}
