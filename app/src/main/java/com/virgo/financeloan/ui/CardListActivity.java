package com.virgo.financeloan.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.virgo.financeloan.AppApplication;
import com.virgo.financeloan.R;
import com.virgo.financeloan.model.request.AddBankCardReqVo;
import com.virgo.financeloan.model.responce.BaseBean;
import com.virgo.financeloan.model.responce.CardData;
import com.virgo.financeloan.model.responce.CardVo;
import com.virgo.financeloan.model.responce.UserData;
import com.virgo.financeloan.mvp.CardListPresenter;
import com.virgo.financeloan.mvp.contract.LoanDetailContract;
import com.virgo.financeloan.ui.view.CustomTitleView;
import com.virgo.financeloan.ui.view.EmptyView;
import com.virgo.financeloan.ui.view.LoadingDialog;
import com.virgo.financeloan.ui.view.LoanRefreshLayout;
import com.virgo.financeloan.util.UniqueKey;

import java.io.Serializable;
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

public class CardListActivity extends BaseActivity<CardListPresenter> implements EmptyView.OnDataLoadStatusListener, LoanRefreshLayout.OnRefreshListener, LoanDetailContract.ViewList {
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
        mAdapter = new BaseQuickAdapter<CardVo, BaseViewHolder>(R.layout.view_card_item) {
            @Override
            protected void convert(BaseViewHolder helper, final CardVo item) {
                helper.setText(R.id.bank_name_tv, item.getBankAccountName()); //银行卡名字
                helper.setText(R.id.bank_num_tv, item.getBankCardNo());//银行卡卡号
                helper.setText(R.id.bank_node_tv, item.getOpenAccountBankName());//开户行支行
                //长安实践监听
                helper.getView(R.id.root_ll).setOnLongClickListener(new View.OnLongClickListener() {

                    @Override
                    public boolean onLongClick(View v) {
                        mCardVo = item;
                        showDelete(item);
                        return false;
                    }
                });
                helper.getView(R.id.root_ll).setOnClickListener(new View.OnClickListener() {
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
                AddBankCardReqVo bankCardReqVo = new AddBankCardReqVo();
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
