package com.virgo.financeloan.mvp;


import com.virgo.financeloan.model.request.LoginReqVo;
import com.virgo.financeloan.model.responce.BaseBean;
import com.virgo.financeloan.model.responce.IsOkData;
import com.virgo.financeloan.model.responce.UserData;
import com.virgo.financeloan.mvp.contract.LoginContract;
import com.virgo.financeloan.net.Repository;
import com.virgo.financeloan.net.rx.RxHelper;
import com.sai.framework.mvp.BasePresenter;

/**
 * 功能说明： 登录
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/8/29
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class LoginPresent extends BasePresenter<Repository, LoginContract.View> {

    public void login(LoginReqVo loginReqVo, String version) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().login(getRequestBody(loginReqVo), version)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<UserData>>() {
            @Override
            public void onSuccess(String response, BaseBean<UserData> result) {
                getRootView().onSuccessLogin(result.data);
            }

            @Override
            public void onFailure(String code, String error) {
                super.onFailure(code, error);
                getRootView().onFailureLogin(code, error);
            }
        }).start();
    }

}
