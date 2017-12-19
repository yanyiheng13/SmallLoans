package com.virgo.financeloan.ui.view;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.virgo.financeloan.R;
import com.virgo.financeloan.model.responce.CardVo;
import com.virgo.financeloan.ui.AddCardActivity;
import com.virgo.financeloan.ui.CardListActivity;
import com.virgo.financeloan.util.ViewUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.Getter;

/**
 * 功能说明：放款账号信息
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/11 10:16
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */

public class LendingAccountView extends LinearLayout {
    public static final int REQUEST_CODE_ADD_CODE = 20;
    public static final int REQUEST_CODE_LIST_CODE = 21;

    @BindView(R.id.bank_account_num_ll)
    LinearLayout mLlAccountNum;//银行卡账号父容器
    @BindView(R.id.bank_account_name_ll)
    LinearLayout mLlAccountName;//账户名父容器
    @BindView(R.id.bank_account_bank_ll)
    LinearLayout mLlAccountBranch;//开户行支行父容器
    /**
     * 申请银行账号
     */
    @Getter
    @BindView(R.id.bank_account_num_et)
    TextView mTvBankNum;
    /**
     * 申请账户名
     */
    @Getter
    @BindView(R.id.bank_account_name_et)
    TextView mTvAccountName;
    /**
     * 申请开户支行
     */
    @Getter
    @BindView(R.id.bank_account_bank_et)
    TextView mTvNodeBank;
    /**
     * 添加账号
     */
    @BindView(R.id.lending_add_account_tv)
    TextView mTvAddAccount;
    /**
     * 更换账号
     */
    @BindView(R.id.lending_change_account_tv)
    TextView mTvChangeAccount;

    private List<CardVo> mCardVoList;


    public LendingAccountView(Context context) {
        this(context, null);
    }

    public LendingAccountView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LendingAccountView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_layout_lending, this);
        ButterKnife.bind(this, this);
        setData(null);//初始化隐藏银行卡状态  如果有银行卡信息再显示
        setLine(mTvAddAccount);
        setLine(mTvChangeAccount);
    }

    /**
     * 传递银行账号列表
     *
     * @param cardVoList
     */
    public void setData(List<CardVo> cardVoList) {
        mCardVoList = cardVoList;
        if (mCardVoList == null || mCardVoList.size() == 0) {
            ViewUtil.gone(mLlAccountNum, mLlAccountName, mLlAccountBranch, mTvChangeAccount);
        } else {
            ViewUtil.visible(mLlAccountNum, mLlAccountName, mLlAccountBranch, mTvChangeAccount);
            CardVo cardVo = cardVoList.get(0);
            mTvBankNum.setText(cardVo.getBankCardNo());
            mTvAccountName.setText(cardVo.getBankAccountName());
            mTvNodeBank.setText(cardVo.getOpenAccountBankName());
        }
    }

    @OnClick({R.id.lending_add_account_tv, R.id.lending_change_account_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lending_add_account_tv://添加账户按钮
                AddCardActivity.newIntent(getContext(), 1, REQUEST_CODE_ADD_CODE);
                break;
            case R.id.lending_change_account_tv://更换账户按钮
                CardListActivity.newIntent(getContext(), REQUEST_CODE_LIST_CODE);
                break;
            default:
                break;
        }
    }

    private void setLine(TextView textView) {
        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        textView.getPaint().setAntiAlias(true);//抗锯齿
    }
}
