package com.virgo.financeloan.home.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.virgo.financeloan.AppApplication;
import com.virgo.financeloan.R;
import com.virgo.financeloan.model.responce.LoanUsingVo;
import com.virgo.financeloan.model.responce.LoanVo;
import com.virgo.financeloan.model.responce.RepaymentWayAndAgingVo;
import com.virgo.financeloan.mvp.LoanListPresent;
import com.virgo.financeloan.mvp.contract.LoanListContract;
import com.virgo.financeloan.ui.BaseFragment;
import com.virgo.financeloan.loan.activity.LoanDetailActivity;
import com.virgo.financeloan.ui.LoginActivity;
import com.virgo.financeloan.ui.view.CustomTitleView;
import com.virgo.financeloan.ui.view.EmptyView;
import com.virgo.financeloan.ui.view.LoanRefreshLayout;
import com.virgo.financeloan.util.UIUtil;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 功能说明： 首页
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-8-24
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
public class HomeFragment extends BaseFragment<LoanListPresent> implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, EmptyView.OnDataLoadStatusListener, LoanListContract.View, LoanRefreshLayout.OnRefreshListener {

    @BindView(R.id.title_view)
    CustomTitleView mTitleView;
    @BindView(R.id.empty_view)
    EmptyView mEmptyView;
    @BindView(R.id.swipe_refresh)
    LoanRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    SliderLayout mSliderLayout;

    private BaseQuickAdapter<LoanVo, BaseViewHolder> mAdapter;

    @Override
    public int inflateId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTitleView.setLeftGone();
        HashMap<String, Integer> url_maps = new HashMap<>();
        url_maps.put("Big Bang Theory", R.mipmap.banner01);
        url_maps.put("House of Cards", R.mipmap.banner_02);

        mSliderLayout = new SliderLayout(getContext());
        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView.description(name).image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);
            mSliderLayout.addSlider(textSliderView);

        }
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        mSliderLayout.setDuration(4000);
        mSliderLayout.addOnPageChangeListener(this);
        mSliderLayout.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, UIUtil.dip2px(getContext(), 200)));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new BaseQuickAdapter<LoanVo, BaseViewHolder>(R.layout.view_home_list_litem) {
            @Override
            protected void convert(BaseViewHolder helper, final LoanVo item) {
                helper.setText(R.id.product_introduce, item.getIntroduce());//产品介绍
                helper.setText(R.id.loan_list_tv_name, item.getProductClassifyName());//海易贷
                helper.setText(R.id.loan_list_tv_bank, item.getProductBaseDescribe());
                TextView tvBtn = helper.getView(R.id.apply_loan_tv);
                View rootView = helper.getView(R.id.loan_item_root_ll);
                tvBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!AppApplication.isLogin()) {
                            LoginActivity.newIntent(getActivity(), LoginActivity.TAG_LOGIN);
                            return;
                        }
                        List<RepaymentWayAndAgingVo> list = item.getRepaymentWayAndAgingListCollection();
                        final List<LoanUsingVo> listData = item.getLoanPurposeInfoList();
                        RepaymentWayAndAgingVo agingVo = null;
                        if (list != null && list.size() > 0) {
                            agingVo = list.get(0);
                        }
                        final RepaymentWayAndAgingVo finalAgingVo = agingVo;
                        if (listData == null || listData.size() == 0 || finalAgingVo == null || finalAgingVo.getAgingInfoList() == null || finalAgingVo.getAgingInfoList().size() <= 0) {
                            showDialog();
                            return;
                        }
                        LoanDetailActivity.newIntent(HomeFragment.this.getContext(), item);
//                        WebViewActivity.newIntent(getContext());
                    }
                });
            }
        };
        mRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mEmptyView.onStart();

        mAdapter.addHeaderView(mSliderLayout);
        getPresenter().loanList("v1");
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

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


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void showDialog() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
        builder.setTitle("提示");
        builder.setMessage("此产品为非正常产品,请查看其他产品");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }
}
