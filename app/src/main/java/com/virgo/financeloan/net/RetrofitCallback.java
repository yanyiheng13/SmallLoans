package com.virgo.financeloan.net;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 功能说明：上传图片回调
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/17 14:48
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
public abstract class RetrofitCallback <T> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.isSuccessful()) {
            onSuccess(call, response);
        } else {
            onFailure(call, new Throwable(response.message()));
        }
    }
    public abstract void onSuccess(Call<T> call, Response<T> response);
    //用于进度的回调
    public abstract void onLoading(long total, long progress) ;

}
