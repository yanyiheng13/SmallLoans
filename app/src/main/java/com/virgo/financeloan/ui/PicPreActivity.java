package com.virgo.financeloan.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.virgo.financeloan.AppApplication;
import com.virgo.financeloan.R;
import com.virgo.financeloan.model.request.GetFileBytesReqVo;
import com.virgo.financeloan.model.responce.PictureData;
import com.virgo.financeloan.mvp.PicturePrePresenter;
import com.virgo.financeloan.mvp.contract.PicDetailContract;
import com.virgo.financeloan.ui.view.EmptyView;
import com.virgo.financeloan.util.BitmapAndStringUtils;
import com.virgo.financeloan.util.UniqueKey;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;

/**
 * 功能说明：图片预览界面
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/19 15:52
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */

public class PicPreActivity extends BaseActivity<PicturePrePresenter> implements PicDetailContract.View, EmptyView.OnDataLoadStatusListener {
    @BindView(R.id.empty_view)
    EmptyView mEmptyView;
    @BindView(R.id.photoView)
    PhotoView mPhotoView;

    private String mPath;
    private Boolean isByte;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mPath = getIntent().getStringExtra("path");
            isByte = getIntent().getBooleanExtra("isByte", false);
        } else {
            mPath = savedInstanceState.getString("path");
            isByte = savedInstanceState.getBoolean("isByte");
        }
        setContentView(R.layout.activity_pic_pre);
        ButterKnife.bind(this);
        mEmptyView.setOnDataLoadStatusListener(this);
        if (isByte) {
            mEmptyView.onStart();
            GetFileBytesReqVo reqVo = new GetFileBytesReqVo();
            reqVo.setPath(mPath);
            getPresenter().dataDetail(UniqueKey.VERSION.V1, AppApplication.getUserData().getToken(), reqVo);
        } else {

        }
    }

    @Override
    public void onSuccessData(PictureData pictureData) {
        mEmptyView.onSuccess();
        if (pictureData != null || !TextUtils.isEmpty(pictureData.getBytes())) {
            mPhotoView.setImageBitmap(BitmapAndStringUtils.convertStringToIcon(pictureData.getBytes()));
        }
    }

    @Override
    public void onFailureData(String code, String msg) {
        mEmptyView.onError();
    }

    public static void newIntent(Context context, String path, boolean isByte) {
        Intent intent = new Intent(context, PicPreActivity.class);
        intent.putExtra("path", path);
        intent.putExtra("isByte", isByte);
        context.startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("path", mPath);
        outState.putBoolean("isByte", isByte);
    }

    @Override
    public void onDataLoadAgain() {
        GetFileBytesReqVo reqVo = new GetFileBytesReqVo();
        reqVo.setPath(mPath);
        getPresenter().dataDetail(UniqueKey.VERSION.V1, AppApplication.getUserData().getToken(), reqVo);
    }
}
