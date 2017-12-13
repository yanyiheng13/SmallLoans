package com.virgo.financeloan.net;


import com.virgo.financeloan.BuildConfig;
import com.sai.framework.mvp.MvpModel;
import com.sai.framework.retrofit.RetrofitHelper;

import java.util.HashMap;

/**
 * 功能说明：
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-24 上午12:32
 * @Copyright (c) 2017. Inc. All rights reserved.
 */
public class Repository implements MvpModel {

    private RemoteService mRemoteService;

    private LocalService mLocalService;

    private RetrofitHelper.Builder mBuilder;

    private static class RepositoryInstance {
        public static Repository instance = new Repository();
    }


    private Repository() {
//        InputStream[] inputStreams = new InputStream[]{com.community.life.AppApplication.mApplication.getResources().openRawResource(R.raw.le2017)};

        mBuilder = new RetrofitHelper.Builder().baseUrl(BuildConfig.api_url).debug(BuildConfig.DEBUG);
        mBuilder.dynamicParameter(new RetrofitHelper.OnDynamicParameterListener() {
            @Override
            public HashMap<String, String> headers() {
                return listener == null ? null : listener.headers();
            }

            @Override
            public HashMap<String, String> commonParam() {
                return listener == null ? null : listener.commonParam();
            }
        });
        mRemoteService = mBuilder.build().create(RemoteService
                .class);
    }

    private OnDynamicParameterListener listener;
    public void setOnDynamicParameterListener(OnDynamicParameterListener listener) {
        this.listener = listener;
    }
    public interface OnDynamicParameterListener {
        HashMap<String, String> headers();
        HashMap<String, String> commonParam();
    }

    public Repository setHeader(HashMap<String, String> mapHeader) {
        if (mBuilder != null) {
            mBuilder.headers(mapHeader);
        }
        return this;
    }

    public static Repository get() {
        return RepositoryInstance.instance;
    }

    public RemoteService getRemote() {
        return mRemoteService;
    }

    public LocalService getLocal() {
        return mLocalService;
    }
}
