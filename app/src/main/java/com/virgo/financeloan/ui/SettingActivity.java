package com.virgo.financeloan.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.virgo.financeloan.AppApplication;
import com.virgo.financeloan.R;
import com.virgo.financeloan.util.SharePrefrenceUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能说明： 设置页面
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-11-21
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }

    public static void newIntent(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.exit_account_rl, R.id.change_pw_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exit_account_rl:
                showDialog("退出登录", "确认将退出本次登录状态？");
                break;
            case R.id.change_pw_rl:
                ChangePwActivity.newIntent(this);
                break;
        }
    }

    private void showDialog(String title, String content) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharePrefrenceUtil.setString("user", "logindata", "");
                AppApplication.setUserData(null);
                LoginActivity.newIntent(SettingActivity.this);
                finish();
            }
        });
        builder.show();
    }
}
