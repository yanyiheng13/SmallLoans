package com.virgo.financeloan.loan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.virgo.financeloan.AppApplication;
import com.virgo.financeloan.R;
import com.virgo.financeloan.model.request.RecordReqVo;
import com.virgo.financeloan.model.responce.LoanRecordDetailData;
import com.virgo.financeloan.model.responce.LoanRecordVo;
import com.virgo.financeloan.model.responce.ProtocolContentVo;
import com.virgo.financeloan.loan.model.response.ProtocolItemVo;
import com.virgo.financeloan.model.responce.UserData;
import com.virgo.financeloan.mvp.LoanRecordDetailPresenter;
import com.virgo.financeloan.mvp.contract.LoanRecordDetailContract;
import com.virgo.financeloan.ui.BaseActivity;
import com.virgo.financeloan.ui.WebViewActivity;
import com.virgo.financeloan.ui.view.ClickMoreTextView;
import com.virgo.financeloan.ui.view.CustomTitleView;
import com.virgo.financeloan.ui.view.EmptyView;
import com.virgo.financeloan.ui.view.LoadingDialog;
import com.virgo.financeloan.util.CommonUtil;
import com.virgo.financeloan.util.UniqueKey;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
     * 已还金额
     */
    @BindView(R.id.money_left_tv)
    TextView mTvAlreadyAmount;
    /**
     * 未还总金额
     */
    @BindView(R.id.money_right_tv)
    TextView mTvNoAmount;
    /**
     * 账单号
     */
    @BindView(R.id.repayment_account_order_num)
    TextView mTvOrderNum;
    /**
     * 借款金额
     */
    @BindView(R.id.repayment_amount_tv)
    TextView mTvAmount;
    /**
     * 收款帐号
     */
    @BindView(R.id.repayment_account_tv)
    TextView mTvAccount;
    /**
     * 当前第几期
     */
    @BindView(R.id.current_position_tv)
    TextView mTvCurrentPosition;
    /**
     * 总第几期
     */
    @BindView(R.id.step_tv_end)
    TextView mTvEndPosition;
    /**
     * 借款用途
     */
    @BindView(R.id.repayment_use_tv)
    TextView mTvUsing;
    /**
     * 起止时间
     */
    @BindView(R.id.repayment_time_tv)
    TextView mTvStartEndTime;
    /**
     * 借款合同
     */
    @BindView(R.id.click_more_view)
    ClickMoreTextView mMoreClickView;
    /**
     * 还款记录
     */
    @BindView(R.id.repayment_record_rl)
    RelativeLayout mRlRecord;
    /**
     * 还款计划
     */
    @BindView(R.id.repayment_plan_rl)
    RelativeLayout mRlPlan;
    /**
     * 下面的分割线  当还款已结清 需要隐藏还款计划和分割线
     */
    @BindView(R.id.view_line)
    View mViewLine;
    /**
     * 还款进度跟布局
     */
    @BindView(R.id.repayment_progress_root_ll)
    LinearLayout mLlProgressRoot;
    /**
     * 已结清父布局
     */
    @BindView(R.id.ll_amount)
    LinearLayout mLlAmount;
    /**
     * 已结清
     */
    @BindView(R.id.repayment_total_amount_tv)
    TextView mTvTotalAmount;
    /**
     * 借款信息 列表传过来的数据
     */
    private LoanRecordVo mLoanRecordVo;
    /**
     * 借款合同信息
     */
    private List<ProtocolItemVo> mListProtocol;
    /**
     * 当前点击的协议
     */
    private ProtocolItemVo mProtocolVo;

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

        mEmptyView.setOnDataLoadStatusListener(this);
        mEmptyView.onStart();
        onDataLoadAgain();//初始请求页面详细信息
    }

    /**
     * 详情数据
     *
     * @param detailData
     */
    @Override
    public void onSuccessDetail(LoanRecordDetailData detailData) {
        if (detailData == null) {
            mEmptyView.onEmpty();
            return;
        }
        mEmptyView.onSuccess();
        mTvEndPosition.setText(detailData.getTotalPeriod());
        mTvCurrentPosition.setText("还款中, 第" + detailData.getCurrentPeriod() + "期");
        mTvAlreadyAmount.setText(CommonUtil.formatAmountByInteger(detailData.getRepaidAmount()) + "元" );
        mTvNoAmount.setText(CommonUtil.formatAmountByInteger(detailData.getNoRepayAmount()) + "元");
        mTvOrderNum.setText(detailData.getBookedBillNum());
        mTvUsing.setText(detailData.getLoanPurpose());
        mTvAccount.setText(detailData.getLoanAccountDesc());
        mTvAmount.setText(CommonUtil.formatAmountByInteger(detailData.getBillAmount()) + "元");
        mTvStartEndTime.setText(detailData.getStartToEndDate());
        mListProtocol = detailData.getContractInfoList();

        if ("0".equals(detailData.getNoRepayAmount()) || TextUtils.isEmpty(detailData.getNoRepayAmount())) {
            mRlPlan.setVisibility(View.GONE);
            mViewLine.setVisibility(View.GONE);
            mLlProgressRoot.setVisibility(View.GONE);
            mLlAmount.setVisibility(View.VISIBLE);
            mTvTotalAmount.setText(CommonUtil.formatAmountByKeepTwo(detailData.getRepaidAmount()) + "元");
        } else {
            mRlPlan.setVisibility(View.VISIBLE);
            mViewLine.setVisibility(View.VISIBLE);
            mLlProgressRoot.setVisibility(View.VISIBLE);
            mLlAmount.setVisibility(View.GONE);
        }

        if (mListProtocol == null || mListProtocol.size() == 0) {
            return;
        }
        String[] proArray = new String[mListProtocol.size()];
        for (int i = 0; i < mListProtocol.size(); i++) {
            String name = mListProtocol.get(i).getContractName();
            if (TextUtils.isEmpty(name)) {
               return;
            }
            proArray[i] = "<" + name + ">";
        }
        mMoreClickView.splitCharSequence(proArray, false);
        mMoreClickView.setOnPositionClickListener(new ClickMoreTextView.OnPositionClickListener() {
            @Override
            public void onPositionClick(int position) {
                mProtocolVo = mListProtocol.get(position);
               Intent intent = PdfDownloadActivity.newIntent(LoanRecordDetailActivity.this, mProtocolVo.getContractName(), mProtocolVo.getPath(), null );
               LoanRecordDetailActivity.this.startActivity(intent);
              }
        });
    }

    @Override
    public void onFailureDetail(String code, String msg) {
        mEmptyView.onError();
    }

    /**
     * 协议内容
     *
     * @param contentVo
     */
    @Override
    public void onSuccessProtocolContent(ProtocolContentVo contentVo) {
        LoadingDialog.hide();
        //跳转到webView预览合同内容
//        if (contentVo != null && !TextUtils.isEmpty(contentVo)) {
//            AppApplication.mApplication.setWebContent(contentVo.getContent());
//            WebViewActivity.newIntent(this);
//        }
    }

    @Override
    public void onFailureProtocolContent(String code, String msg) {
        LoadingDialog.hide();
    }

    @Override
    public void onDataLoadAgain() {
        UserData userData = AppApplication.getUserData();
        RecordReqVo reqVo = new RecordReqVo();
        reqVo.setLoanApplyNo(mLoanRecordVo.getLoanApplyNo());
        reqVo.setBookedBillId(mLoanRecordVo.getBookedBillId());
        getPresenter().loadRecordDetail(UniqueKey.VERSION.V1, userData.token, reqVo);
    }

    @OnClick({R.id.repayment_plan_rl, R.id.repayment_record_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            //还款计划
            case R.id.repayment_plan_rl:
                RepaymentPlanListActivity.newIntent(this, mLoanRecordVo);
                break;
            //还款记录
            case R.id.repayment_record_rl:
                RepaymentRecordListActivity.newIntent(this, mLoanRecordVo);
                break;
        }

    }

    public static void newIntent(Context context, LoanRecordVo loanRecordVo) {
        Intent intent = new Intent(context, LoanRecordDetailActivity.class);
        intent.putExtra(TAG, loanRecordVo);
        context.startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(TAG, mLoanRecordVo);
    }
}
