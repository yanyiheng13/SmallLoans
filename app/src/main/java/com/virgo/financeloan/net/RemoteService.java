package com.virgo.financeloan.net;


import com.jakewharton.retrofit2.adapter.rxjava2.Result;
import com.virgo.financeloan.model.responce.BaseBean;
import com.virgo.financeloan.model.responce.UpDataBean;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

/**
 * 功能说明：
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-24 上午12:15
 * @Copyright (c) 2017. Inc. All rights reserved.
 */
public interface RemoteService {
    // 登录接口
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("user/login/{version}")
    Flowable<ResponseBody> login(@Body RequestBody body, @Path("version")String version);
    //发送短信验证码
    @POST("user/sendSecurityCode/{version}/{token}/{smsType}")
    Flowable<ResponseBody> sendCode(@Path("version")String version, @Path("token")String token, @Path("smsType") String smsType);
    //修改密码
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("user/changePassword/{version}/{token}")
    Flowable<ResponseBody> changePw(@Body RequestBody body, @Path("version") String version, @Path("token") String token);
    //小贷产品列表
    @POST("loan/listProduct/{version}")
    Flowable<ResponseBody> loanList(@Path("version")String version);
    //我的借款列表 待用户确认或者取消
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("mine/loan/userConfirm/submit/{version}/{token}")
    Flowable<ResponseBody> orderConfirm(@Body RequestBody body, @Path("version") String version, @Path("token") String token);
    //提交贷款申请
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("loan/loanDetail/apply/submit/{version}/{token}")
    Flowable<ResponseBody> applySubmit(@Body RequestBody body, @Path("version") String version, @Path("token") String token);
    //试算列表
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("loan/loanDetail/trial/{version}")
    Flowable<ResponseBody> trialList(@Body RequestBody body, @Path("version") String version);
    //订单列表 全部信息
    @POST("mine/loan/record/full/{version}/{token}")
    Flowable<ResponseBody> orderFullList(@Path("version") String version, @Path("token") String token);
    //订单列表 需要用户确认
    @POST("mine/loan/record/toUserConfirm/{version}/{token}")
    Flowable<ResponseBody> orderConfirmList(@Path("version") String version, @Path("token") String token);
    //账户列表信息
    @POST("loan/loanDetail/apply/account/query/{version}/{token}")
    Flowable<ResponseBody> accountList(@Path("version") String version, @Path("token") String token);
    //
    @POST("loan/loanDetail/apply/account/add/{version}/{token}")
    Flowable<ResponseBody> addAccount(@Path("version") String version, @Path("token") String token, @Body RequestBody body);
    //删除银行卡信息
    @POST("loan/loanDetail/apply/account/remove/{version}/{token}")
    Flowable<ResponseBody> deleteAccount(@Path("version") String version, @Path("token") String token, @Body RequestBody body);
    //协议列表
    @POST("contract/productBase/list/{version}/{token}")
    Flowable<ResponseBody> protocolList(@Path("version") String version, @Path("token") String token, @Body RequestBody body);
    //协议内容
    @POST("contract/productBase/template/{version}/{token}")
    Flowable<ResponseBody> protocolContent(@Path("version") String version, @Path("token") String token, @Body RequestBody body);
    //协议map
    @POST("contract/productBase/fill/{version}/{token}")
    Flowable<ResponseBody> mapInfo(@Path("version") String version, @Path("token") String token, @Body RequestBody body);
    //获取订单号
    @POST("loan/loanDetail/apply/getLoanOrderNo/{version}/{token}")
    Flowable<ResponseBody> getOrderNo(@Path("version") String version, @Path("token") String token);
    //上传图片信息
    @Multipart
    @POST("loan/loanDetail/apply/upload/submit/{version}/{token}")
    Flowable<ResponseBody> uploadPic(@Path("version") String version, @Path("token") String token, @PartMap() Map<String, RequestBody> requestBodyMap);
    //贷款明细
    @POST("mine/loan/record/detail/index/{version}/{token}")
    Flowable<ResponseBody> loadRecordDetail(@Path("version") String version, @Path("token") String token, @Body RequestBody body);
    //还款计划
    @POST("mine/loan/record/repaymentPlan/{version}/{token}")
    Flowable<ResponseBody> repaymentPlan(@Path("version") String version, @Path("token") String token, @Body RequestBody body);
    //还款记录列表
    @POST("mine/loan/record/repaymentRecord/{version}/{token}")
    Flowable<ResponseBody> repaymentRecord(@Path("version") String version, @Path("token") String token, @Body RequestBody body);
    //我的页面-待用户确认-用户贷款确认试算.
    @POST("mine/loan/userConfirm/trial/{version}/{token}")
    Flowable<ResponseBody> repaymentTrial(@Path("version") String version, @Path("token") String token, @Body RequestBody body);
    //上传资料列表
    @POST("loan/loanDetail/apply/upload/list/{version}/{token}")
    Flowable<ResponseBody> upDataList(@Path("version") String version, @Path("token") String token, @Body RequestBody body);
    //资料图片详情
    @POST("file/getBytes/{version}/{token}")
    Flowable<ResponseBody> dataDetail(@Path("version") String version, @Path("token") String token, @Body RequestBody body);

    /**
     * 上传图片
     * @return
     */
    @Multipart
    @POST("loan/loanDetail/apply/upload/submit/{version}/{token}")
    Call<UpDataBean> uploadFile(@Path("version") String version, @Path("token") String token, @PartMap() Map<String, RequestBody> requestBodyMap, @Part() MultipartBody.Part file);


}
