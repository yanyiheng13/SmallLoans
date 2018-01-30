package com.virgo.financeloan.user.presenter;


import com.sai.framework.mvp.BasePresenter;
import com.virgo.financeloan.AppApplication;
import com.virgo.financeloan.user.model.request.AddCardReqVo;
import com.virgo.financeloan.model.responce.BaseBean;
import com.virgo.financeloan.user.model.response.CardData;
import com.virgo.financeloan.mvp.contract.LoanDetailContract;
import com.virgo.financeloan.net.Repository;
import com.virgo.financeloan.net.rx.RxHelper;
import com.virgo.financeloan.user.presenter.contract.CardListContract;

/**
 * 功能说明： 银行卡列表请求
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/12/12
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class CardListPresenter extends BasePresenter<Repository, CardListContract.View> {
    /**
     * 银行卡列表
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
     * 删除银行卡信息
     */
    public void deleteAccount(String version, String token, AddCardReqVo cardReqVo) {
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
