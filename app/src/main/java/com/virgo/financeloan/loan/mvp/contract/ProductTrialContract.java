package com.virgo.financeloan.loan.mvp.contract;


import com.sai.framework.mvp.MvpView;
import com.virgo.financeloan.model.responce.BaseBean;
import com.virgo.financeloan.model.responce.ProtocolContentVo;
import com.virgo.financeloan.loan.model.response.ProtocolItemVo;
import com.virgo.financeloan.model.responce.TrialData;

import java.util.List;
import java.util.Map;

/**
 * 功能说明：还款计划
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/12/14
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class ProductTrialContract {

    public interface View extends MvpView {
        void onSuccessRepaymentTrial(TrialData trialData);
        void onFailureRepaymentTrial(String code, String msg);

        void onSuccessConfirm(BaseBean baseBean);
        void onFailureConfirm(String code, String msg);

        void onSuccessProtocol(List<ProtocolItemVo> voList);
        void onFailureProtocol(String code, String msg);

        void onSuccessProtocolContent(ProtocolContentVo contentVo);
        void onFailureProtocolContent(String code, String msg);

        void onSuccessMap(Map<String, String> map);
        void onFailureMap(String code, String msg);
    }
}
