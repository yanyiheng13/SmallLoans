package com.virgo.financeloan.mvp;


import com.luck.picture.lib.entity.LocalMedia;
import com.sai.framework.mvp.BasePresenter;
import com.virgo.financeloan.AppApplication;
import com.virgo.financeloan.model.request.LoanApplyReqVo;
import com.virgo.financeloan.model.request.ProtocolContentReqVo;
import com.virgo.financeloan.model.request.ProtocolListReqVo;
import com.virgo.financeloan.model.responce.BaseBean;
import com.virgo.financeloan.model.responce.CardData;
import com.virgo.financeloan.model.responce.IsOkData;
import com.virgo.financeloan.model.responce.LoanOrderNoVo;
import com.virgo.financeloan.model.responce.ProtocolContentVo;
import com.virgo.financeloan.model.responce.ProtocolVo;
import com.virgo.financeloan.mvp.contract.LoanDetailContract;
import com.virgo.financeloan.net.Repository;
import com.virgo.financeloan.net.rx.RxHelper;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 功能说明： 即时货源列表
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/8/29
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class LoanDetailPresenter extends BasePresenter<Repository, LoanDetailContract.View> {
    /**
     * 贷款申请提交
     * @param loanApplyReqVo
     * @param version
     * @param token
     */
    public void applySubmit(LoanApplyReqVo loanApplyReqVo, String version, String token) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().applySubmit(getRequestBody(loanApplyReqVo), version, token)).callBack(new RxHelper
                .CallBackAdapter<BaseBean>() {
            @Override
            public void onSuccess(String response, BaseBean result) {
                getRootView().onSuccessApply(result);
            }

            @Override
            public void onFailure(String code, String error) {
                super.onFailure(code, error);
                getRootView().onFailureApply(code, error);
            }
        }).start();
    }

    /**
     * 账户列表
     * @param version
     * @param token
     */
    public void accountList(String version, String token) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().accountList(version, token)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<CardData>>() {
            @Override
            public void onSuccess(String response, BaseBean<CardData> result) {
                getRootView().onSuccessCardList(result.data);
            }

            @Override
            public void onFailure(String code, String error) {
                super.onFailure(code, error);
                getRootView().onFailureCardList(code, error);
            }
        }).application(AppApplication.mApplication).start();
    }

    /**
     * 协议列表
     * @param version
     * @param token
     * @param listReqVo
     */
    public void protocolList(String version, String token, ProtocolListReqVo listReqVo) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().protocolList(version, token, getRequestBody(listReqVo))).callBack(new RxHelper
                .CallBackAdapter<BaseBean<List<ProtocolVo>>>() {
            @Override
            public void onSuccess(String response, BaseBean<List<ProtocolVo>> result) {
                getRootView().onSuccessProtocol(result.data);
            }

            @Override
            public void onFailure(String code, String error) {
                super.onFailure(code, error);
                getRootView().onFailureProtocol(code, error);
            }
        }).application(AppApplication.mApplication).start();
    }

    /**
     * 协议内容
     * @param version
     * @param token
     * @param contentReqVo
     */
    public void protocolContent(String version, String token, ProtocolContentReqVo contentReqVo) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().protocolContent(version, token, getRequestBody(contentReqVo))).callBack(new RxHelper
                .CallBackAdapter<BaseBean<ProtocolContentVo>>() {
            @Override
            public void onSuccess(String response, BaseBean<ProtocolContentVo> result) {
                getRootView().onSuccessProtocolContent(result.data);
            }

            @Override
            public void onFailure(String code, String error) {
                super.onFailure(code, error);
                getRootView().onFailureProtocolContent(code, error);
            }
        }).application(AppApplication.mApplication).start();
    }

    /**
     * 订单号
     * @param version
     * @param token
     */
    public void getOrderNo(String version, String token) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().getOrderNo(version, token)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<LoanOrderNoVo>>() {
            @Override
            public void onSuccess(String response, BaseBean<LoanOrderNoVo> result) {
                getRootView().onSuccessOrderNo(result.data);
            }

            @Override
            public void onFailure(String code, String error) {
                super.onFailure(code, error);
                getRootView().onFailureOrderNo(code, error);
            }
        }).application(AppApplication.mApplication).start();
    }

    /**
     * 上传图片到资源服务器
     */
    public void uploadPic(String version, String token, List<LocalMedia> mediaList) {
        Map<String, RequestBody> map = new HashMap<>();
        if (mediaList != null && mediaList.size() > 0) {
            for (int i = 0; i < mediaList.size(); i++) {
                LocalMedia localMedia = mediaList.get(i);
                File file = new File(localMedia.getCompressPath());
                RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
                map.put("img" + (i + 1) + "\";filename=\"" + file.getName(), body);
            }
        }
        new RxHelper().view(getRootView()).load(getModel().getRemote().uploadPic(version, token, map)).application(AppApplication.mApplication).callBack(new RxHelper
                .CallBackAdapter<BaseBean>() {
            @Override
            public void onSuccess(String response, BaseBean result) {
                getRootView().onSuccessPic(result);
            }

            @Override
            public void onFailure(String code, String msg) {
                super.onFailure(code, msg);
                getRootView().onFailurePic(code, msg);
            }
        }).start();
    }

    public RequestBody toRequestBody(String value) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        return requestBody;
    }

    public static RequestBody toRequestBodyOfImage(File pFile) {
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), pFile);
        return fileBody;
    }


}
