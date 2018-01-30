package com.virgo.financeloan.loan.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.virgo.financeloan.AppApplication;
import com.virgo.financeloan.R;
import com.virgo.financeloan.loan.model.request.ContractFillReqVo;
import com.virgo.financeloan.loan.model.request.ContractListReqVo;
import com.virgo.financeloan.loan.mvp.ProductTrialPresenter;
import com.virgo.financeloan.loan.mvp.contract.ProductTrialContract;
import com.virgo.financeloan.model.request.LoanUserConfirmReqVo;
import com.virgo.financeloan.model.request.ProtocolContentReqVo;
import com.virgo.financeloan.model.request.TrialByProductNoReqVo;
import com.virgo.financeloan.model.responce.BaseBean;
import com.virgo.financeloan.model.responce.LoanRecordVo;
import com.virgo.financeloan.model.responce.ProtocolContentVo;
import com.virgo.financeloan.loan.model.response.ProtocolItemVo;
import com.virgo.financeloan.model.responce.TrialData;
import com.virgo.financeloan.model.responce.UserData;
import com.virgo.financeloan.ui.BaseActivity;
import com.virgo.financeloan.ui.WebViewActivity;
import com.virgo.financeloan.ui.view.ClickMoreTextView;
import com.virgo.financeloan.ui.view.EmptyView;
import com.virgo.financeloan.ui.view.LoadingDialog;
import com.virgo.financeloan.ui.view.PlanHeadView;
import com.virgo.financeloan.util.CommonUtil;
import com.virgo.financeloan.util.UniqueKey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能说明：我的页面-待用户确认-用户贷款确认试算.
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/14 17:19
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */

public class RepaymentTrialActivity extends BaseActivity<ProductTrialPresenter> implements ProductTrialContract.View, EmptyView.OnDataLoadStatusListener {
    private static final String TAG = "TAG_DATA";
    @BindView(R.id.empty_view)
    EmptyView mEmptyView;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    /**
     * 更多协议
     */
    ClickMoreTextView mClickMoreView;
    /**
     *
     */
    CheckBox mCheckBox;

    /**
     * 借款信息 列表传过来的数据
     */
    private LoanRecordVo mLoanRecordVo;

    /**
     * 根据产品小号返回的预算信息
     */
    private TrialData mMainPlanData;

    /**
     * 试算信息 是根据产品小号确定的
     */
    private BaseQuickAdapter<TrialData.InstallmentPlanInfo, BaseViewHolder> mAdapter;
    /**
     * 头部View
     */
    private PlanHeadView mPlanView;

    /**
     * 协议列表
     */
    private List<ProtocolItemVo> mProtocolVoList;

    /**
     * 当前点击协议
     */
    private ProtocolItemVo mProtocolVo;
    /**
     * 这个保留字段  用于区分确认和取消的请求成功后的细分操作
     */
    private boolean isConfirm;

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
                TextView tvAmount = helper.getView(R.id.rate_tv);//还款额
                TextView tvInterest = helper.getView(R.id.interest_amount_tv);//利息
                TextView tvRepayments = helper.getView(R.id.repayments_time_tv);//还款时间

                tvCount.setText(item.getPeriod());
                tvRepayments.setText(item.getRepaymentDate());
                tvAmount.setText(CommonUtil.formatAmountByKeepTwo(item.getCurrentTotalAmount()));

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
        //明细信息
        mPlanView = new PlanHeadView(this);
        mAdapter.addHeaderView(mPlanView);

        //试算还款计划头部View
        View viewHead = LayoutInflater.from(this).inflate(R.layout.view_trial_head, null);
        mAdapter.addHeaderView(viewHead);
        //用于占位
        View viewFooter = LayoutInflater.from(this).inflate(R.layout.layout_footer, null);
        mClickMoreView = (ClickMoreTextView) viewFooter.findViewById(R.id.click_more_view);
        LinearLayout linearLayout = (LinearLayout) viewFooter.findViewById(R.id.ll_click_more);
        mCheckBox = (CheckBox) viewFooter.findViewById(R.id.check_box);
        View viewGap = viewFooter.findViewById(R.id.view_gap);
        viewGap.setVisibility(View.GONE);

