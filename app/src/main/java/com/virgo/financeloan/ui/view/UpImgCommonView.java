package com.virgo.financeloan.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.entity.LocalMedia;
import com.virgo.financeloan.R;
import com.virgo.financeloan.ui.behavior.OnAddPicClickListener;

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

    @BindView(R.id.custom_grid_view)
    CustomGridView mCustomGridView;
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
        mCustomGridView.setAdapter(myAdapter);
        LocalMedia media = new LocalMedia();
        media.isAddPic = true;
        mListPic.add(media);
        myAdapter.notifyDataSetChanged();
    }

    public void  notifyDataSetChanged() {
        myAdapter.notifyDataSetChanged();
    }

    BaseAdapter myAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return mListPic == null ? 0 : mListPic.size();
        }

        @Override
        public Object getItem(int position) {
            return mListPic.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.up_pic_item, null);
                vh = new ViewHolder();
                vh.clearImage = (ImageView) convertView.findViewById(R.id.maintain_pic_clear_img);
                vh.images = (UploadPicView) convertView.findViewById(R.id.maintain_pic_img);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            final LocalMedia item = mListPic.get(position);
            //图片显示区域
            vh.images.setOnClickListener(new View.OnClickListener() {
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
            vh.clearImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListPic.remove(item);
                    mSelectPic.remove(item);
                    myAdapter.notifyDataSetChanged();
                }
            });
            if (item.isAddPic) {
                vh.clearImage.setVisibility(View.GONE);
                vh.images.setIsShow(false);
                if (mListPic.size() == 10) {
                    vh.images.setVisibility(View.GONE);
                } else {
                    vh.images.setImageResource(R.mipmap.icon_add_pic);
                    vh.images.setVisibility(View.VISIBLE);
                }
                return convertView;
            } else {
                vh.images.setIsShow(true);
                vh.images.setVisibility(View.VISIBLE);
                vh.clearImage.setVisibility(View.VISIBLE);
            }
            // 例如 LocalMedia 里面返回三种path
            // 1.media.getPath(); 为原图path
            // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
            // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
            // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
//                int mimeType = media.getMimeType();
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
                    .into(vh.images.imgView());

            return convertView;
        }

        class ViewHolder {
            ImageView clearImage;
            UploadPicView images;
        }
    };

    public OnAddPicClickListener listener;
    public UpImgCommonView setOnAddPicClickListener(OnAddPicClickListener listener) {
        this.listener = listener;
        return this;
    }
}
