package com.virgo.financeloan.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
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
import com.virgo.financeloan.ui.behavior.OnAddPicClickListener;
import com.virgo.financeloan.util.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;
import lombok.Setter;

/**
 * 功能说明：上传图片
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/12 14:55
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */

public class UpImgCommonView extends BaseLinearLayout {

    @BindView(R.id.img_content1)
    LinearLayout mContentImg1;
    @BindView(R.id.img_content2)
    LinearLayout mContentImg2;
    @BindView(R.id.img_content3)
    LinearLayout mContentImg3;
    private UploadPicView[] mImageArray = new UploadPicView[9];
    private ImageView[] mImageClearArray = new ImageView[9];
    private TextView[] mTvAlready = new TextView[9];
    private View[] viewArray = new View[9];
    /**
     * 图片信息
     */
    @Setter
    @Getter
    private List<LocalMedia> mListPic = new ArrayList<>();
    /**
     * 选中的图片列表
     */
    @Setter
    @Getter
    private List<LocalMedia> mSelectPic = new ArrayList<>();

    /**
     * 已上传的
     */
    @Setter
    @Getter
    private List<LocalMedia> mUpPic = new ArrayList<>();

    @Setter
    @Getter
    private String orderNum;
    /**
     * 文件类型  身份证 房产等等
     */
    @Setter
    @Getter
    private String fileType;
    /**
     * 前端维护的一个order要传给后端的
     */
    private int orderToServer;

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
    @Setter
    @Getter
    private String order;

    /**
     * 屏幕宽度
     */
    private int width;

    public UpImgCommonView(Context context) {
        this(context, null);
    }

