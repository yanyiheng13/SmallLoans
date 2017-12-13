package com.virgo.financeloan.mvp;


import com.virgo.financeloan.model.request.ChangePwReqVo;
import com.virgo.financeloan.model.responce.BaseBean;
import com.virgo.financeloan.model.responce.IsOkData;
import com.virgo.financeloan.mvp.contract.ChangePwContract;
import com.virgo.financeloan.net.Repository;
import com.virgo.financeloan.net.rx.RxHelper;
import com.sai.framework.mvp.BasePresenter;

/**
 * 功能说明： 修改密码
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/8/29
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class ChangePwPresent extends BasePresenter<Repository, ChangePwContract.View> {

    public void changePw(ChangePwReqVo changePwReqVo, String version, String token) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().changePw(getRequestBody(changePwReqVo), version, token)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<IsOkData>>() {
            @Override
            public void onSuccess(String response, BaseBean<IsOkData> result) {
                getRootView().onSuccessChangePw(result.data);
            }

            @Override
            public void onFailure(String code, String error) {
                super.onFailure(code, error);
                getRootView().onFailureChangePw(code, error);
            }
        }).start();
    }

    public void sendCode(String version, String token, String smsType) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().sendCode(version, token, smsType)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<IsOkData>>() {
            @Override
            public void onSuccess(String response, BaseBean<IsOkData> result) {
                getRootView().onSuccessSendCode(result.data);
            }

            @Override
            public void onFailure(String code, String error) {
                super.onFailure(code, error);
                getRootView().onFailureSendCode(code, error);
            }
        }).start();
    }
}
