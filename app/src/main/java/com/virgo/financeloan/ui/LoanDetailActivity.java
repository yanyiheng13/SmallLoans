package com.virgo.financeloan.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;
import com.virgo.financeloan.AppApplication;
import com.virgo.financeloan.R;
import com.virgo.financeloan.model.request.LoanApplyReqVo;
import com.virgo.financeloan.model.request.ProtocolContentReqVo;
import com.virgo.financeloan.model.request.ProtocolListReqVo;
import com.virgo.financeloan.model.request.TrialReqVo;
import com.virgo.financeloan.model.responce.AgingVo;
import com.virgo.financeloan.model.responce.BaseBean;
import com.virgo.financeloan.model.responce.CardData;
import com.virgo.financeloan.model.responce.CardVo;
import com.virgo.financeloan.model.responce.LoanOrderNoVo;
import com.virgo.financeloan.model.responce.LoanUsingVo;
import com.virgo.financeloan.model.responce.LoanVo;
import com.virgo.financeloan.model.responce.ProtocolContentVo;
import com.virgo.financeloan.model.responce.ProtocolVo;
import com.virgo.financeloan.model.responce.RepaymentWayAndAgingVo;
import com.virgo.financeloan.model.responce.UserData;
import com.virgo.financeloan.mvp.LoanDetailPresenter;
import com.virgo.financeloan.mvp.contract.LoanDetailContract;
import com.virgo.financeloan.ui.view.ClickMoreTextView;
import com.virgo.financeloan.ui.view.EmptyView;
import com.virgo.financeloan.ui.view.LendingAccountView;
import com.virgo.financeloan.ui.view.LoadingDialog;
import com.virgo.financeloan.util.CommonUtil;
import com.virgo.financeloan.util.SharePrefrenceUtil;
import com.virgo.financeloan.util.UniqueKey;
import com.virgo.financeloan.util.ViewUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能说明： 小贷产品详情
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/11/20
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */

public class LoanDetailActivity extends BaseActivity<LoanDetailPresenter> implements EmptyView.OnDataLoadStatusListener, LoanDetailContract.View, View.OnFocusChangeListener {

    @BindView(R.id.empty_view)
    EmptyView mEmptyView;
    @BindView(R.id.loan_detail_icon_img)
    ImageView mImgIcon;
    @BindView(R.id.loan_detail_tv_name)
    TextView mTvName;
    @BindView(R.id.loan_detail_tv_bank)
    TextView mTvBank;
    /**
     * 立即申请
     */
    @BindView(R.id.loan_detail_apply_tv)
    TextView mTvApply;
    /**
     * 参考月利率
     */
    @BindView(R.id.loan_detail_rate_tv)
    TextView mTvRate;
    /**
     * 申请金额
     */
    @BindView(R.id.loan_detail_amount_et)
    EditText mEditAmount;
    /**
     * 放款账号信息
     */
    @BindView(R.id.account_view)
    LendingAccountView mLendingView;
    /**
     * 申请条件
     */
    @BindView(R.id.apply_position_tv)
    TextView mTvApplyPosition;
    /**
     * 贷款用途
     */
    @BindView(R.id.loan_detail_use_tv)
    TextView mTvUse;
    /**
     * 借款期限
     */
    @BindView(R.id.loan_detail_time_tv)
    TextView mTvTime;
    /**
     * 更多协议
     */
    @BindView(R.id.click_more_view)
    ClickMoreTextView mClickMoreView;

    /**
     * 还款期限弹窗
     */
    private PowerMenu mPowerMenuMonth;
    /**
     * 还款用途弹窗
     */
    private PowerMenu mPowerMenuUse;
    /**
     * orderNum
     */
    private String mOrderNo;
    /**
     * 银行卡列表
     */
    private List<CardVo> mCardList;
    /**
     * 协议列表
     */
    private List<ProtocolVo> mProtocolVoList;
    /**
     * 选中银行卡信息
     */
    private CardVo mCardVo;
    /**
     * 当前点击协议
     */
    private ProtocolVo mProtocolVo;
    /**
     * 上一界面传递过来的产品对象
     */
    private LoanVo mLoanVo;

