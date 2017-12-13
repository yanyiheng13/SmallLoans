package com.virgo.financeloan.mvp;


import com.virgo.financeloan.AppApplication;
import com.virgo.financeloan.model.responce.BaseBean;
import com.virgo.financeloan.model.responce.WaybillData;
import com.virgo.financeloan.mvp.contract.WaybillContract;
import com.virgo.financeloan.net.Repository;
import com.virgo.financeloan.net.rx.RxHelper;
import com.sai.framework.mvp.BasePresenter;

/**
 * 功能说明： 车辆列表
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/8/29
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class WaybillPresent extends BasePresenter<Repository, WaybillContract.View> {

//    public void waybillList(int currentPageNum, int rows) {
//        new RxHelper().view(getRootView()).load(getModel().getRemote().applySubmit("v1", currentPageNum, rows)).callBack(new RxHelper
//                .CallBackAdapter<BaseBean<WaybillData>>() {
//            @Override
//            public void onSuccess(String response, BaseBean<WaybillData> result) {
//                getRootView().onSuccessWaybill(result.data);
//            }
//
//            @Override
//            public void onFailure(String error) {
//                super.onFailure(error);
//                getRootView().onFailureWaybill(error, error);
//            }
//        }).application(AppApplication.mApplication).start();
//    }
}