        linearLayout.setVisibility(View.VISIBLE);
        mAdapter.addFooterView(viewFooter);
        mRecyclerView.setAdapter(mAdapter);
        askData();
    }

    private void askData() {
        UserData userData = AppApplication.getUserData();
        TrialByProductNoReqVo noReqVo = new TrialByProductNoReqVo();
        noReqVo.setContractMoney(mLoanRecordVo.getLoanAmount());//合同金额即实际金额
        noReqVo.setLoanStartDate(mLoanRecordVo.getLoanApplyDate());//借款开始日期
        noReqVo.setPeriodCount(mLoanRecordVo.getPeriod());//借款期数
        noReqVo.setProductNo(mLoanRecordVo.getProductNo());//产品小类编号
        noReqVo.setRepaymentDay(mLoanRecordVo.getRepayDay());//还款日
        getPresenter().repaymentTrial(UniqueKey.VERSION.V1, userData.getToken(), noReqVo);

        ContractListReqVo reqVo = new ContractListReqVo();
        reqVo.setProductBaseNo(mLoanRecordVo.getProductBaseNo());
        getPresenter().protocolList(UniqueKey.VERSION.V1, AppApplication.getUserData().getToken(), reqVo);
    }

    @OnClick({R.id.tv_sure, R.id.tv_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sure:
                if (!mCheckBox.isChecked()) {
                    Toast.makeText(this, "请先阅读并同意贷款协议", Toast.LENGTH_SHORT).show();
                    break;
                }
                showDialog("确认本笔贷款", "您将确认本笔贷款，确认后将进入放款阶段", true);
                break;
            case R.id.tv_cancel:
                showDialog("取消本笔贷款", "您将取消本笔贷款，是否再考虑一下呢", false);
                break;
            default:
                break;
        }
    }

    private void showDialog(String title, String content, final boolean isConfirm) {
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RepaymentTrialActivity.this.isConfirm = isConfirm;
                LoadingDialog.show(RepaymentTrialActivity.this, false);
                LoanUserConfirmReqVo userConfirmReqVo = new LoanUserConfirmReqVo();
                userConfirmReqVo.setConfirm(isConfirm);
                userConfirmReqVo.setLoanApplyNo(mLoanRecordVo.getLoanApplyNo());
                getPresenter().loanRecordConfirm(userConfirmReqVo, "v1", AppApplication.getUserData().getToken());
            }
        });
        builder.show();
    }

    @Override
    public void onSuccessRepaymentTrial(TrialData trialData) {
        mMainPlanData = trialData;
        if (trialData == null) {
            mEmptyView.onError();
        } else {
            mEmptyView.onSuccess();
            mAdapter.setNewData(trialData.getInstallmentPlanInfoList());
        }
        mPlanView.upData(trialData, mLoanRecordVo);
    }

    @Override
    public void onFailureRepaymentTrial(String code, String msg) {
        mEmptyView.onError();
    }

    @Override
    public void onSuccessConfirm(BaseBean baseBean) {
        LoadingDialog.hide();
        finish();
    }

    @Override
    public void onFailureConfirm(String code, String msg) {
        LoadingDialog.hide();
    }

    @Override
    public void onSuccessProtocol(List<ProtocolItemVo> voList) {
        mProtocolVoList = voList;
        if (mProtocolVoList == null || mProtocolVoList.size() == 0) {
            return;
        }
        String[] proArray = new String[mProtocolVoList.size()];
        for (int i = 0; i < mProtocolVoList.size(); i++) {
            proArray[i] = "《" + mProtocolVoList.get(i).getName() + "》 ";
        }
        mClickMoreView.splitCharSequence(proArray, true);
        mClickMoreView.setOnPositionClickListener(new ClickMoreTextView.OnPositionClickListener() {
            @Override
            public void onPositionClick(int position) {
                mProtocolVo = mProtocolVoList.get(position);
                LoadingDialog.show(RepaymentTrialActivity.this, false);
                UserData userData = AppApplication.getUserData();
                ProtocolContentReqVo reqVo = new ProtocolContentReqVo();
                reqVo.setPath(mProtocolVoList.get(position).getPath());
                getPresenter().protocolContent(UniqueKey.VERSION.V1, userData.getToken(), reqVo);
            }
        });
    }

    @Override
    public void onFailureProtocol(String code, String msg) {

    }

    @Override
    public void onSuccessProtocolContent(ProtocolContentVo contentVo) {
        if (contentVo != null && !TextUtils.isEmpty(contentVo.getTemplate())) {
            AppApplication.mApplication.setWebContent(contentVo.getTemplate());
            ContractFillReqVo contractFillReqVo = new ContractFillReqVo();
            contractFillReqVo.setLoanApplyNo(mLoanRecordVo.getLoanApplyNo());
            contractFillReqVo.setCode(mProtocolVo.getCode());
            getPresenter().contractReplaceInfo(UniqueKey.VERSION.V1, AppApplication.getUserData().getToken(), contractFillReqVo);
        } else {
            LoadingDialog.hide();
        }
    }

    @Override
    public void onFailureProtocolContent(String code, String msg) {
        LoadingDialog.hide();
    }

    @Override
    public void onSuccessMap(Map<String, String> map) {
        LoadingDialog.hide();
        String content = AppApplication.mApplication.getWebContent();
        if (map != null && !TextUtils.isEmpty(content)) {
            Map<String, Object> context = new HashMap<>();
            context.put("loan", map);
            AppApplication.mApplication.setWebContent(CommonUtil.parse(content, context));
            WebViewActivity.newIntent(this);
        }
    }

    @Override
    public void onFailureMap(String code, String msg) {
        LoadingDialog.hide();
    }

    @Override
    public void onDataLoadAgain() {
        askData();
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
}