    /**
     * 用途和借款期限对象
     */
    public Map<String, AgingVo> mMapLimit = new HashMap<>();
    public Map<String, LoanUsingVo> mMapUsing = new HashMap<>();

    /**
     * 选中的分期信息
     */
    private AgingVo mAgingVo;
    /**
     * 选中的用途对象
     */
    private LoanUsingVo mSelectUsingVo;
    /**
     * 借款期限
     */
    private String[] mArrayLimit;
    /**
     * 借款用途，后端传过来的
     */
    private String[] mArrayUsing = null;
    /**
     * 还款方式
     */
    private RepaymentWayAndAgingVo mRepaymentWayAndAgingVo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mLoanVo = (LoanVo) getIntent().getSerializableExtra("loanVo");
        } else {
            mLoanVo = (LoanVo) savedInstanceState.getSerializable("loanVo");
        }
        setContentView(R.layout.activity_loan_detail);
        ButterKnife.bind(this);
        ViewUtil.setBuyLayoutAnimator(mTvApply, true);
        mEditAmount.setOnFocusChangeListener(this);
        mEmptyView.setNoDataTxtResId(R.string.loan_no_data);
        mTvName.setText(mLoanVo.getProductClassifyName());
        mTvBank.setText(mLoanVo.getProductBaseDescribe());
        mTvRate.setText(mLoanVo.getRefMonthRate());
        List<AgingVo> agingVos = null;
        if (mLoanVo != null && mLoanVo.getRepaymentWayAndAgingListCollection() != null && mLoanVo.getRepaymentWayAndAgingListCollection().size() > 0) {
            List<RepaymentWayAndAgingVo> listData = mLoanVo.getRepaymentWayAndAgingListCollection();
            if (listData != null && listData.size() > 0) {
                for (int i = 0; i < listData.size(); i++) {
                    mRepaymentWayAndAgingVo = listData.get(i);
                    if (mRepaymentWayAndAgingVo != null && "ACPI".equals(mRepaymentWayAndAgingVo.getRepaymentWayNo())) {
                        agingVos = mRepaymentWayAndAgingVo.getAgingInfoList();
                        break;
                    }
                }
            }
        }
        //显示加载中,请求账户列表
        mEmptyView.onStart();
        //请求银行卡列表信息
        UserData userData = AppApplication.getUserData();
        getPresenter().accountList(UniqueKey.VERSION.V1, userData.getToken());
        ProtocolListReqVo reqVo = new ProtocolListReqVo();
        reqVo.setProductBaseNo(mLoanVo.getProductBaseNo());

        mOrderNo = SharePrefrenceUtil.getString("config", mLoanVo.getProductBaseNo());
//        if (TextUtils.isEmpty(mOrderNo)) {
            getPresenter().getOrderNo(UniqueKey.VERSION.V1, userData.token);
