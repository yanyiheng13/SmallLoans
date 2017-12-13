package com.virgo.financeloan.mvp.contract;


import com.virgo.financeloan.model.responce.IsOkData;
import com.virgo.financeloan.model.responce.UserData;
import com.sai.framework.mvp.MvpView;

/**
 * 功能说明：登录
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/8/29
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class LoginContract {

    public interface View extends MvpView {
        void onSuccessLogin(UserData loginData);
        void onFailureLogin(String code, String msg);

        void onSuccessSendCode(IsOkData isOkData);
        void onFailureSendCode(String code, String msg);
    }
}
