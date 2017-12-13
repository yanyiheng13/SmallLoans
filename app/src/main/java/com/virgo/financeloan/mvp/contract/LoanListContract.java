package com.virgo.financeloan.mvp.contract;


import com.virgo.financeloan.model.responce.LoanData;
import com.sai.framework.mvp.MvpView;
import com.virgo.financeloan.model.responce.LoanVo;

import java.util.List;

/**
 * 功能说明：车辆列表
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/8/29
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class LoanListContract {
    public interface View extends MvpView {
        void onSuccessLoadList(List<LoanVo> loanVos);
        void onFailureLoadList(String code, String msg);
    }
}
