package com.virgo.financeloan.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.virgo.financeloan.AppApplication;
import com.virgo.financeloan.R;
import com.virgo.financeloan.model.responce.UpDataBean;
import com.virgo.financeloan.net.RemoteService;
import com.virgo.financeloan.net.Repository;
import com.virgo.financeloan.net.RetrofitCallback;
import com.virgo.financeloan.util.UniqueKey;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;
import lombok.Setter;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 功能说明：上传图片自定义view
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 17-12-13
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
public class UploadPicView extends RelativeLayout {

    @BindView(R.id.up_helper_view)
    UploadPicHelperView mUploadPicView;

    @BindView(R.id.image_pic)
    ImageView mUpImgView;

    /**
     * 图片Id
     */
    @Getter
    @Setter
    private String picId;

    private boolean isUping;

    public UploadPicView(Context context) {
        this(context, null);
    }

    public UploadPicView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UploadPicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_upload_pic, this);
        ButterKnife.bind(this, this);
    }

    public void setUping(boolean isUping) {
        this.isUping = isUping;
    }

    public boolean getUping() {
        return isUping;
    }

    /**
     * 设置显示的view
     */
    public UploadPicView setImageResource(int ids) {
        if (ids != 0) {
            mUpImgView.setImageResource(ids);
        }
        return this;
    }

    public UploadPicView setProgress(int progress) {
        mUploadPicView.setProgress(progress);
        return this;
    }

    public UploadPicView setError() {
        mUploadPicView.setError();
        return this;
    }

    public UploadPicView setIsShow(boolean isShow) {
        mUploadPicView.setIsShow(isShow);
        return this;
    }

    public ImageView imgView() {
        return mUpImgView;
    }

    public static final String BASE_URL = "http://39.106.24.18:8067/";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    /**
     * 添加图片
     */
    public void addPic(String filePath, String orderNo, String fileType, String position) {
        File file = new File(filePath);
        Map<String, RequestBody> hashMap = new HashMap<>();
        hashMap.put("fileType", toRequestBody(fileType));
        hashMap.put("loanOrderNo", toRequestBody(orderNo));
        hashMap.put("operateType", toRequestBody("0"));
        hashMap.put("order", toRequestBody(position));
        RequestBody body1 = RequestBody.create(MediaType.parse("image/*"), file);
        //通过该行代码将RequestBody转换成特定的FileRequestBody
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), body1);
        //获取接口实例
        RemoteService movieService = retrofit.create(RemoteService.class);
        Call<UpDataBean> call = movieService.uploadFile(UniqueKey.VERSION.V1, AppApplication.getUserData().getToken(), hashMap, part);
        call.enqueue(callback);
    }

    /**
     * 删除图片
     */
    public void deletePic(String orderNo, String fileType, String position) {
        Map<String, RequestBody> hashMap = new HashMap<>();
        hashMap.put("fileType", toRequestBody(fileType));
        hashMap.put("loanOrderNo", toRequestBody(orderNo));
        hashMap.put("operateType", toRequestBody("2"));
        hashMap.put("order", toRequestBody(position));

        //获取接口实例
        RemoteService movieService = retrofit.create(RemoteService.class);
        Call<UpDataBean> call = Repository.get().getRemote().uploadFile(UniqueKey.VERSION.V1, AppApplication.getUserData().getToken(), hashMap, null);
        call.enqueue(callbackDelete);
    }

    /**
     * 删除图片的回调
     */
    RetrofitCallback<UpDataBean> callbackDelete = new RetrofitCallback<UpDataBean>() {
        @Override
        public void onSuccess(Call<UpDataBean> call, final Response<UpDataBean> response) {
            UploadPicView.this.post(new Runnable() {
                @Override
                public void run() {
                    UpDataBean dataBean = response.body();
                    if (dataBean != null && dataBean.isSuccess()) {
                    } else {
                        setError();
                    }
                }
            });
        }

        @Override
        public void onFailure(final Call<UpDataBean> call, final Throwable t) {
            UploadPicView.this.post(new Runnable() {
                @Override
                public void run() {
                    setError();
                }
            });
        }
    };

    /**
     * 添加图片的回调
     */
    RetrofitCallback<UpDataBean> callback = new RetrofitCallback<UpDataBean>() {
        @Override
        public void onSuccess(Call<UpDataBean> call, final Response<UpDataBean> response) {
            UploadPicView.this.post(new Runnable() {
                @Override
                public void run() {
                    UpDataBean dataBean = response.body();
                    if (dataBean != null && dataBean.isSuccess()) {
                        setProgress(100);
                        picId = dataBean.getData();
                        isUping = false;
                    } else {
                        setError();
                    }
                }
            });
        }

        @Override
        public void onFailure(final Call<UpDataBean> call, final Throwable t) {
            UploadPicView.this.post(new Runnable() {
                @Override
                public void run() {
                    setError();
                }
            });
        }
    };

    public RequestBody toRequestBody(String value) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        return requestBody;
    }
}
