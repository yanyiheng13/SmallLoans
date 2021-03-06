package com.virgo.financeloan.mvp.contract;


import com.sai.framework.mvp.MvpView;
import com.virgo.financeloan.model.responce.BaseBean;
import com.virgo.financeloan.model.responce.RepayPlanData;
import com.virgo.financeloan.model.responce.RepayRecordData;
import com.virgo.financeloan.model.responce.TrialData;

import java.util.List;

/**
 * 功能说明：还款计划
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/12/14
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class RepaymentPlanContract {

    public interface View extends MvpView {
        void onSuccessRepaymentRecord(List<RepayRecordData> repayRecordData);
        void onFailureRepaymentRecord(String code, String msg);

        void onSuccessRepaymentPlan(List<RepayPlanData> repayPlanData);
        void onFailureRepaymentPlan(String code, String msg);

        void onSuccessRepaymentTrial(TrialData trialData);
        void onFailureRepaymentTrial(String code, String msg);

        void onSuccessConfirm(BaseBean baseBean);
        void onFailureConfirm(String code, String msg);
    }
}
