package com.virgo.financeloan.mvp;


import com.luck.picture.lib.entity.LocalMedia;
import com.sai.framework.mvp.BasePresenter;
import com.virgo.financeloan.AppApplication;
import com.virgo.financeloan.model.request.LoanApplyReqVo;
import com.virgo.financeloan.model.request.ProtocolContentReqVo;
import com.virgo.financeloan.model.request.ProtocolListReqVo;
import com.virgo.financeloan.model.request.RecordReqVo;
import com.virgo.financeloan.model.responce.BaseBean;
import com.virgo.financeloan.model.responce.CardData;
import com.virgo.financeloan.model.responce.LoanOrderNoVo;
import com.virgo.financeloan.model.responce.LoanRecordDetailData;
import com.virgo.financeloan.model.responce.ProtocolContentVo;
import com.virgo.financeloan.model.responce.ProtocolVo;
import com.virgo.financeloan.mvp.contract.LoanDetailContract;
import com.virgo.financeloan.mvp.contract.LoanRecordDetailContract;
import com.virgo.financeloan.net.Repository;
import com.virgo.financeloan.net.rx.RxHelper;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 功能说明： 贷款明细详情
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/8/29
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class LoanRecordDetailPresenter extends BasePresenter<Repository, LoanRecordDetailContract.View> {
    /**
     * 贷款记录明细
     */
    public void loadRecordDetail(String version, String token, RecordReqVo recordReqVo) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().loadRecordDetail(version, token, getRequestBody(recordReqVo))).callBack(new RxHelper
                .CallBackAdapter<BaseBean<LoanRecordDetailData>>() {
            @Override
            public void onSuccess(String response, BaseBean<LoanRecordDetailData> result) {
                getRootView().onSuccessDetail(result.data);
            }

            @Override
            public void onFailure(String code, String error) {
                super.onFailure(code, error);
                getRootView().onFailureDetail(code, error);
            }
        }).start();
    }

}
