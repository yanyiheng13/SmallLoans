package com.virgo.financeloan.loan.mvp;


import com.sai.framework.mvp.BasePresenter;
import com.virgo.financeloan.AppApplication;
import com.virgo.financeloan.model.request.LoanApplyReqVo;
import com.virgo.financeloan.model.request.QueryUploadFileReqVo;
import com.virgo.financeloan.model.responce.BaseBean;
import com.virgo.financeloan.user.model.response.CardData;
import com.virgo.financeloan.model.responce.LoanOrderNoVo;
import com.virgo.financeloan.model.responce.UploadFileVo;
import com.virgo.financeloan.mvp.contract.LoanDetailContract;
import com.virgo.financeloan.net.Repository;
import com.virgo.financeloan.net.rx.RxHelper;

import java.util.List;

/**
 * 功能说明： 贷款详情
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/11/19
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class LoanDetailPresenter extends BasePresenter<Repository, LoanDetailContract.View> {
    /**
     * 贷款申请提交
     * @param loanApplyReqVo
     * @param version
     * @param token
     */
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

    /**
     * 查询上传资料列表
     * @param version
     * @param token
     */
    public void upDataList(QueryUploadFileReqVo uploadFileReqVo, String version, String token) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().upDataList(version, token, getRequestBody(uploadFileReqVo))).callBack(new RxHelper
                .CallBackAdapter<BaseBean<List<UploadFileVo>>>() {
            @Override
            public void onSuccess(String response,BaseBean<List<UploadFileVo>> list) {
                getRootView().onSuccessPicList(list.data);
            }

            @Override
            public void onFailure(String code, String error) {
                super.onFailure(code, error);
                getRootView().onFailurePicList(code, error);
            }
        }).start();
    }

    /**
     * 账户列表
     * @param version
     * @param token
     */
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

    /**
     * 订单号
     * @param version
     * @param token
     */
    public void getOrderNo(String version, String token) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().getOrderNo(version, token)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<LoanOrderNoVo>>() {
            @Override
            public void onSuccess(String response, BaseBean<LoanOrderNoVo> result) {
                getRootView().onSuccessOrderNo(result.data);
            }

            @Override
            public void onFailure(String code, String error) {
                super.onFailure(code, error);
                getRootView().onFailureOrderNo(code, error);
            }
        }).application(AppApplication.mApplication).start();
    }
}
