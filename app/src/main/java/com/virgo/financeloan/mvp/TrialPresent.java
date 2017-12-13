package com.virgo.financeloan.mvp;


import com.virgo.financeloan.AppApplication;
import com.virgo.financeloan.model.request.TrialReqVo;
import com.virgo.financeloan.model.responce.BaseBean;
import com.virgo.financeloan.model.responce.IsOkData;
import com.virgo.financeloan.model.responce.TrialData;
import com.virgo.financeloan.mvp.contract.TrialContract;
import com.virgo.financeloan.net.Repository;
import com.virgo.financeloan.net.rx.RxHelper;
import com.sai.framework.mvp.BasePresenter;

/**
 * 功能说明： 试算列表
 *
 * @author: 闫毅恒
 * @email： yanyiheng86@163.com
 * @version: 1.0
 * @date: 2017/11/29
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class TrialPresent extends BasePresenter<Repository, TrialContract.View> {

    public void trialList(TrialReqVo trialReqVo, String version) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().trialList(getRequestBody(trialReqVo), version)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<TrialData>>() {
            @Override
            public void onSuccess(String response, BaseBean<TrialData> result) {
                getRootView().onSuccessTrail(result.data);
            }

            @Override
            public void onFailure(String code, String error) {
                super.onFailure(code, error);
                getRootView().onFailureTrial(code, error);
            }
        }).application(AppApplication.mApplication).start();
    }
}
