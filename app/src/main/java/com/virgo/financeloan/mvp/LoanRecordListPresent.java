package com.virgo.financeloan.mvp;


import com.virgo.financeloan.AppApplication;
import com.virgo.financeloan.model.request.LoanUserConfirmReqVo;
import com.virgo.financeloan.model.responce.BaseBean;
import com.virgo.financeloan.model.responce.LoanRecordVo;
import com.virgo.financeloan.mvp.contract.LoanRecordListContract;
import com.virgo.financeloan.net.Repository;
import com.virgo.financeloan.net.rx.RxHelper;
import com.sai.framework.mvp.BasePresenter;

import java.util.List;

/**
 * 功能说明： 订单列表
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/11/26
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class LoanRecordListPresent extends BasePresenter<Repository, LoanRecordListContract.View> {

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

    public void loanRecordAllList(String version, String token) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().orderFullList(version, token)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<List<LoanRecordVo>>>() {
            @Override
            public void onSuccess(String response, BaseBean<List<LoanRecordVo>> result) {
                getRootView().onSuccessLoanRecodeList(result.data);
            }

            @Override
            public void onFailure(String code, String error) {
                super.onFailure(code, error);
                getRootView().onFailureLoanRecodeList(code, error);
            }
        }).application(AppApplication.mApplication).start();
    }

    public void loanRecordConfirmList(String version, String token) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().orderConfirmList(version, token)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<List<LoanRecordVo>>>() {
            @Override
            public void onSuccess(String response, BaseBean<List<LoanRecordVo>> result) {
                getRootView().onSuccessLoanRecodeList(result.data);
            }

            @Override
            public void onFailure(String code, String error) {
                super.onFailure(code, error);
                getRootView().onFailureLoanRecodeList(code, error);
            }
        }).application(AppApplication.mApplication).start();
    }
}
