package com.virgo.financeloan.mvp;


import com.virgo.financeloan.AppApplication;
import com.virgo.financeloan.model.request.AddBankCardReqVo;
import com.virgo.financeloan.model.responce.BaseBean;
import com.virgo.financeloan.model.responce.CurrentGoods;
import com.virgo.financeloan.mvp.contract.AddCardContract;
import com.virgo.financeloan.net.Repository;
import com.virgo.financeloan.net.rx.RxHelper;
import com.sai.framework.mvp.BasePresenter;

/**
 * 功能说明： 即时货源列表
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/12/13
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class AddCardPresenter extends BasePresenter<Repository, AddCardContract.View> {

    public void addAccount(String version, String token, AddBankCardReqVo addBankCardReqVo) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().addAccount(version, token, getRequestBody(addBankCardReqVo))).callBack(new RxHelper
                .CallBackAdapter<BaseBean>() {
            @Override
            public void onSuccess(String response, BaseBean result) {
                getRootView().onSuccess(result);
            }

            @Override
            public void onFailure(String code, String error) {
                super.onFailure(code, error);
                getRootView().onFailure(code, error);
            }
        }).application(AppApplication.mApplication).start();
    }

}
