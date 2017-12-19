package com.virgo.financeloan.mvp;

import com.sai.framework.mvp.BasePresenter;
import com.virgo.financeloan.model.request.GetFileBytesReqVo;
import com.virgo.financeloan.model.responce.BaseBean;
import com.virgo.financeloan.model.responce.PictureData;
import com.virgo.financeloan.mvp.contract.PicDetailContract;
import com.virgo.financeloan.net.Repository;
import com.virgo.financeloan.net.rx.RxHelper;

/**
 * 功能说明：图片详情
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/19 16:24
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */

public class PicturePrePresenter extends BasePresenter<Repository, PicDetailContract.View> {

    public void dataDetail(String version, String token, GetFileBytesReqVo reqVo) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().dataDetail(version, token, getRequestBody(reqVo))).callBack(new RxHelper
                .CallBackAdapter<BaseBean<PictureData>>() {
            @Override
            public void onSuccess(String response, BaseBean<PictureData> result) {
                getRootView().onSuccessData(result.data);
            }

            @Override
            public void onFailure(String code, String error) {
                super.onFailure(code, error);
                getRootView().onFailureData(code, error);
            }
        }).start();
    }
}
