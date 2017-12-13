package com.virgo.financeloan.ui.fragment;

import  android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.virgo.financeloan.R;
import com.virgo.financeloan.model.responce.LoanVo;
import com.virgo.financeloan.model.responce.RepaymentWayAndAgingVo;
import com.virgo.financeloan.mvp.LoanListPresent;
import com.virgo.financeloan.mvp.contract.LoanListContract;
import com.virgo.financeloan.ui.BaseFragment;
import com.virgo.financeloan.ui.LoanDetailActivity;
import com.virgo.financeloan.ui.view.CustomTitleView;
import com.virgo.financeloan.ui.view.EmptyView;
import com.virgo.financeloan.ui.view.LoanRefreshLayout;
import com.virgo.financeloan.util.CommonUtil;
import com.virgo.financeloan.util.Span;
import com.virgo.financeloan.util.Trestle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 功能说明： 贷款列表
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-11-22
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
public class LoansFragment extends BaseFragment<LoanListPresent> implements EmptyView.OnDataLoadStatusListener, LoanListContract.View, LoanRefreshLayout.OnRefreshListener {
    @BindView(R.id.title_view)
    CustomTitleView mTitleView;
    @BindView(R.id.empty_view)
    EmptyView mEmptyView;
    @BindView(R.id.swipe_refresh)
    LoanRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private BaseQuickAdapter<LoanVo, BaseViewHolder> mAdapter;

    @Override
    public int inflateId() {
        return R.layout.fragment_loan_list;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTitleView.setLeftGone();
        mEmptyView.setNoDataTxtResId(R.string.current_no_loan);
        mEmptyView.setOnDataLoadStatusListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new BaseQuickAdapter<LoanVo, BaseViewHolder>(R.layout.view_loan_list_item) {
            @Override
            protected void convert(BaseViewHolder helper, final LoanVo item) {
                List<RepaymentWayAndAgingVo> list = item.getRepaymentWayAndAgingListCollection();
                RepaymentWayAndAgingVo agingVo = null;
                if (list != null && list.size() > 0) {
                    agingVo = list.get(0);
                }
                String maxAmount = "-1".equals(item.getSingleMaxLoanAmount()) ? "--" : CommonUtil.formatAmountByInteger(item.getSingleMaxLoanAmount());
                helper.setText(R.id.loan_list_tv_name, item.getProductClassifyName());//海易贷
                helper.setText(R.id.loan_list_tv_bank, item.getProductBaseDescribe());
                helper.setText(R.id.loan_item_max_amount_tv, maxAmount + "元");//最高金额
                helper.setText(R.id.loan_item_rate_tv, item.getRefMonthRate());
                helper.setText(R.id.loan_item_date_tv, item.getMaxAging() + "月");
                View rootView = helper.getView(R.id.loan_item_root_ll);
                rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoanDetailActivity.newIntent(LoansFragment.this.getContext(), item);
                    }
                });
            }
        };
        mRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mEmptyView.onStart();
        getPresenter().loanList("v1");
    }


    @Override
    public void onSuccessLoadList(List<LoanVo> loanVos) {
        mRefreshLayout.setPtrFrameLayoutRefreshSuccess();
        if (loanVos == null || loanVos.size() == 0) {
            mEmptyView.onEmpty();
        } else {
            mEmptyView.onSuccess();
            mAdapter.setNewData(loanVos);
        }
    }

    private CharSequence getRateText(String value, String unit) {
        Span span1 = new Span.Builder(value).build();
        Span span2 = new Span.Builder(unit).absoluteSize(getContext().getResources().getDimensionPixelSize(R.dimen.font_size_12)).build();
        List<Span> list = new ArrayList<>();
        list.add(span1);
        list.add(span2);
        return Trestle.getFormattedText(list);
    }

    @Override
    public void onFailureLoadList(String code, String msg) {
        mRefreshLayout.setPtrFrameLayoutRefreshSuccess();
        mEmptyView.onError();
    }

    //第一次数据加载失败点击重新请求
    @Override
    public void onDataLoadAgain() {
        getPresenter().loanList("v1");
    }

    //刷新数据
    @Override
    public void onRefreshStart(final PtrFrameLayout frame) {
        getPresenter().loanList("v1");
    }
}
