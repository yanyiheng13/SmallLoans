package com.virgo.financeloan.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.virgo.financeloan.AppApplication;
import com.virgo.financeloan.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能说明：webView
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/13 17:03
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */

public class WebViewActivity extends BaseActivity {
    @BindView(R.id.web_view)
    WebView mWebView;

    private WebSettings webSettings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        String webContent = AppApplication.mApplication.getWebContent();
        if (TextUtils.isEmpty(webContent)) { finish(); return;}
        initWebView();
        mWebView.loadDataWithBaseURL(null, webContent, "text/html", "utf-8", null);
    }

    private void initWebView() {
        webSettings = mWebView.getSettings();
        // 关闭webview自动保存密码
        webSettings.setSavePassword(false);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

//        final String ua = new StringBuilder().append(webSettings.getUserAgentString()).append(" LeFinanceTrade/").append(GlobalData.version).toString();
//        webSettings.setUserAgentString(ua);


        webSettings.setJavaScriptEnabled(true);

        webSettings.setAllowFileAccess(true);// 允许访问文件
        webSettings.setBuiltInZoomControls(false);// 设置显示缩放按钮
        webSettings.setSupportZoom(false); // 支持缩放


//        String cachePath = WebViewHelper.getCachePath();
        //app cache
        webSettings.setAppCacheEnabled(true);
        webSettings.getDatabasePath();
//        webSettings.setAppCachePath(cachePath);

        //js 度读写db
        webSettings.setDatabaseEnabled(true);
//        webSettings.setDatabasePath(cachePath);
        // dom localStorage
        webSettings.setDomStorageEnabled(true);

        //开启缓存
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);


        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//适应内容大小
//        myWebViewClient = new MyWebViewClient();
//        mWebView.setWebViewClient(myWebViewClient);
//        myWebChromeClient = new MyWebChromeClient();
//        mWebView.setWebChromeClient(myWebChromeClient);
//        mWebView.addJavascriptInterface(new JsBridge(), "native");

        //解决https在5.0以上的跨域问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(mWebView, true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.setAllowUniversalAccessFromFileURLs(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            if (!GlobalData.isOnlineEnvironment()) {
//                WebView.setWebContentsDebuggingEnabled(true);
//            }
//        }

        if (Build.VERSION.SDK_INT >= 19) // KITKAT
        {
//            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
    }

    public static void newIntent(Context context) {
        Intent intent = new Intent(context, WebViewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppApplication.mApplication.setWebContent(null);
    }
}