    public UpImgCommonView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UpImgCommonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.VERTICAL);
        inflate(context, R.layout.view_up_common, this);
        ButterKnife.bind(this, this);

        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        for (int i = 0; i < 9; i++) {
            viewArray[i] = LayoutInflater.from(getContext()).inflate(R.layout.up_pic_item, null);
            mImageArray[i] = (UploadPicView) viewArray[i].findViewById(R.id.maintain_pic_img);
            mImageClearArray[i] = (ImageView) viewArray[i].findViewById(R.id.maintain_pic_clear_img);
            mTvAlready[i] = (TextView) viewArray[i].findViewById(R.id.tv_up);
        }
    }

    public void setAlreadyUpPic(List<UploadFileVo> mediaList) {
        if (mediaList != null && mediaList.size() > 0) {
            int size = mediaList.size();
            if (size > 9) {
                size = 9;
            }
            int maxOrder = 1;
            for (int i = 0; i < size; i++) {
                UploadFileVo uploadFileVo = mediaList.get(i);
                if (uploadFileVo == null) {
                    continue;
                }
                String order = uploadFileVo.getOrder();
                LocalMedia localMedia = new LocalMedia();
                localMedia.picPath = uploadFileVo.getFileAdrress();
                localMedia.fileId = uploadFileVo.getId();

                localMedia.isFromServer = true;
                localMedia.order = order;
                if (!TextUtils.isEmpty(order)) {
                    int o = Integer.valueOf(order);
                    if (o > maxOrder) {
                        maxOrder = o;
                    }
                }
                orderToServer = maxOrder;
                mSelectPic.add(localMedia);
            }
        }
        mListPic.addAll(mSelectPic);
        if (mListPic.size() < 9) {
            LocalMedia media = new LocalMedia();
            media.isAddPic = true;
            mListPic.add(media);
        }
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
        update();
    }

    private void update() {
        mContentImg1.removeAllViews();
        mContentImg2.removeAllViews();
        mContentImg3.removeAllViews();
        if (mListPic == null) {
            return;
        }
        int size = mListPic.size();
        if (size <= 3) {
            mContentImg1.setVisibility(View.VISIBLE);
            mContentImg2.setVisibility(View.GONE);
            mContentImg3.setVisibility(View.GONE);
        } else if (size <= 6) {
            mContentImg1.setVisibility(View.VISIBLE);
            mContentImg2.setVisibility(View.VISIBLE);
            mContentImg3.setVisibility(View.GONE);
        } else {
            mContentImg1.setVisibility(View.VISIBLE);
            mContentImg2.setVisibility(View.VISIBLE);
            mContentImg3.setVisibility(View.VISIBLE);
        }
        int widths = (int) ((width - UIUtil.dip2px(getContext(), 46)) / 3);
        if (size > 9) {
            size = 9;
        }
        for (int i = 0; i < size; i++) {
            if (i < 3) {
                mContentImg1.addView(viewArray[i]);
            } else if (i < 6) {
                mContentImg2.addView(viewArray[i]);
            } else if (i < 9) {
                mContentImg3.addView(viewArray[i]);
            }
            final int finalI = i;
            final LocalMedia item = mListPic.get(i);
            mImageArray[finalI].setCurrentMedia(item);

            viewArray[i].getLayoutParams().width = widths;
            viewArray[i].getLayoutParams().height = widths;

            if (TextUtils.isEmpty(item.order)) {
                orderToServer ++;
                item.order = orderToServer + "";
            }

            //图片显示区域
            String path = "";
            if (item.isAddPic) {
                mTvAlready[i].setVisibility(View.GONE);
                mImageClearArray[i].setVisibility(View.GONE);
                mImageArray[i].setIsShow(false);
                if (mListPic.size() == 10) {
                    mImageArray[i].setVisibility(View.GONE);
                } else {
                    mImageArray[i].setImageResource(R.mipmap.icon_add_pic);
                    mImageArray[i].setVisibility(View.VISIBLE);
                }
            } else if (item.isFromServer) {
                mTvAlready[i].setVisibility(View.VISIBLE);
            } else {
                mTvAlready[i].setVisibility(View.GONE);
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
                mImageArray[i].setIsShow(true);
                mImageArray[i].setVisibility(View.VISIBLE);
                mImageClearArray[i].setVisibility(View.VISIBLE);
                if (!item.isUping && TextUtils.isEmpty(item.picPath)) {
                    //添加
                    mImageArray[i].addPic(path, orderNum, fileType, item.order);
                    item.isUping = true;
                }
                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.color.main_bg)
                        .diskCacheStrategy(DiskCacheStrategy.ALL);
                Glide.with(getContext())
                        .load(path)
                        .apply(options)
                        .into(mImageArray[i].imgView());
            }
            final String finalPath = path;
            mImageArray[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.isAddPic) {
                        if (listener != null) {
                            listener.onAddPicClick(UpImgCommonView.this);
                        }
                        intoPics(9 - mListPic.size() + 1);
                    } else {
                        //如果上传失败  点击重新上传
                        if (item.isError) {
//                             //添加
                            mImageArray[finalI].addPic(finalPath, orderNum, fileType, item.order);
                            item.isUping = true;
                        } else if (item.isFromServer) {
                            //需要跳转到图片下载界面  预览图片
                            PicPreActivity.newIntent(getContext(), item.picPath, true);
                        } else if (!item.isUping) {
                            //跳转到当前上传图片预览界面
                            PicPreActivity.newIntent(getContext(), finalPath, false);
                        }
                    }
                }
            });
            //清除按钮
            mImageClearArray[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.isUping) {
                        Toast.makeText(getContext(), "上传中，无法进行删除操作", Toast.LENGTH_SHORT).show();
                    } else if (!TextUtils.isEmpty(item.picPath)) {
                        //已经上传成功,需要从服务器删除
                        mListPic.remove(item);
                        mSelectPic.remove(item);
                        mImageArray[finalI].deletePic(orderNum, getFileType());
                        update();
                    } else {
                        //没有上传成功,本地删除就行
                        mListPic.remove(item);
                        mSelectPic.remove(item);
                       item.isUping = false;
                        update();
                    }
                }
            });

        }

    }

    public OnAddPicClickListener listener;

    public UpImgCommonView setOnAddPicClickListener(OnAddPicClickListener listener) {
        this.listener = listener;
        return this;
    }
}
