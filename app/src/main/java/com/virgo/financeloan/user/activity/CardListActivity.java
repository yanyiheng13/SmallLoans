package com.virgo.financeloan.user.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.virgo.financeloan.AppApplication;
import com.virgo.financeloan.R;
import com.virgo.financeloan.user.model.request.AddCardReqVo;
import com.virgo.financeloan.model.responce.BaseBean;
import com.virgo.financeloan.user.model.response.CardData;
import com.virgo.financeloan.user.model.response.CardVo;
import com.virgo.financeloan.model.responce.UserData;
import com.virgo.financeloan.user.presenter.CardListPresenter;
import com.virgo.financeloan.mvp.contract.LoanDetailContract;
import com.virgo.financeloan.ui.BaseActivity;
import com.virgo.financeloan.ui.view.CustomTitleView;
import com.virgo.financeloan.ui.view.EmptyView;
import com.virgo.financeloan.ui.view.LoadingDialog;
import com.virgo.financeloan.ui.view.LoanRefreshLayout;
import com.virgo.financeloan.user.presenter.contract.CardListContract;
import com.virgo.financeloan.util.BankListUtil;
import com.virgo.financeloan.util.UniqueKey;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 功能说明：银行卡列表界面
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/11 15:19
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */

public class CardListActivity extends BaseActivity<CardListPresenter> implements EmptyView.OnDataLoadStatusListener, LoanRefreshLayout.OnRefreshListener, CardListContract.View {
    @BindView(R.id.title_view)
    CustomTitleView mTitleView;
    @BindView(R.id.empty_view)
    EmptyView mEmptyView;
    @BindView(R.id.swipe_refresh)
    LoanRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    /**
     * 要删除的银行卡账户对象
     */
    private CardVo mCardVo;
    /**
     * 全局银行卡列表
     */
    private List<CardVo> mCardList;
    /**
     * 适配器
     */
    private BaseQuickAdapter<CardVo, BaseViewHolder> mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);
        ButterKnife.bind(this);
        mEmptyView.setOnDataLoadStatusListener(this);
        mRefreshLayout.setOnRefreshListener(this);
        //添加银行卡
        mTitleView.setRightText(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCardActivity.newIntent(CardListActivity.this);
            }
        }, getString(R.string.btn_add));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new BaseQuickAdapter<CardVo, BaseViewHolder>(R.layout.view_cardlist_item) {
            @Override
            protected void convert(BaseViewHolder helper, final CardVo item) {
                //长安实践监听
                ImageView imageIcon = helper.getView(R.id.card_icon);
                TextView bankName = helper.getView(R.id.card_name);
                TextView bankCategory = helper.getView(R.id.card_category);
                TextView bankNumber = helper.getView(R.id.card_number);
                RelativeLayout mRlParent = helper.getView(R.id.card_contain);


                CardVo cardVoUtil = BankListUtil.getBankObjInfo(item.getBankCode());
                if (cardVoUtil.getDrawableId() == 0) {
//                    imageIcon.setBackgroundResource(R.mipmap.icon_bank_logo_default);
                    imageIcon.setBackground(null);
                } else {
                    imageIcon.setBackgroundResource(cardVoUtil.getDrawableId());
                }
                if (!TextUtils.isEmpty(cardVoUtil.getBankAccountName())) {
                    bankName.setText(cardVoUtil.getBankAccountName());
                } else {
                    bankName.setText(item.getOpenAccountBankName());
                }
                //银行卡卡号 脱敏
                bankNumber.setText(item.getBankCardNo());
                //银行卡类型，现阶段只支持储蓄卡
                bankCategory.setText("储蓄卡");
                //银行卡背景
                Drawable d = getResources().getDrawable(R.drawable.shape_bankcard_bg);
                GradientDrawable customBackGround = (GradientDrawable) d;
                if (customBackGround != null && cardVoUtil.getCardBgColor() != 0) {
                    customBackGround.setColor(cardVoUtil.getCardBgColor());
                } else {
                    customBackGround.setColor(0xFF03B996);
                }
                mRlParent.setBackground(customBackGround);

                RelativeLayout bankContain = helper.getView(R.id.card_contain);
                bankContain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra("data", item);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }
                });
            }
        };
        View viewFooter = LayoutInflater.from(this).inflate(R.layout.view_layout_footer, null);
        mAdapter.addFooterView(viewFooter);
        mRecyclerView.setAdapter(mAdapter);
        //显示加载中,请求账户列表
        mEmptyView.onStart();
        //请求银行卡列表信息
        UserData userData = AppApplication.getUserData();
        getPresenter().accountList(UniqueKey.VERSION.V1, userData.token);
    }

    @Override
    public void onRefreshStart(PtrFrameLayout frame) {
        //请求银行卡列表信息
        UserData userData = AppApplication.getUserData();
        getPresenter().accountList(UniqueKey.VERSION.V1, userData.token);
    }

    @Override
    public void onDataLoadAgain() {
        //请求银行卡列表信息
        UserData userData = AppApplication.getUserData();
        getPresenter().accountList(UniqueKey.VERSION.V1, userData.token);
    }

    private void showDelete(final CardVo cardVo) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("删除账户");
        builder.setMessage("确定要删除次账户信息吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoadingDialog.show(CardListActivity.this, false);
                AddCardReqVo bankCardReqVo = new AddCardReqVo();
                bankCardReqVo.setBindId(cardVo.getBindId());
                getPresenter().deleteAccount(UniqueKey.VERSION.V1, AppApplication.getUserData().getToken(), bankCardReqVo);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            LoadingDialog.show(this, false);
            //请求银行卡列表信息
            UserData userData = AppApplication.getUserData();
            getPresenter().accountList(UniqueKey.VERSION.V1, userData.token);
        }
    }

    public static void newIntent(Context context, int requestCode) {
        Intent intent = new Intent(context, CardListActivity.class);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    @Override
    public void onSuccessCardList(CardData cardData) {
        LoadingDialog.hide();
        mRefreshLayout.setPtrFrameLayoutRefreshSuccess();
        if (cardData != null) {
            mCardList = cardData.getCardInfoList();
            mAdapter.setNewData(cardData.getCardInfoList());
            if (mCardList == null || mCardList.size() == 0) {
                mEmptyView.onEmpty();
            } else {
                mEmptyView.onSuccess();
            }
            return;
        }
        mEmptyView.onEmpty();
    }

    @Override
    public void onFailureCardList(String code, String msg) {
        LoadingDialog.hide();
        mRefreshLayout.setPtrFrameLayoutRefreshSuccess();
        mEmptyView.onError();
    }

    @Override
    public void onSuccessDelete(BaseBean baseBean) {
        LoadingDialog.hide();
        if (mCardList != null || mCardList.size() > 0) {
            mCardList.remove(mCardVo);
        }
        if (mCardList == null || mCardList.size() <= 0) {
            mEmptyView.onEmpty();
        } else {
            mAdapter.setNewData(mCardList);
        }
    }

    @Override
    public void onFailureDelete(String code, String msg) {
        LoadingDialog.hide();
    }
}
