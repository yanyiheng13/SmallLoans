package com.virgo.financeloan.mvp.contract;


import com.virgo.financeloan.model.responce.BaseBean;
import com.sai.framework.mvp.MvpView;

/**
 * 功能说明：添加银行卡信息
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/12/13
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class AddCardContract {

    public interface View extends MvpView {
        void onSuccess(BaseBean baseBean);
        void onFailure(String code, String msg);
    }
}
