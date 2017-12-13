package com.virgo.financeloan.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.virgo.financeloan.AppApplication;
import com.virgo.financeloan.BuildConfig;
import com.virgo.financeloan.R;
import com.virgo.financeloan.model.request.LoginReqVo;
import com.virgo.financeloan.model.responce.IsOkData;
import com.virgo.financeloan.model.responce.UserData;
import com.virgo.financeloan.mvp.LoginPresent;
import com.virgo.financeloan.mvp.contract.LoginContract;
import com.virgo.financeloan.ui.view.LoadingDialog;
import com.virgo.financeloan.util.CommonUtil;
import com.virgo.financeloan.util.RSAUtil;
import com.virgo.financeloan.util.SharePrefrenceUtil;
import com.virgo.financeloan.util.UiTitleBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能说明： 登录
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-11-19
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
public class LoginActivity extends BaseActivity<LoginPresent> implements LoginContract.View {
    public static int TAG_CHANGE_PW = 100;
    public static int TAG_LOGIN = 101;

    @BindView(R.id.login_originpw_edit)
    EditText mEditPhone;
    @BindView(R.id.login_pw_edit)
    EditText mEditPw;
    @BindView(R.id.login_tv)
    TextView mTvLogin;

    private boolean isExit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            isExit = getIntent().getBooleanExtra("isExit", false);
        } else {
            isExit = savedInstanceState.getBoolean("isExit");
        }
        UiTitleBarUtil uiTitleBarUtil = new UiTitleBarUtil(this);
        uiTitleBarUtil.setColorBar(ContextCompat.getColor(this, android.R.color.white));
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.login_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_tv:
                String phone = mEditPhone.getText().toString();
                if (TextUtils.isEmpty(phone) || !(phone.startsWith("1")) || phone.length() != 11) {
                    Toast.makeText(this, "请正确输入手机号", Toast.LENGTH_SHORT).show();
                    break;
                }
                String pw = mEditPw.getText().toString();
                if (TextUtils.isEmpty(pw)) {
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                    break;
                }
                LoadingDialog.show(this, false);
                LoginReqVo reqVo = new LoginReqVo(mEditPhone.getText().toString(),
                        RSAUtil.encryptByPubKey(pw, BuildConfig.app_secrect), CommonUtil.getIPAddress(this), CommonUtil.getDeviceId(this));
                getPresenter().login(reqVo, "v1");
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            Bundle bundle = data.getExtras();
            if (requestCode == TAG_CHANGE_PW && bundle != null) {
                String pw = bundle.getString("pw");
                mEditPw.setText(pw);
                onClick(mTvLogin);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (isExit) {
            HomeActivity.newIntent(this);
            finish();
            return;
        }
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    @Override
    public void onSuccessLogin(UserData userData) {
        LoadingDialog.hide();
        if ("1".equals(userData.getChangePwd())) {
            AppApplication.setUserData(userData);
            showDialog("修改密码", "为了您得账号安全，首次登录需要更改密码");
        } else {
            AppApplication.setUserData(userData);
            SharePrefrenceUtil.setString("user", "logindata", new Gson().toJson(userData));
            if (isExit) {
                HomeActivity.newIntent(this);
                return;
            }
            setResult(Activity.RESULT_OK);
            finish();
        }
    }

    private void showDialog(String title, String content) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ChangePwActivity.newIntent(LoginActivity.this, TAG_CHANGE_PW, true);
            }
        });
        builder.show();
    }

    @Override
    public void onFailureLogin(String code, String msg) {
        LoadingDialog.hide();
        UserData userData = new UserData();
        userData.token = "1";
    }

    @Override
    public void onSuccessSendCode(IsOkData isOkData) {

    }

    @Override
    public void onFailureSendCode(String code, String msg) {

    }

    public static void newIntent(Activity context, int requestCode) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivityForResult(intent, requestCode);
    }

    public static void newIntent(Activity context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("isExit", true);
        context.startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isExit", isExit);
    }

    @Override
    protected boolean isSetting() {
        return false;
    }

}
