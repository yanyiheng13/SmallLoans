package com.virgo.financeloan.mvp.contract;


import com.sai.framework.mvp.MvpView;
import com.virgo.financeloan.model.responce.BaseBean;
import com.virgo.financeloan.user.model.response.CardData;
import com.virgo.financeloan.model.responce.LoanOrderNoVo;
import com.virgo.financeloan.model.responce.UploadFileVo;

import java.util.List;

/**
 * 功能说明：借款申请详情页
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/12/13
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class LoanDetailContract {

    public interface View extends MvpView {
        void onSuccessCardList(CardData cardData);
        void onFailureCardList(String code, String msg);

        void onSuccessApply(BaseBean baseBean);
        void onFailureApply(String code, String msg);

        void onSuccessOrderNo(LoanOrderNoVo loanOrderNoVo);
        void onFailureOrderNo(String code, String msg);

        void onSuccessPicList(List<UploadFileVo> uploadFileVos);
        void onFailurePicList(String code, String msg);
    }
}
