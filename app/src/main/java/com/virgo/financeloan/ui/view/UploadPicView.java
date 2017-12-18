package com.virgo.financeloan.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.virgo.financeloan.AppApplication;
import com.virgo.financeloan.R;
import com.virgo.financeloan.net.FileRequestBody;
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

    public UploadPicView setProgress(int progress){
        mUploadPicView.setProgress(progress);
        return this;
    };

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

    RetrofitCallback<String> callback = new RetrofitCallback<String>() {
        @Override
        public void onSuccess(Call<String> call, final Response<String> response) {
//                ((Activity) getContext()).runOnUIThread(getContext(), response.body().toString());
//                //进度更新结束
            UploadPicView.this.post(new Runnable() {
                @Override
                public void run() {
                    Log.d("tag_tag", response + "==");
                }
            });
        }

        @Override
        public void onFailure(final Call<String> call, Throwable t) {
            UploadPicView.this.post(new Runnable() {
                @Override
                public void run() {
                    Log.d("tag_tag", call + "==");
                }
            });
//                runOnUIThread(getContext(), t.getMessage());
//                //进度更新结束
        }

        @Override
        public void onLoading(final long total, final long progress) {
//            super.onLoading(total, progress);
            //此处进行进度更新
            UploadPicView.this.post(new Runnable() {
                @Override
                public void run() {
                    Log.d("tag_tag", "progress == " + progress);
                    mUploadPicView.setProgress((int)(progress/(total * 1.00) * 100));
                }
            });
        }
    };

    protected void onLoad(String progress) {

    }

    public void upPic(String filePath, String orderNo) {
        File file = new File(filePath);

        Map<String, RequestBody> hashMap = new HashMap<>();
        hashMap.put("fileType", toRequestBody("100302"));
        hashMap.put("loanOrderNo", toRequestBody(orderNo));
        hashMap.put("operateType", toRequestBody("0"));
        hashMap.put("order", toRequestBody("6"));

        RequestBody body1 = RequestBody.create(MediaType.parse("image/*"), file);

        //通过该行代码将RequestBody转换成特定的FileRequestBody
        FileRequestBody body = new FileRequestBody(body1, callback);

        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), body1);

        Call<String> call = Repository.get().getRemote().uploadFile(UniqueKey.VERSION.V1, AppApplication.getUserData().getToken(), hashMap, part);
        call.enqueue(callback);
    }

    protected RequestBody getRequestBody(Object obj) {
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(obj));
    }

    public RequestBody toRequestBody(String value) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        return requestBody;
    }
}
