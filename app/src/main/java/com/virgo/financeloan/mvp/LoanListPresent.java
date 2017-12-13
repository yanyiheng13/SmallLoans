package com.virgo.financeloan.mvp;


import com.virgo.financeloan.model.responce.BaseBean;
import com.virgo.financeloan.model.responce.LoanData;
import com.virgo.financeloan.model.responce.LoanVo;
import com.virgo.financeloan.mvp.contract.LoanListContract;
import com.virgo.financeloan.net.Repository;
import com.virgo.financeloan.net.rx.RxHelper;
import com.sai.framework.mvp.BasePresenter;

import java.util.List;

/**
 * 功能说明： 小贷产品列表
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/11/20
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class LoanListPresent extends BasePresenter<Repository, LoanListContract.View> {

    public void loanList(String version) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().loanList(version)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<List<LoanVo>>>() {
            @Override
            public void onSuccess(String response, BaseBean<List<LoanVo>> result) {
                getRootView().onSuccessLoadList(result.data);
            }

            @Override
            public void onFailure(String code, String error) {
                super.onFailure(code, error);
                getRootView().onFailureLoadList(code, error);
            }
        }).start();
    }

}
