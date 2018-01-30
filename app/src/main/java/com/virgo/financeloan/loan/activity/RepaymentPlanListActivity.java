package com.virgo.financeloan.loan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
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
 * @author YiHeng Yan
 * @Description  还款记录列表
 * @Email yanyiheng86@163.com
 * @date 2017/12/16 21:23
 */

public class RepaymentPlanListActivity extends BaseActivity<RepaymentPlanPresenter> implements EmptyView.OnDataLoadStatusListener, RepaymentPlanContract.View {
    private static final String TAG = "tag_data";
    @BindView(R.id.title_view)
    CustomTitleView mTitleView;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.empty_view)
    EmptyView mEmptyView;

    private BaseQuickAdapter<RepayPlanData, BaseViewHolder> mAdapter;

    /**
     * 借款信息 列表传过来的数据
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
        ButterKnife.bind(this);
        mTitleView.setTitle("还款计划");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new BaseQuickAdapter<RepayPlanData, BaseViewHolder>(R.layout.view_item_plan) {
            @Override
            protected void convert(BaseViewHolder helper, final RepayPlanData item) {
                TextView mTvTime = helper.getView(R.id.plan_time_tv);//time
                TextView mTvPeriod = helper.getView(R.id.plan_period_tv);
                TextView mTvStatus = helper.getView(R.id.plan_status_tv);
                TextView mTvRepayment = helper.getView(R.id.order_total_amount_tv);//本次还款金额
                TextView mTvBenJin = helper.getView(R.id.order_benjin_amount_tv);//本金
                TextView mTvRate = helper.getView(R.id.order_rate_amount_tv);//利息
                TextView mTvFaRate = helper.getView(R.id.order_fa_rate_amount_tv);//罚息
                LinearLayout mLlFa = helper.getView(R.id.order_fa_amount_ll);//罚息父布局

                mTvTime.setText(item.getRepaymentDate());
                mLlFa.setVisibility(View.GONE);
                mTvRepayment.setText(CommonUtil.formatAmountByKeepTwo(item.getTotalAmount()) );
                mTvPeriod.setText("第" + item.getPeriod() + "期");
                mTvStatus.setText(item.getStatus());
                List<RepayRecordVo> listData = item.getSubjectList();
                if (listData != null && listData.size() >= 2) {
                    RepayRecordVo repayRecordVo = listData.get(0);
                    mTvBenJin.setText(CommonUtil.formatAmountByKeepTwo(repayRecordVo.getSubjectAmount()) + "元");
                    RepayRecordVo repayRecordVo1 = listData.get(1);
                    mTvRate.setText(CommonUtil.formatAmountByKeepTwo(repayRecordVo1.getSubjectAmount())+ "元");
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
        getPresenter().repaymentPlan(UniqueKey.VERSION.V1, userData.token, reqVo);
    }

    @Override
    public void onSuccessRepaymentPlan(List<RepayPlanData> repayPlanData) {
        if (repayPlanData == null || repayPlanData.size() == 0) {
            mEmptyView.onEmpty();
            return;
        }
        mEmptyView.onSuccess();
        mAdapter.setNewData(repayPlanData);
    }

    @Override
    public void onFailureRepaymentPlan(String code, String msg) {
        checkToken(code);
    }

    public static void newIntent(Context context, LoanRecordVo loanRecordVo) {
        Intent intent = new Intent(context, RepaymentPlanListActivity.class);
        intent.putExtra(TAG, loanRecordVo);
        context.startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(TAG, mLoanRecordVo);
    }

    /**
     * 以下四个方法在这不用
     */
    @Override
    public void onSuccessRepaymentRecord(List<RepayRecordData> repayRecordData) {

    }

    @Override
    public void onFailureRepaymentRecord(String code, String msg) {

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
