package com.virgo.financeloan.mvp;


import com.sai.framework.mvp.BasePresenter;
import com.virgo.financeloan.AppApplication;
import com.virgo.financeloan.model.request.LoanApplyReqVo;
import com.virgo.financeloan.model.request.ProtocolContentReqVo;
import com.virgo.financeloan.model.request.ProtocolListReqVo;
import com.virgo.financeloan.model.responce.BaseBean;
import com.virgo.financeloan.model.responce.CardData;
import com.virgo.financeloan.model.responce.ProtocolContentVo;
import com.virgo.financeloan.model.responce.ProtocolVo;
import com.virgo.financeloan.mvp.contract.LoanDetailContract;
import com.virgo.financeloan.net.Repository;
import com.virgo.financeloan.net.rx.RxHelper;

import java.util.List;

/**
 * 功能说明： 即时货源列表
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/8/29
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class LoanDetailPresenter extends BasePresenter<Repository, LoanDetailContract.View> {

    public void applySubmit(LoanApplyReqVo loanApplyReqVo, String version, String token) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().applySubmit(getRequestBody(loanApplyReqVo), version, token)).callBack(new RxHelper
                .CallBackAdapter<BaseBean>() {
            @Override
            public void onSuccess(String response, BaseBean result) {
                getRootView().onSuccessApply(result);
            }

            @Override
            public void onFailure(String code, String error) {
                super.onFailure(code, error);
                getRootView().onFailureApply(code, error);
            }
        }).start();
    }

    public void accountList(String version, String token) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().accountList(version, token)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<CardData>>() {
            @Override
            public void onSuccess(String response, BaseBean<CardData> result) {
                getRootView().onSuccessCardList(result.data);
            }

            @Override
            public void onFailure(String code, String error) {
                super.onFailure(code, error);
                getRootView().onFailureCardList(code, error);
            }
        }).application(AppApplication.mApplication).start();
    }

    public void protocolList(String version, String token, ProtocolListReqVo listReqVo) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().protocolList(version, token, getRequestBody(listReqVo))).callBack(new RxHelper
                .CallBackAdapter<BaseBean<List<ProtocolVo>>>() {
            @Override
            public void onSuccess(String response, BaseBean<List<ProtocolVo>> result) {
                getRootView().onSuccessProtocol(result.data);
            }

            @Override
            public void onFailure(String code, String error) {
                super.onFailure(code, error);
                getRootView().onFailureProtocol(code, error);
            }
        }).application(AppApplication.mApplication).start();
    }

    public void protocolContent(String version, String token, ProtocolContentReqVo contentReqVo) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().protocolContent(version, token, getRequestBody(contentReqVo))).callBack(new RxHelper
                .CallBackAdapter<BaseBean<ProtocolContentVo>>() {
            @Override
            public void onSuccess(String response, BaseBean<ProtocolContentVo> result) {
                getRootView().onSuccessProtocolContent(result.data);
            }

            @Override
            public void onFailure(String code, String error) {
                super.onFailure(code, error);
                getRootView().onFailureProtocolContent(code, error);
            }
        }).application(AppApplication.mApplication).start();
    }

}