//        }
        getPresenter().protocolList(UniqueKey.VERSION.V1, userData.getToken(), reqVo);

        List<LoanUsingVo> listUsing = mLoanVo.getLoanPurposeInfoList();
        mArrayLimit = new String[agingVos.size()];
        mArrayUsing = new String[listUsing.size()];
        for (int i = 0; i < agingVos.size(); i++) {
            AgingVo agingVo = agingVos.get(i);
            mArrayLimit[i] = agingVo.getAging();
            mMapLimit.put(agingVo.getAging(), agingVo);
        }
        for (int i = 0; i < listUsing.size(); i++) {
            LoanUsingVo usingVo = listUsing.get(i);
            mArrayUsing[i] = usingVo.getLoanPurposeDesc();
            mMapUsing.put(usingVo.getLoanPurposeDesc(), usingVo);
        }
    }

    @OnClick({R.id.loan_detail_trial_tv, R.id.loan_detail_apply_tv, R.id.loan_detail_use_tv, R.id.loan_detail_time_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loan_detail_apply_tv:
                if (!AppApplication.isLogin()) {
                    LoginActivity.newIntent(this, LoginActivity.TAG_LOGIN);
                    break;
                }
                String use = mTvUse.getText().toString();//用途
                String timeLimit = mTvTime.getText().toString();//时间期限
                String amount = mEditAmount.getText().toString();//贷款金额
                String bankNum = mLendingView.getMTvBankNum().getText().toString();//银行账号
                String accountName = mLendingView.getMTvAccountName().getText().toString();//账户名称
                String nodeBank = mLendingView.getMTvNodeBank().getText().toString();//开户支行
                if (TextUtils.isEmpty(use)) {
                    Toast.makeText(this, "请选择借款用途", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (TextUtils.isEmpty(timeLimit)) {
                    Toast.makeText(this, "请选择贷款期限", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (TextUtils.isEmpty(amount)) {
                    Toast.makeText(this, "请输入贷款金额", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (mCardVo == null) {
                    Toast.makeText(this, "请添加银行卡信息", Toast.LENGTH_SHORT).show();
                    break;
                }
                LoadingDialog.show(this, false);
                LoanApplyReqVo loanApplyReqVo = new LoanApplyReqVo();
                loanApplyReqVo.setRepaymentWaySerialNumber(mRepaymentWayAndAgingVo != null ? mRepaymentWayAndAgingVo.getRepaymentWaySerialNumber() : "");
                if (mAgingVo != null) {
                    loanApplyReqVo.setAging(mAgingVo.getAging());
                    loanApplyReqVo.setAgingNo(mAgingVo.getAgingNo());
                }
                loanApplyReqVo.setProductBaseNo(mLoanVo.getProductBaseNo());
                loanApplyReqVo.setProductTypeOne(mLoanVo.getProductClassifyNo());
                loanApplyReqVo.setLoanAmt(CommonUtil.yuanToCent(amount.replace(",", "")));
                loanApplyReqVo.setBindId(mCardVo.getBindId());
                loanApplyReqVo.setLoanPurposeCode(mSelectUsingVo.getLoanPurposeCode());//借款用途编码
                loanApplyReqVo.setLoanPurposeDesc(use);//借款用途
                loanApplyReqVo.setUploadFile("0");
                loanApplyReqVo.setLoanOrderNo(mOrderNo);

                loanApplyReqVo.setRefMonthRate(mLoanVo.getRefMonthRate());//借款利率
                loanApplyReqVo.setSourceIp(CommonUtil.getIPAddress(this));
                UserData userData = AppApplication.getUserData();
                getPresenter().applySubmit(loanApplyReqVo, "v1", userData.token);
                break;
            case R.id.loan_detail_trial_tv:
                String uses = mTvUse.getText().toString();//用途
                String timeLimits = mTvTime.getText().toString();//时间期限
                String ammounts = mEditAmount.getText().toString();//贷款金额
                if (TextUtils.isEmpty(uses)) {
                    Toast.makeText(this, "请选择借款用途", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (TextUtils.isEmpty(timeLimits)) {
                    Toast.makeText(this, "请选择贷款期限", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (TextUtils.isEmpty(ammounts)) {
                    Toast.makeText(this, "请输入贷款金额", Toast.LENGTH_SHORT).show();
                    break;
                }
                TrialReqVo trialReqVo = new TrialReqVo();
                trialReqVo.setAgingNo(mAgingVo.getAgingNo());
                trialReqVo.setLoanAmt(CommonUtil.yuanToCent(ammounts.replace(",", "")));
                trialReqVo.setProductBaseNo(mLoanVo.getProductBaseNo());
                trialReqVo.setRepaymentWaySerialNumber(mRepaymentWayAndAgingVo.getRepaymentWaySerialNumber());
                trialReqVo.setRate(mLoanVo.getRefMonthRate());
                TrialListActivity.newIntent(this, trialReqVo);
                break;
            case R.id.loan_detail_use_tv:
                List<PowerMenuItem> list = new ArrayList<>();
                for (int i = 0; i < mArrayUsing.length; i++) {
                    PowerMenuItem item = new PowerMenuItem(mArrayUsing[i], false);
                    list.add(item);
                }
                showUsr(list).showAsDropDown(mTvUse);
                break;
            case R.id.loan_detail_time_tv:
                List<PowerMenuItem> list1 = new ArrayList<>();
                for (int i = 0; i < mArrayLimit.length; i++) {
                    PowerMenuItem item = new PowerMenuItem(mArrayLimit[i] + "月", false);
                    list1.add(item);
                }
                showMonth(list1).showAsDropDown(mTvTime);
                break;
            default:
                break;
        }
    }

    @Override
    public void onDataLoadAgain() {
        //请求银行卡列表信息
        UserData userData = AppApplication.getUserData();
        getPresenter().accountList(UniqueKey.VERSION.V1, userData.token);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == LoginActivity.TAG_LOGIN) {
                onClick(mTvApply);
            } else if (requestCode == mLendingView.REQUEST_CODE_ADD_CODE) {
                //添加银行卡成功回来
                //请求银行卡列表信息
                LoadingDialog.show(this, false);
                UserData userData = AppApplication.getUserData();
                getPresenter().accountList(UniqueKey.VERSION.V1, userData.token);
            } else if (requestCode == mLendingView.REQUEST_CODE_LIST_CODE) {
                if (data == null) {
                    //添加银行卡成功回来
                    //请求银行卡列表信息
                    LoadingDialog.show(this, false);
                    UserData userData = AppApplication.getUserData();
                    getPresenter().accountList(UniqueKey.VERSION.V1, userData.token);
                }
                CardVo cardVo = (CardVo) data.getSerializableExtra("data");
                if (cardVo == null) {
                    //添加银行卡成功回来
                    //请求银行卡列表信息
                    LoadingDialog.show(this, false);
                    UserData userData = AppApplication.getUserData();
                    getPresenter().accountList(UniqueKey.VERSION.V1, userData.token);
                } else {
                    mCardVo = cardVo;
                    List<CardVo> cardVos = new ArrayList<>();
                    cardVos.add(cardVo);
                    mLendingView.setData(cardVos);
                }
            }
        }
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
                LoginActivity.newIntent(LoanDetailActivity.this);
                finish();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            String amount = mEditAmount.getText().toString();
            if (!TextUtils.isEmpty(amount)) {
                mEditAmount.setText(CommonUtil.formatAmountByInteger(CommonUtil.yuanToCent(amount)));
            }
        }
    }

    public static void newIntent(Context context, LoanVo loanVo) {
        Intent intent = new Intent(context, LoanDetailActivity.class);
        intent.putExtra("loanVo", loanVo);
        context.startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("loanVo", mLoanVo);
    }

    @Override
    public void onSuccessCardList(CardData cardData) {
        mEmptyView.onSuccess();
        LoadingDialog.hide();
        if (cardData == null) {
            mLendingView.setData(null);
            return;
        }
        mCardList = cardData.getCardInfoList();
        mLendingView.setData(mCardList);
        if (mCardList != null && mCardList.size() > 0) {
            mCardVo = mCardList.get(0);
        }
    }


    @Override
    public void onFailureCardList(String code, String msg) {
        mEmptyView.onError();
        LoadingDialog.hide();
    }

    /**
     * 提交贷款申请
     * @param baseBean
     */
    @Override
    public void onSuccessApply(BaseBean baseBean) {
        LoadingDialog.hide();
        showDialog();
    }

    @Override
    public void onFailureApply(String code, String msg) {
        LoadingDialog.hide();
        checkToken(code);
    }

    /**
     * 请求协议列表
     * @param voList
     */
    @Override
    public void onSuccessProtocol(List<ProtocolVo> voList) {
        mProtocolVoList = voList;
        if (mProtocolVoList == null || mProtocolVoList.size() == 0) {
            return;
        }
        String[] proArray = new String[mProtocolVoList.size()];
        for (int i = 0; i < mProtocolVoList.size(); i++) {
            proArray[i] = mProtocolVoList.get(i).getName();
        }
        mClickMoreView.splitCharSequence(proArray, true);
        mClickMoreView.setOnPositionClickListener(new ClickMoreTextView.OnPositionClickListener() {
            @Override
            public void onPositionClick(int position) {
                mProtocolVo = mProtocolVoList.get(position);
                LoadingDialog.show(LoanDetailActivity.this, false);
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

    /**
     * 请求协议内容
     * @param contentVo
     */
    @Override
    public void onSuccessProtocolContent(ProtocolContentVo contentVo) {
        LoadingDialog.hide();
        if (contentVo != null && !TextUtils.isEmpty(contentVo.getContent())) {
            AppApplication.mApplication.setWebContent(contentVo.getContent());
            WebViewActivity.newIntent(this);
        }
    }

    @Override
    public void onFailureProtocolContent(String code, String msg) {
        LoadingDialog.hide();

    }

    /**
     * 请求订单号
     * @param loanOrderNoVo
     */
    @Override
    public void onSuccessOrderNo(LoanOrderNoVo loanOrderNoVo) {
        if (loanOrderNoVo != null && !TextUtils.isEmpty(loanOrderNoVo.getLoanOrderNo())) {
            mOrderNo = loanOrderNoVo.getLoanOrderNo();
            SharePrefrenceUtil.setString("config", mLoanVo.getProductBaseNo(), loanOrderNoVo.getLoanOrderNo());
        }
    }

    @Override
    public void onFailureOrderNo(String code, String msg) {

    }

    /**
     * 上传图片至资源服务器
     * @param baseBean
     */
    @Override
    public void onSuccessPic(BaseBean baseBean) {

    }

    @Override
    public void onFailurePic(String code, String msg) {

    }

    private void showDialog() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("提交成功");
        builder.setMessage("您已成功提交借款申请，后续请留意申请动态");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }

    /**
     * 贷款期限弹窗
     *
     * @param list
     * @return
     */
    public PowerMenu showMonth(final List<PowerMenuItem> list) {
        if (mPowerMenuMonth == null) {
            mPowerMenuMonth = new PowerMenu.Builder(this)
                    .addItemList(list)// list has "Novel", "Poerty", "Art"
                    .setAnimation(MenuAnimation.SHOWUP_TOP_LEFT)// Animation start point (TOP | LEFT)
                    .setMenuRadius(10f)
                    .setMenuShadow(10f)
                    .setTextColor(getResources().getColor(R.color.color_666666))
                    .setSelectedTextColor(Color.WHITE)
                    .setMenuColor(Color.WHITE)
                    .setSelectedMenuColor(getResources().getColor(R.color.colorPrimary))
                    .setOnMenuItemClickListener(new OnMenuItemClickListener<PowerMenuItem>() {
                        @Override
                        public void onItemClick(int position, PowerMenuItem item) {
                            mAgingVo = mMapLimit.get(item.title.replace("月", ""));
                            mTvTime.setText(item.title);
                            mPowerMenuMonth.dismiss();
                        }
                    }).setAnimation(MenuAnimation.DROP_DOWN).build();
        }
        return mPowerMenuMonth;
    }

    /**
     * 显示用途弹窗
     *
     * @param list
     * @return
     */
    public PowerMenu showUsr(final List<PowerMenuItem> list) {
        if (mPowerMenuUse == null) {
            mPowerMenuUse = new PowerMenu.Builder(
                    this)
                    .addItemList(list)// list has "Novel", "Poerty", "Art"
                    .setAnimation(MenuAnimation.SHOWUP_TOP_LEFT)// Animation start point (TOP | LEFT)
                    .setMenuRadius(10f)
                    .setMenuShadow(10f)
                    .setTextColor(getResources().getColor(R.color.color_666666))
                    .setSelectedTextColor(Color.WHITE)
                    .setMenuColor(Color.WHITE)
                    .setSelectedMenuColor(getResources().getColor(R.color.colorPrimary))
                    .setOnMenuItemClickListener(new OnMenuItemClickListener<PowerMenuItem>() {
                        @Override
                        public void onItemClick(int position, PowerMenuItem item) {
                            mSelectUsingVo = mMapUsing.get(item.getTitle());
                            mTvUse.setText(item.title);
                            mPowerMenuUse.dismiss();
                        }
                    }).setAnimation(MenuAnimation.DROP_DOWN)
                    .build();
        }
        return mPowerMenuUse;
    }
}
