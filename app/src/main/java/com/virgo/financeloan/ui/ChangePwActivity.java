package com.virgo.financeloan.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.virgo.financeloan.AppApplication;
import com.virgo.financeloan.BuildConfig;
import com.virgo.financeloan.R;
import com.virgo.financeloan.model.request.ChangePwReqVo;
import com.virgo.financeloan.model.responce.IsOkData;
import com.virgo.financeloan.model.responce.UserData;
import com.virgo.financeloan.mvp.ChangePwPresent;
import com.virgo.financeloan.mvp.contract.ChangePwContract;
import com.virgo.financeloan.ui.view.CustomTitleView;
import com.virgo.financeloan.ui.view.LoadingDialog;
import com.virgo.financeloan.util.RSAUtil;
import com.virgo.financeloan.util.SharePrefrenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能说明： 修改密码界面
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-11-20
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
public class ChangePwActivity extends BaseActivity<ChangePwPresent> implements ChangePwContract.View {
    public static String TAG_PW = "pw";

    @BindView(R.id.title_view)
    CustomTitleView mTitleView;
    @BindView(R.id.login_originpw_edit)
    EditText mEditOriginPw;
    @BindView(R.id.new_pw_edit)
    EditText mEditNewPw;
    @BindView(R.id.sure_pw_edit)
    EditText mEditSureNewPw;
    @BindView(R.id.login_code_edit)
    EditText mEditCode;
    @BindView(R.id.login_time_tv)
    TextView mTvCode;
    @BindView(R.id.register_tv)
    TextView mTvLogin;
    @BindView(R.id.check_agree)
    CheckBox mCheckbox;

    private CountDownTimer mTimer;
    private boolean isFirst;

    public String mPw;//用于首次登录后 修改密码后返回登录界面

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            isFirst = getIntent().getBooleanExtra("first", false);
        } else {
            isFirst = savedInstanceState.getBoolean("first");
            mPw = savedInstanceState.getString(TAG_PW);
        }
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        if (isFirst) {
            mTitleView.setLeftGone();
        }
        mTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTvCode.setText(String.format(getString(R.string.code_time), millisUntilFinished / 1000 + ""));
            }

            @Override
            public void onFinish() {
                mTvCode.setEnabled(true);
                mTvCode.setText(R.string.get_code);
            }
        };
    }

    @OnClick({R.id.login_time_tv, R.id.register_tv, R.id.agree_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_time_tv:
                mTvCode.setEnabled(false);
                UserData userData = AppApplication.getUserData();
                getPresenter().sendCode("v1", userData.token, "1");
                mTimer.start();
                break;
            case R.id.register_tv:
                String originPw = mEditOriginPw.getText().toString();
                String newPw = mEditNewPw.getText().toString();
                String sureNewPw = mEditSureNewPw.getText().toString();
                String code = mEditCode.getText().toString();
                if (TextUtils.isEmpty(originPw)) {
                    Toast.makeText(this, "请输入原密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(newPw) || TextUtils.isEmpty(sureNewPw)) {
                    Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!newPw.equals(sureNewPw)) {
                    Toast.makeText(this, "新密码两次输入不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(code) || code.length() < 4 || code.length() > 8) {
                    Toast.makeText(this, "请输入4-8位合法短信验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                LoadingDialog.show(this, false);
                mPw = newPw;
                ChangePwReqVo reqVo = new ChangePwReqVo(RSAUtil.encryptByPubKey(originPw, BuildConfig.app_secrect), RSAUtil.encryptByPubKey(mPw, BuildConfig.app_secrect), code);
                getPresenter().changePw(reqVo, "v1", AppApplication.getUserData().token);
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccessChangePw(IsOkData loginData) {
        LoadingDialog.hide();
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(TAG_PW, mPw);
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (!isFirst) {
            finish();
        }
    }

    @Override
    public void onFailureChangePw(String code, String msg) {
        LoadingDialog.hide();
        checkToken(code);
    }

    @Override
    public void onSuccessSendCode(IsOkData isOkData) {
        Toast.makeText(this, "验证码已经下发成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailureSendCode(String code, String msg) {
        mTimer.onFinish();
        mTvCode.setText(R.string.get_code);
        mTvCode.setEnabled(true);
    }

    public static void newIntent(Activity context) {
        Intent intent = new Intent(context, ChangePwActivity.class);
        context.startActivity(intent);
    }

    public static void newIntent(Activity context, int requestCode, boolean isFirst) {
        Intent intent = new Intent(context, ChangePwActivity.class);
        intent.putExtra("first", isFirst);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("first", isFirst);
        outState.putString(TAG_PW, mPw);
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
                LoginActivity.newIntent(ChangePwActivity.this);
                finish();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }
}
