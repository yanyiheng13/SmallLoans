package com.virgo.financeloan.mvp.contract;


import com.sai.framework.mvp.MvpView;
import com.virgo.financeloan.model.responce.IsOkData;
import com.virgo.financeloan.model.responce.PictureData;
import com.virgo.financeloan.model.responce.UserData;

/**
 * 功能说明：图片详情
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/8/29
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class PicDetailContract {

    public interface View extends MvpView {

        void onSuccessData(PictureData pictureData);
        void onFailureData(String code, String msg);

    }
}
