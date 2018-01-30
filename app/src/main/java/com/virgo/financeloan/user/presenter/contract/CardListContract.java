package com.virgo.financeloan.user.presenter.contract;


import com.sai.framework.mvp.MvpView;
import com.virgo.financeloan.model.responce.BaseBean;
import com.virgo.financeloan.user.model.response.CardData;

/**
 * 功能说明：银行卡列表和删除银行卡
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/12/13
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class CardListContract {

    public interface View extends MvpView {
        void onSuccessCardList(CardData cardData);
        void onFailureCardList(String code, String msg);

        void onSuccessDelete(BaseBean baseBean);
        void onFailureDelete(String code, String msg);
    }
}
