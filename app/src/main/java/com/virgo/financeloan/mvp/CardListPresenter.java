package com.virgo.financeloan.mvp;


import com.sai.framework.mvp.BasePresenter;
import com.virgo.financeloan.AppApplication;
import com.virgo.financeloan.model.request.AddBankCardReqVo;
import com.virgo.financeloan.model.responce.BaseBean;
import com.virgo.financeloan.model.responce.CardData;
import com.virgo.financeloan.mvp.contract.LoanDetailContract;
import com.virgo.financeloan.net.Repository;
import com.virgo.financeloan.net.rx.RxHelper;

/**
 * 功能说明： 即时货源列表
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/8/29
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class CardListPresenter extends BasePresenter<Repository, LoanDetailContract.ViewList> {


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

    public void deleteAccount(String version, String token, AddBankCardReqVo cardReqVo) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().deleteAccount(version, token, getRequestBody(cardReqVo))).callBack(new RxHelper
                .CallBackAdapter<BaseBean>() {
            @Override
            public void onSuccess(String response, BaseBean result) {
                getRootView().onSuccessDelete(result);
            }

            @Override
            public void onFailure(String code, String error) {
                super.onFailure(code, error);
                getRootView().onFailureDelete(code, error);
            }
        }).application(AppApplication.mApplication).start();
    }

}
