package com.virgo.financeloan.mvp.contract;


import com.virgo.financeloan.model.responce.IsOkData;
import com.sai.framework.mvp.MvpView;
import com.virgo.financeloan.model.responce.TrialData;

/**
 * 功能说明：登录
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/8/29
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class TrialContract {

    public interface View extends MvpView {
        void onSuccessTrail(TrialData trialData);
        void onFailureTrial(String code, String msg);
    }
}
