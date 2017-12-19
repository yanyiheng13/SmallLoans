package com.virgo.financeloan.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.virgo.financeloan.AppApplication;
import com.virgo.financeloan.R;
import com.virgo.financeloan.util.SharePrefrenceUtil;
import com.virgo.financeloan.util.UiTitleBarUtil;
import com.virgo.financeloan.net.Repository;
import com.sai.framework.base.SaiActivity;
import com.sai.framework.mvp.BasePresenter;
import com.sai.framework.mvp.MvpModel;

import butterknife.ButterKnife;


/**
 * 功能说明： 基类
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-24 上午12:01
 * @Copyright (c) 2017. Inc. All rights reserved.
 */
public class BaseActivity<P extends BasePresenter> extends SaiActivity<P> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isSetting()) {
            UiTitleBarUtil uiTitleBarUtil = new UiTitleBarUtil(this);
            uiTitleBarUtil.setColorBar(ContextCompat.getColor(this, R.color.colorAccent));
        }
    }

//    @Override
//    public void setContentView(View view) {
//        super.setContentView(view);
//    }

    protected boolean isSetting() {
        return true;
    }

    @Override
    public void showLoadDialog() {

    }

    protected void checkToken(String code) {
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
                LoginActivity.newIntent(BaseActivity.this);
                finish();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    @Override
    public void hideLoadDialog() {

    }

    @Override
    protected MvpModel getModel() {
        return Repository.get();
    }

}
