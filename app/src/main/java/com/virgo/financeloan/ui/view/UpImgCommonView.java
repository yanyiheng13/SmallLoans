package com.virgo.financeloan.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.entity.LocalMedia;
import com.virgo.financeloan.R;
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
     *
     */
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

    private boolean isUp;
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
        LocalMedia media = new LocalMedia();
        media.isAddPic = true;
        mListPic.add(media);
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        for (int i = 0; i < 9; i++) {
            viewArray[i] = LayoutInflater.from(getContext()).inflate(R.layout.up_pic_item, null);
            mImageArray[i] = (UploadPicView) viewArray[i].findViewById(R.id.maintain_pic_img);
            mImageClearArray[i] = (ImageView) viewArray[i].findViewById(R.id.maintain_pic_clear_img);
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
        } else if (size <= 9) {
            mContentImg1.setVisibility(View.VISIBLE);
            mContentImg2.setVisibility(View.VISIBLE);
            mContentImg3.setVisibility(View.VISIBLE);
        }
        int widths = (int)((width - UIUtil.dip2px(getContext(), 46)) / 3);
        for (int i = 0; i < size; i++) {
            if (i < 3) {
                mContentImg1.addView(viewArray[i]);
            } else if (i < 6) {
                mContentImg2.addView(viewArray[i]);
            } else if (i < 9) {
                mContentImg3.addView(viewArray[i]);
            }
            final LocalMedia item = mListPic.get(i);
            viewArray[i].getLayoutParams().width = widths;
            viewArray[i].getLayoutParams().height = widths;
            //图片显示区域
            mImageArray[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.isAddPic) {
                        if (listener != null) {
                            listener.onAddPicClick(UpImgCommonView.this);
                        }
                        intoPics();
                    } else {
//                        intoPre();
                    }
                }
            });
            //清除按钮
            final int finalI = i;
            mImageClearArray[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListPic.remove(item);
                    mSelectPic.remove(item);
                    mImageArray[finalI].setUping(false);
                    update();
                }
            });
            String path = "";
            if (item.isAddPic) {
                mImageClearArray[i].setVisibility(View.GONE);
                mImageArray[i].setIsShow(false);
                if (mListPic.size() == 10) {
                    mImageArray[i].setVisibility(View.GONE);
                } else {
                    mImageArray[i].setImageResource(R.mipmap.icon_add_pic);
                    mImageArray[i].setVisibility(View.VISIBLE);
                }
                return;
            } else {
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
                if (!mImageArray[i].getUping()) {
                    mImageArray[i].upPic(path, orderNum);
                    mImageArray[i].setUping(true);
                }
            }
            // 例如 LocalMedia 里面返回三种path
            // 1.media.getPath(); 为原图path
            // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
            // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
            // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
//                int mimeType = media.getMimeType();
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.color.main_bg)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(getContext())
                    .load(path)
                    .apply(options)
                    .into(mImageArray[i].imgView());
        }

    }

    public OnAddPicClickListener listener;

    public UpImgCommonView setOnAddPicClickListener(OnAddPicClickListener listener) {
        this.listener = listener;
        return this;
    }
}
