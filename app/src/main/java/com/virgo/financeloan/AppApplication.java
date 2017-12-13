package com.virgo.financeloan;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.virgo.financeloan.model.responce.UserData;
import com.virgo.financeloan.net.Repository;
import com.virgo.financeloan.util.SharePrefrenceUtil;

import java.util.HashMap;


public class AppApplication extends Application {

    public static AppApplication mApplication;
    private static UserData mUserData;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
//        String user = SharePrefrenceUtil.getString("config", "user_info");
        Repository.get().setOnDynamicParameterListener(new Repository.OnDynamicParameterListener() {
            @Override
            public HashMap<String, String> headers() {
                return null;
            }

            @Override
            public HashMap<String, String> commonParam() {
//                HashMap<String, String>  hashMap = new HashMap();
//                hashMap.put("source", "android");
//                hashMap.put("token", isLogin() ? mUserData.token : "");
//                hashMap.put("uid", isLogin() ? mUserData.uid : "");
                return null;
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(base);
    }

    public static boolean isLogin() {
        return getUserData() != null && !TextUtils.isEmpty(mUserData.token);
    }

    public static UserData getUserData() {
        if (mUserData == null) {
            String userData = SharePrefrenceUtil.getString("user", "logindata");
            if (TextUtils.isEmpty(userData) || userData.length() < 10) {
                return null;
            }
            mUserData = new Gson().fromJson(userData, new TypeToken<UserData>(){}.getType());
        }
        return mUserData;
    }

    public static void setUserData(UserData userData) {
        mUserData = userData;
    }
}
