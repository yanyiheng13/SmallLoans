package com.virgo.financeloan.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.entity.LocalMedia;
import com.virgo.financeloan.R;
import com.virgo.financeloan.model.responce.UploadFileVo;
import com.virgo.financeloan.ui.PicPreActivity;
import com.virgo.financeloan.ui.behavior.OnAddPicClickListener2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.Getter;
import lombok.Setter;

/**
 * 功能说明：身份证上传界面
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/12 13:57
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */

public class IdCardView extends BaseLinearLayout {
    /**
     * 正面图片
     */
    @BindView(R.id.id_front_img)
    UploadPicView mImgFront;
    /**
     * 反面图片
     */
    @BindView(R.id.id_side_img)
    UploadPicView mImgSide;

    /**
     * 正面已上传
     */
    @BindView(R.id.tv_up1)
    TextView mTvFront;

    /**
     * 反面已上传
     */
    @BindView(R.id.tv_up2)
    TextView mTvSide;

    /**
     */
    @BindView(R.id.tv1)
    TextView mTv1;

    /**
     */
    @BindView(R.id.tv2)
    TextView mTv2;

    /**
     * 正面图片按钮清除
     */
    @BindView(R.id.id_front_img_clear)
    ImageView mFrontImgClear;
    /**
     * 反面图片按钮清除
     */
    @BindView(R.id.id_side_img_clear)
    ImageView mSideImgClear;

    /**
     * 正面已选择
     */
    private List<LocalMedia> mListFront = new ArrayList<>();
    /**
     * 反面已选择的图片
     */
    private List<LocalMedia> mListSide = new ArrayList<>();
    @Setter
    @Getter
    private String orderNum;
    /**
     * 文件类型  身份证 房产等等
     */
    @Setter
    @Getter
    private String fileType1;
    /**
     * 文件类型  身份证 房产等等
     */
    @Setter
    @Getter
    private String fileType2;

    /**
     * 操作类型 ADD(Integer.valueOf(0), "新增"),
     * MODIFY(Integer.valueOf(1), "修改"),
     * REMOVE(Integer.valueOf(2), "删除");
     */
    @Setter
    @Getter
    private String operateType;

    /**
     * 上传多张的时候  需要传递顺序
     */
    private int orderToServer;

    private boolean isHouse;

    public IdCardView(Context context) {
        this(context, null);
    }

    public IdCardView setIsHouse(boolean isHouse) {
        this.isHouse = isHouse;
        if (isHouse) {
            mTv1.setText("户主页面");
            mTv2.setText("本人页面");
        }
        return this;
    }

