package com.virgo.financeloan.user.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.virgo.financeloan.AppApplication;
import com.virgo.financeloan.R;
import com.virgo.financeloan.user.model.request.AddCardReqVo;
import com.virgo.financeloan.model.responce.BaseBean;
import com.virgo.financeloan.user.model.response.CardVo;
import com.virgo.financeloan.model.responce.UserData;
import com.virgo.financeloan.user.presenter.AddCardPresenter;
import com.virgo.financeloan.user.presenter.contract.AddCardContract;
import com.virgo.financeloan.ui.BaseActivity;
import com.virgo.financeloan.ui.view.LoadingDialog;
import com.virgo.financeloan.util.UniqueKey;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能说明：添加银行卡界面
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/11 14:48
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */

public class AddCardActivity extends BaseActivity<AddCardPresenter> implements AddCardContract.View {
    private static final String SAVE_TAG = "TAG";
    /**
     * 申请银行账号
     */
    @BindView(R.id.bank_account_num_et)
    EditText mEtBankNum;
    /**
     * 申请账户名
     */
    @BindView(R.id.bank_account_name_et)
    EditText mEtAccountName;
    /**
     * 申请开户支行
     */
    @BindView(R.id.bank_account_bank_et)
    EditText mEtNodeBank;
    /**
     * 银行选择
     */
    @BindView(R.id.bank_account_select_tv)
    TextView mTvSelect;
    /**
     * 当前选择的银行卡
     */
    private CardVo mCardVo;

    public int mTab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mTab = getIntent().getIntExtra(SAVE_TAG, 0);
        } else {
            mTab = savedInstanceState.getInt(SAVE_TAG);
            mCardVo = (CardVo) savedInstanceState.getSerializable("cardVo");
        }
        setContentView(R.layout.activity_add_card);
        ButterKnife.bind(this);
        if (mCardVo != null) {
            mTvSelect.setText(mCardVo.getBankName());
        }
    }

    @OnClick({R.id.login_tv, R.id.bank_account_select_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_tv:
                String bankNum = mEtBankNum.getText().toString();
                String accountName = mEtAccountName.getText().toString();
                String nodeBank = mEtNodeBank.getText().toString();
                if (mCardVo == null || TextUtils.isEmpty(bankNum) || TextUtils.isEmpty(accountName) || TextUtils.isEmpty(nodeBank)) {
                    return;
                }
                LoadingDialog.show(this, false);
                UserData userData = AppApplication.getUserData();
                //请求参数设置
                AddCardReqVo addBankCardReqVo = new AddCardReqVo();
                addBankCardReqVo.setBankAccountName(accountName);
                addBankCardReqVo.setBankCardNo(bankNum);
                addBankCardReqVo.setOpenAccountBankName(nodeBank);
                addBankCardReqVo.setBankCode(mCardVo.getBankCode());
                addBankCardReqVo.setBankName(mCardVo.getBankName());
                getPresenter().addAccount(UniqueKey.VERSION.V1, userData.token, addBankCardReqVo);
                break;
            case R.id.bank_account_select_tv:
                //选择所属银行
                CardSelectListActivity.newIntent(this);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            mCardVo = (CardVo) data.getSerializableExtra("data");
            if (mCardVo != null) {
                mTvSelect.setText(mCardVo.getBankName());
            }
        }
    }

    public static void newIntent(Context context) {
        Intent intent = new Intent(context, AddCardActivity.class);
        ((Activity) context).startActivityForResult(intent, 11);
    }

    public static void newIntent(Context context, int type, int requestCode) {
        Intent intent = new Intent(context, AddCardActivity.class);
        intent.putExtra(SAVE_TAG, type);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    @Override
    public void onSuccess(BaseBean baseBean) {
        LoadingDialog.hide();
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void onFailure(String code, String msg) {
        LoadingDialog.hide();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("cardVo", mCardVo);
        outState.putInt(SAVE_TAG, mTab);
    }
}
