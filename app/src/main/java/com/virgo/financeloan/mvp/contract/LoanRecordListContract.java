package com.virgo.financeloan.mvp.contract;


import com.virgo.financeloan.model.responce.BaseBean;
import com.virgo.financeloan.model.responce.LoanRecordVo;
import com.sai.framework.mvp.MvpView;

import java.util.List;

/**
 * 功能说明：订单列表
 *
 * @author: 闫毅恒
 * @email： yanyiheng86@163.com
 * @version: 1.0
 * @date: 2017/11/26
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class LoanRecordListContract {

    public interface View extends MvpView {
        void onSuccessConfirm(BaseBean baseBean);
        void onFailureConfirm(String code, String msg);

        void onSuccessLoanRecodeList(List<LoanRecordVo> loanRecordRespVo);
        void onFailureLoanRecodeList(String code, String msg);
    }
}
