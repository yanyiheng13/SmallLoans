package com.virgo.financeloan.ui;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.virgo.financeloan.R;


/**
 * 功能说明：启动页
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-24 上午12:27
 * @Copyright (c) 2017. Inc. All rights reserved.
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                HomeActivity.newIntent(SplashActivity.this);
                finish();
            }
        }, 600);


}
}
