package com.virgo.financeloan.mvp;


import com.sai.framework.mvp.BasePresenter;
import com.virgo.financeloan.AppApplication;
import com.virgo.financeloan.model.request.LoanUserConfirmReqVo;
import com.virgo.financeloan.model.request.RecordReqVo;
import com.virgo.financeloan.model.request.TrialByProductNoReqVo;
import com.virgo.financeloan.model.responce.BaseBean;
import com.virgo.financeloan.model.responce.RepayPlanData;
import com.virgo.financeloan.model.responce.RepayRecordData;
import com.virgo.financeloan.model.responce.TrialData;
import com.virgo.financeloan.mvp.contract.RepaymentPlanContract;
import com.virgo.financeloan.net.Repository;
import com.virgo.financeloan.net.rx.RxHelper;

import java.util.List;

/**
 * 功能说明： 还款计划请求
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/12/13
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class RepaymentPlanPresenter extends BasePresenter<Repository, RepaymentPlanContract.View> {

    /**
     * 还款计划
     */
    public void repaymentPlan(String version, String token, RecordReqVo recordReqVo) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().repaymentPlan(version, token, getRequestBody(recordReqVo))).callBack(new RxHelper
                .CallBackAdapter<BaseBean<List<RepayPlanData>>>() {
            @Override
            public void onSuccess(String response, BaseBean<List<RepayPlanData>> result) {
                getRootView().onSuccessRepaymentPlan(result.data);
            }

            @Override
            public void onFailure(String code, String error) {
                super.onFailure(code, error);
                getRootView().onFailureRepaymentPlan(code, error);
            }
        }).application(AppApplication.mApplication).start();
    }

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
     *  还款记录
     */
    public void repaymentRecord(String version, String token, RecordReqVo recordReqVo) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().repaymentRecord(version, token, getRequestBody(recordReqVo))).callBack(new RxHelper
                .CallBackAdapter<BaseBean<List<RepayRecordData>>>() {
            @Override
            public void onSuccess(String response, BaseBean<List<RepayRecordData>> result) {
                getRootView().onSuccessRepaymentRecord(result.data);
            }

            @Override
            public void onFailure(String code, String error) {
                super.onFailure(code, error);
                getRootView().onFailureRepaymentRecord(code, error);
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

}
