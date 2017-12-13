package com.virgo.financeloan.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.entity.LocalMedia;
import com.virgo.financeloan.R;
import com.virgo.financeloan.ui.behavior.OnAddPicClickListener2;

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
    ImageView mImgFront;
    /**
     * 反面图片
     */
    @BindView(R.id.id_side_img)
    ImageView mImgSide;

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
     * 已选择的图片
     */
    private List<LocalMedia> mListFront;

    /**
     * 已选择的图片
     */
    private List<LocalMedia> mListSide;

    public IdCardView(Context context) {
        this(context, null);
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
     * 设置数据
     * @param
     */
    public void setData(List<LocalMedia> list, int tab) {
        if (tab == 0) {
            mListFront = list;
            imgShowOrDelete(mFrontImgClear, mListFront, mImgFront);
        } else if (tab == 1) {
            mListSide = list;
            imgShowOrDelete(mSideImgClear, mListSide, mImgSide);
        }
    }

    /**
     * 逻辑控制
     * @param clearImage
     * @param listLocalMedia
     * @param imgView
     */
    private void imgShowOrDelete(final ImageView clearImage, final List<LocalMedia> listLocalMedia, final ImageView imgView) {
        clearImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listLocalMedia.clear();
                imgView.setImageDrawable(null);
                clearImage.setVisibility(View.GONE);
            }
        });
        clearImage.setVisibility(View.VISIBLE);
        // 例如 LocalMedia 里面返回三种path
        // 1.media.getPath(); 为原图path
        // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
        // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
        // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
//                int mimeType = media.getMimeType();
        LocalMedia item = listLocalMedia.get(0);
        String path = "";
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
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.color.main_bg)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(getContext())
                .load(path)
                .apply(options)
                .into(imgView);

    }

    public OnAddPicClickListener2 listener;
    public IdCardView setOnAddPicClickListener(OnAddPicClickListener2 listener) {
        this.listener = listener;
        return this;
    }

}
