package com.virgo.financeloan.mvp.contract;


import com.sai.framework.mvp.MvpView;
import com.virgo.financeloan.model.responce.LoanRecordDetailData;
import com.virgo.financeloan.model.responce.ProtocolContentVo;

/**
 * 功能说明：贷款明细详情页面
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/12/14
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class LoanRecordDetailContract {

    public interface View extends MvpView {
        void onSuccessDetail(LoanRecordDetailData detailData);
        void onFailureDetail(String code, String msg);

        void onSuccessProtocolContent(ProtocolContentVo contentVo);
        void onFailureProtocolContent(String code, String msg);
    }
}
