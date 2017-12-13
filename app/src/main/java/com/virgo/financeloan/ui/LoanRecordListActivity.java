package com.virgo.financeloan.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.virgo.financeloan.AppApplication;
import com.virgo.financeloan.R;
import com.virgo.financeloan.model.request.LoanUserConfirmReqVo;
import com.virgo.financeloan.model.responce.BaseBean;
import com.virgo.financeloan.model.responce.LoanRecordVo;
import com.virgo.financeloan.mvp.LoanRecordListPresent;
import com.virgo.financeloan.mvp.contract.LoanRecordListContract;
import com.virgo.financeloan.ui.view.CustomTitleView;
import com.virgo.financeloan.ui.view.EmptyView;
import com.virgo.financeloan.ui.view.LoadingDialog;
import com.virgo.financeloan.ui.view.LoanRefreshLayout;
import com.virgo.financeloan.util.CommonUtil;
import com.virgo.financeloan.util.SharePrefrenceUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 功能说明： 订单列表
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-11-21
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
public class LoanRecordListActivity extends BaseActivity<LoanRecordListPresent> implements LoanRecordListContract.View, EmptyView.OnDataLoadStatusListener, LoanRefreshLayout.OnRefreshListener {
    @BindView(R.id.title_view)
    CustomTitleView mTitleView;
    @BindView(R.id.swipe_refresh)
    LoanRefreshLayout mLoadRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecycleView;
    @BindView(R.id.empty_view)
    EmptyView mEmptyView;

    private int mType;
    private boolean isConfirm;
    private BaseQuickAdapter<LoanRecordVo, BaseViewHolder> mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mType = getIntent().getIntExtra("type", 0);
        } else {
            mType = savedInstanceState.getInt("type");
        }
        setContentView(R.layout.activity_order_list);
        ButterKnife.bind(this);
        if (mType == 1) {
            mEmptyView.setNoDataTxtResId(R.string.current_no_loan);
        } else {
            mEmptyView.setNoDataTxtResId(R.string.current_no_loan_confirm);
        }

        mEmptyView.setOnDataLoadStatusListener(this);
        mLoadRefreshLayout.setOnRefreshListener(this);

        mRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new BaseQuickAdapter<LoanRecordVo, BaseViewHolder>(R.layout.view_item_order) {
            @Override
            protected void convert(BaseViewHolder helper, final LoanRecordVo item) {
//                LinearLayout rootView = helper.getView(R.id.oder_root_ll);//第二层布局 用于隐藏item多加了一层布局
                ImageView imageView = helper.getView(R.id.order_item_icon_img);//前面图标
                LinearLayout linearNoReturn = helper.getView(R.id.order_no_amount_ll);
                TextView tvName = helper.getView(R.id.order_item_name_tv);//name
                TextView tvStatus = helper.getView(R.id.order_item_status_tv);//状态
                TextView tvTotalAmount = helper.getView(R.id.order_total_amount_tv);//总金额
                TextView tvNoAmount = helper.getView(R.id.order_no_amount_tv);//未还金额
                TextView tvRateTxt = helper.getView(R.id.order_rate_tv);//利率
                TextView tvStartTime = helper.getView(R.id.order_start_time_tv);//借款开始日期
                TextView tvEndTime = helper.getView(R.id.order_end_time_tv);//借款开始日期
                TextView tvCancel = helper.getView(R.id.order_cancel_tv);//撤销
                TextView tvSure = helper.getView(R.id.order_sure_tv);//确定
                RelativeLayout rlSure = helper.getView(R.id.order_btn_rl);
                View viewLine = helper.getView(R.id.view_line);
                tvSure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog("确认本笔贷款", "您将确认本笔贷款，确认后将进入放宽阶段", item, true);
                    }
                });
                tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog("取消本笔贷款", "您将取消本笔贷款，是否再考虑一下呢", item, false);
                    }
                });
                if (mType == 1) {//全部
                    rlSure.setVisibility(View.GONE);
                    viewLine.setVisibility(View.GONE);
                } else if (mType == 2) {//待确认
                    rlSure.setVisibility(View.VISIBLE);
                    viewLine.setVisibility(View.VISIBLE);
                }
                tvName.setText(item.getProductBaseName());
                tvStatus.setText(item.getLoanStatus());
                tvTotalAmount.setText(CommonUtil.formatAmountByInteger(item.getLoanAmount()) + "元");
                tvRateTxt.setText(item.getMonthRate());
                tvStartTime.setText(item.getLoanApplyDate());
                tvEndTime.setText(item.getLoanEndDate());
                if (TextUtils.isEmpty(item.getNoRepaymentAmount())) {
                    linearNoReturn.setVisibility(View.GONE);
                } else {
                    linearNoReturn.setVisibility(View.VISIBLE);
                    if ("-1".equals(item.getNoRepaymentAmount())) {
                        tvNoAmount.setText("--元");
                    } else {
                        tvNoAmount.setText(CommonUtil.formatAmountByInteger(item.getNoRepaymentAmount()) + "元");
                    }
                }
            }
        };
        mRecycleView.setAdapter(mAdapter);
        mEmptyView.onStart();
        askData();
    }

    private void askData() {
        if (mType == 1) {
            getPresenter().loanRecordAllList("v1", AppApplication.getUserData().token);
        } else {
            getPresenter().loanRecordConfirmList("v1", AppApplication.getUserData().token);
        }
    }

    private void showDialog(String title, String content, final LoanRecordVo item, final boolean isConfirm) {
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoanRecordListActivity.this.isConfirm = isConfirm;
                LoadingDialog.show(LoanRecordListActivity.this, false);
                LoanUserConfirmReqVo userConfirmReqVo = new LoanUserConfirmReqVo();
                userConfirmReqVo.setConfirm(isConfirm);
                userConfirmReqVo.setLoanApplyNo(item.getLoanApplyNo());
                getPresenter().loanRecordConfirm(userConfirmReqVo, "v1", AppApplication.getUserData().getToken());
            }
        });
        builder.show();
    }

    @Override
    public void onDataLoadAgain() {
        askData();
    }

    @Override
    public void onRefreshStart(final PtrFrameLayout frame) {
        askData();
    }

    public static void newIntent(Context context, int type) {
        Intent intent = new Intent(context, LoanRecordListActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("type", mType);
    }

    @Override
    public void onSuccessConfirm(BaseBean baseBean) {
        askData();
    }

    @Override
    public void onFailureConfirm(String code, String msg) {
        LoadingDialog.hide();
        checkToken(code);
    }

    @Override
    public void onSuccessLoanRecodeList(List<LoanRecordVo> listData) {
        LoadingDialog.hide();
        mLoadRefreshLayout.setPtrFrameLayoutRefreshSuccess();
        if (listData == null || listData.size() == 0) {
            mEmptyView.onEmpty();
            return;
        }
        mEmptyView.onSuccess();
        mAdapter.setNewData(listData);
    }

    @Override
    public void onFailureLoanRecodeList(String code, String msg) {
        LoadingDialog.hide();
        mLoadRefreshLayout.setPtrFrameLayoutRefreshSuccess();
        mEmptyView.onError();
        checkToken(code);
    }

    public void checkToken(String code) {
        if (!"2002".equals(code)) {
            return;
        }
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("重新登录");
        builder.setMessage("您得登录状态已过期，为了您的账号安全，请重新登录");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharePrefrenceUtil.setString("user", "logindata", "");
                AppApplication.setUserData(null);
                LoginActivity.newIntent(LoanRecordListActivity.this);
                finish();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }
}
