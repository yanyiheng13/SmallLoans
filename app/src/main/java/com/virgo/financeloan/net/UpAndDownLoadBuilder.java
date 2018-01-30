package com.virgo.financeloan.net;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.google.gson.Gson;
import com.virgo.financeloan.AppApplication;
import com.virgo.financeloan.BuildConfig;
import com.virgo.financeloan.model.request.GetFileBytesReqVo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * 上传文件，下载文件
 */
public class UpAndDownLoadBuilder {
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/octet-stream, charset=utf-8");
    private static final int mFailure = 1;
    private static final int mSuccess = 2;
    private static final int mProgress = 3;
    private static OnResponseCallBack mResponseCallBack;


    private static Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case mFailure:
                    if (mResponseCallBack != null)
                        mResponseCallBack.onFailure();
                    break;
                case mSuccess:
                    if (mResponseCallBack != null)
                        mResponseCallBack.onSuccess(null);
                    break;
                case mProgress:
                    if (mResponseCallBack != null)
                        mResponseCallBack.onProgress(msg.arg1, (Long) msg.obj);
                    break;
            }
            super.handleMessage(msg);

        }
    };

    public UpAndDownLoadBuilder() {

    }

    public void upFileStream(byte[] file, String url, final String tag, final OnResultCallBack2 callBack2) {
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
                .build();

        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callBack2 != null) {
                    callBack2.onFailure();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (callBack2 != null && response != null) {
                        callBack2.onSuccess(response.body().string(), tag);
                    }
                }
            }
        });


    }

    public static void downFile(GetFileBytesReqVo reqVo, final String path, final String fileName, final OnResponseCallBack callBack) {
        mResponseCallBack = callBack;
        String url = BuildConfig.api_url + "/file/download/v1/" + AppApplication.getUserData().getToken();
        RequestBody requestBody = RequestBody.create( MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(reqVo));
        final Request request = new Request.Builder().url(url).post(requestBody).build();
        OkHttpClient client = new OkHttpClient.Builder().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callBack != null) {
                    handler.sendEmptyMessage(mFailure);
                }
            }

            @Override
            public void onResponse(Call call, Response response) {
                if (callBack == null) {
                    return;
                }
                if (response == null || !response.isSuccessful()) {
                    handler.sendEmptyMessage(mFailure);
                    return;
                }
                InputStream is = response.body().byteStream();
                if (is == null) {
                    handler.sendEmptyMessage(mFailure);
                    return;
                }
                long contentLength = response.body().contentLength();
                if (contentLength == 0) {
                    handler.sendEmptyMessage(mFailure);
                    return;
                }

                File file = new File(path);
                //不支持续传
                if (!file.exists()) {
                    file.mkdirs();
                }
                File file1 = new File(path + fileName);
                OutputStream os = null;
                try {
                    os = new FileOutputStream(file1);
                    byte[] b = new byte[2048];
                    int readSize, readCount = 0;
                    while ((readSize = is.read(b)) != -1) {
                        readCount += readSize;
                        os.write(b, 0, readSize);
                        callBack.onProgress(readCount, contentLength);
                        Message message = Message.obtain();
                        message.arg1 = readCount;
                        message.what = mProgress;
                        message.obj = contentLength;
                        handler.sendMessage(message);
                    }
                    b = null;
                    if (readCount == contentLength) {
                        handler.sendEmptyMessage(mSuccess);
                    } else {
                        handler.sendEmptyMessage(mFailure);
                    }

                } catch (Exception e) {
                    handler.sendEmptyMessage(mFailure);
                } finally {
                    try {
                        if (os != null)
                            os.close();
                        is.close();
                        os = null;
                        is = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                        handler.sendEmptyMessage(mFailure);
                    }
                }
            }
        });
    }

    public OnResultCallBack callBack;

    public interface OnResultCallBack {
        void onSuccess(String result);

        void onFailure();
    }

    public OnResultCallBack2 callBack2;

    public interface OnResultCallBack2 {
        void onSuccess(String result, String tag);

        void onFailure();
    }


    public interface OnResponseCallBack extends OnResultCallBack {
        void onProgress(long down, long size);
    }


    public RequestBody create(final MediaType mediaType, final InputStream inputStream) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return mediaType;
            }

            @Override
            public long contentLength() {
                try {
                    return inputStream.available();
                } catch (IOException e) {
                    return 0;
                }
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source = null;
                try {
                    source = Okio.source(inputStream);
                    sink.writeAll(source);
                } finally {
                    Util.closeQuietly(source);
                }
            }
        };
    }
}