    public IdCardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IdCardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.HORIZONTAL);
        inflate(context, R.layout.view_id_card, this);
        ButterKnife.bind(this, this);
    }

    @OnClick({R.id.id_front_img, R.id.id_side_img, R.id.id_front_img_clear, R.id.id_side_img_clear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_front_img:
                if (listener != null) {
                    listener.onAddPicClick(IdCardView.this, 0);
                }
                intoPics(1);
                break;
            case R.id.id_side_img:
                if (listener != null) {
                    listener.onAddPicClick(IdCardView.this, 1);
                }
                intoPics(1);
                break;
            case R.id.id_front_img_clear:
                break;
            case R.id.id_side_img_clear:
                break;
            default:
                break;
        }
    }

    /**
     * 设置默认上传数据
     */
    public void setAlreadyUpPic(UploadFileVo uploadFileVoFont, UploadFileVo uploadFileVoSide) {
        int maxOrder = 1;
        if (uploadFileVoFont != null && !TextUtils.isEmpty(uploadFileVoFont.getOrder())) {
            int order = Integer.valueOf(uploadFileVoFont.getOrder());
            if (order > maxOrder) {
                maxOrder = order;
            }
            LocalMedia localMedia = new LocalMedia();
            localMedia.picPath = uploadFileVoFont.getFileAdrress();
            localMedia.fileId = uploadFileVoFont.getId();
            localMedia.isFromServer = true;
            localMedia.order = order + "";
            mListFront.add(localMedia);
        }
        setData(mListFront, 0);
        if (uploadFileVoSide != null && !TextUtils.isEmpty(uploadFileVoSide.getOrder())) {
            int order = Integer.valueOf(uploadFileVoSide.getOrder());
            if (order > maxOrder) {
                maxOrder = order;
            }
            LocalMedia localMedia = new LocalMedia();
            localMedia.picPath = uploadFileVoSide.getFileAdrress();
            localMedia.fileId = uploadFileVoSide.getId();
            localMedia.isFromServer = true;
            localMedia.order = order + "";
            mListSide.add(localMedia);
        }
        orderToServer = maxOrder;
        setData(mListSide, 1);
    }

    /**
     * 设置数据
     *
     * @param
     */
    public void setData(List<LocalMedia> list, int tab) {
        if (tab == 0) {
            mListFront = list;
            imgShowOrDelete(0, mFrontImgClear, mListFront, mImgFront, mTvFront, fileType1);
        } else if (tab == 1) {
            mListSide = list;
            imgShowOrDelete(1, mSideImgClear, mListSide, mImgSide, mTvSide, fileType2);
        }
    }

    /**
     * 逻辑控制
     *
     * @param clearImage
     * @param listLocalMedia
     * @param imgView
     */
    private void imgShowOrDelete(final int position, final ImageView clearImage, final List<LocalMedia> listLocalMedia, final UploadPicView imgView, final TextView textView, final String fileType) {
        if (listLocalMedia == null || listLocalMedia.size() == 0) {
            clearImage.setVisibility(View.GONE);
            imgView.setIsShow(false);
            textView.setVisibility(View.GONE);
            imgView.setImageResource(R.mipmap.icon_add_pic);
            imgView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onAddPicClick(IdCardView.this, position);
                    }
                    intoPics(1);
                }
            });
            return;
        }
        final LocalMedia item = listLocalMedia.get(0);
        imgView.setCurrentMedia(item);
        if (TextUtils.isEmpty(item.order)) {
            orderToServer ++;
            item.order = orderToServer + "";
        }
        clearImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.isUping) {
                    Toast.makeText(getContext(), "上传中，无法进行删除操作", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!TextUtils.isEmpty(item.picPath)) {
                    //已经上传成功,需要从服务器删除
                    listLocalMedia.remove(item);
                    imgView.deletePic(orderNum, fileType);

                    setData(listLocalMedia, position);
                } else {
                    //没有上传成功,本地删除就行
                    listLocalMedia.remove(item);
                    item.isUping = false;
                    item.isError = false;
                    setData(listLocalMedia, position);
                }
                clearImage.setVisibility(View.GONE);
                imgView.setIsShow(false);
                textView.setVisibility(View.GONE);
                imgView.setImageResource(R.mipmap.icon_add_pic);
            }
        });

        //图片显示区域
        String path = "";
        if (item.isFromServer) {
            textView.setVisibility(VISIBLE);
            clearImage.setVisibility(VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
            if (item.isCut() && !item.isCompressed()) {
                // 裁剪过
                path = item.getCutPath();
            } else if (item.isCompressed() || (item.isCut() && item.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                path = item.getCompressPath();
            } else {
                // 原图
                path = item.getPath();
            }
            imgView.setIsShow(true);
            imgView.setVisibility(View.VISIBLE);
            clearImage.setVisibility(View.VISIBLE);
            if (!item.isUping && TextUtils.isEmpty(item.picPath)) {
                //添加
                imgView.addPic(path, orderNum, fileType, item.order);
                item.isUping = true;
            }
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(getContext())
                    .load(path)
                    .apply(options)
                    .into(imgView.imgView());
        }
        final String finalPath = path;
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果上传失败  点击重新上传
                if (item.isError) {
                    //添加
                    imgView.addPic(finalPath, orderNum, fileType, item.order);
                    item.isUping = true;
                } else if (item.isFromServer) {
                    //需要跳转到图片下载界面  预览图片
                    PicPreActivity.newIntent(getContext(), item.picPath, true);
                } else if (!item.isUping) {
                    //跳转到当前上传图片预览界面
                    PicPreActivity.newIntent(getContext(), finalPath, false);
                }
            }
        });

    }

    public OnAddPicClickListener2 listener;

    public IdCardView setOnAddPicClickListener(OnAddPicClickListener2 listener) {
        this.listener = listener;
        return this;
    }

}
