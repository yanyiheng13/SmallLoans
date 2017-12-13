package com.virgo.financeloan.mvp.contract;


import com.virgo.financeloan.model.responce.WaybillData;
import com.sai.framework.mvp.MvpView;

/**
 * 功能说明：车辆列表
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/8/29
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class WaybillContract {

    public interface View extends MvpView {
        void onSuccessWaybill(WaybillData waybillData);
        void onFailureWaybill(String code, String msg);
    }
}
