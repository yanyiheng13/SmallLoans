package com.virgo.financeloan.loan.mvp;


import com.sai.framework.mvp.BasePresenter;
import com.virgo.financeloan.AppApplication;
import com.virgo.financeloan.loan.model.request.ContractFillReqVo;
import com.virgo.financeloan.loan.model.request.ContractListReqVo;
import com.virgo.financeloan.loan.mvp.contract.ProductTrialContract;
import com.virgo.financeloan.model.request.LoanUserConfirmReqVo;
import com.virgo.financeloan.model.request.ProtocolContentReqVo;
import com.virgo.financeloan.model.request.TrialByProductNoReqVo;
import com.virgo.financeloan.model.responce.BaseBean;
import com.virgo.financeloan.model.responce.ProtocolContentVo;
import com.virgo.financeloan.loan.model.response.ProtocolItemVo;
import com.virgo.financeloan.model.responce.TrialData;
import com.virgo.financeloan.net.Repository;
import com.virgo.financeloan.net.rx.RxHelper;

import java.util.List;
import java.util.Map;

/**
 * 功能说明： 还款计划请求
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/12/13
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class ProductTrialPresenter extends BasePresenter<Repository, ProductTrialContract.View> {

    public void loanRecordConfirm(LoanUserConfirmReqVo confirmReqVo, String version, String token) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().orderConfirm(getRequestBody(confirmReqVo), version, token)).callBack(new RxHelper
                .CallBackAdapter<BaseBean>() {
            @Override
            public void onSuccess(String response, BaseBean result) {
                getRootView().onSuccessConfirm(result);
            }

            @Override
            public void onFailure(String code, String error) {
                super.onFailure(code, error);
                getRootView().onFailureConfirm(code, error);
            }
        }).application(AppApplication.mApplication).start();
    }

    /**
     * 待用户确认产品预算
     */
    public void repaymentTrial(String version, String token, TrialByProductNoReqVo noReqVo) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().repaymentTrial(version, token, getRequestBody(noReqVo))).callBack(new RxHelper
                .CallBackAdapter<BaseBean<TrialData>>() {
            @Override
            public void onSuccess(String response, BaseBean<TrialData> result) {
                getRootView().onSuccessRepaymentTrial(result.data);
            }

            @Override
            public void onFailure(String code, String error) {
                super.onFailure(code, error);
                getRootView().onFailureRepaymentTrial(code, error);
            }
        }).application(AppApplication.mApplication).start();
    }

    /**
     * 协议列表
     * @param version
     * @param token
     * @param listReqVo
     */
    public void protocolList(String version, String token, ContractListReqVo listReqVo) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().protocolList(version, token, getRequestBody(listReqVo))).callBack(new RxHelper
                .CallBackAdapter<BaseBean<List<ProtocolItemVo>>>() {
            @Override
            public void onSuccess(String response, BaseBean<List<ProtocolItemVo>> result) {
                getRootView().onSuccessProtocol(result.data);
            }

            @Override
            public void onFailure(String code, String error) {
                super.onFailure(code, error);
                getRootView().onFailureProtocol(code, error);
            }
        }).application(AppApplication.mApplication).start();
    }

    /**
     * 协议内容
     * @param version
     * @param token
     * @param contentReqVo
     */
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
    /**
     * 协议需要替换得map信息
     */
    public void contractReplaceInfo(String version, String token, ContractFillReqVo noReqVo) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().mapInfo(version, token, getRequestBody(noReqVo))).callBack(new RxHelper
                .CallBackAdapter<BaseBean<Map<String,String>>>() {
            @Override
            public void onSuccess(String response, BaseBean<Map<String,String>> result) {
                getRootView().onSuccessMap(result.data);
            }

            @Override
            public void onFailure(String code, String error) {
                super.onFailure(code, error);
                getRootView().onFailureMap(code, error);
            }
        }).application(AppApplication.mApplication).start();
    }

}
