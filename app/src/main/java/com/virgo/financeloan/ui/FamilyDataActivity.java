package com.virgo.financeloan.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.virgo.financeloan.R;
import com.virgo.financeloan.ui.behavior.OnAddPicClickListener;
import com.virgo.financeloan.ui.view.GroupView;
import com.virgo.financeloan.ui.view.UpImgCommonView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能说明：家庭财产信息资料上传界面
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/12 10:26
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */

public class FamilyDataActivity extends BaseActivity implements GroupView.OnUpViewGroupListener, OnAddPicClickListener {
    /**
     * 房本复印件
     */
    @BindView(R.id.group_view_house)
    GroupView mHouseView;
    /**
     * 车本复印件
     */
    @BindView(R.id.group_view_car)
    GroupView mCarView;
    /**
     * 其他财产证明
     */
    @BindView(R.id.group_view_other_property)
    GroupView mOtherPropertyView;

    /**
     * 房本子View
     */
    private UpImgCommonView mHouseContentView;
    /**
     * 车本子View
     */
    private UpImgCommonView mCarContentView;
    /**
     * 其他财产证明子View
     */
    private UpImgCommonView mOtherPropertyContentView;

    private View mCurrentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_data);
        ButterKnife.bind(this);
        mHouseContentView = new UpImgCommonView(this).setOnAddPicClickListener(this);
        mCarContentView = new UpImgCommonView(this).setOnAddPicClickListener(this);
        mOtherPropertyContentView = new UpImgCommonView(this).setOnAddPicClickListener(this);

        mHouseView.setGroupName(R.string.house_data).isRequireDot(false).setCustomView(mHouseContentView, true).setOnUpViewGroupListener(this);
        mCarView.setGroupName(R.string.car_data).isRequireDot(false).setCustomView(mCarContentView, false).setOnUpViewGroupListener(this);
        mOtherPropertyView.setGroupName(R.string.other_property).isRequireDot(false).setCustomView(mOtherPropertyContentView, false).setOnUpViewGroupListener(this);

    }

    public static void newIntent(Context context) {
        Intent intent = new Intent(context, FamilyDataActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onAddPicClick(View view) {
        mCurrentView = view;
    }

    @Override
    public void onUpClick(View view) {

    }

    @Override
    public void onGroupClick(View view) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    UpImgCommonView upImgCommonView = ((UpImgCommonView)mCurrentView);
                    List<LocalMedia> listCommon = PictureSelector.obtainMultipleResult(data);
                    upImgCommonView.setMSelectPic(listCommon);
                    upImgCommonView.getMListPic().clear();
                    upImgCommonView.getMListPic().addAll(listCommon);
                    LocalMedia mediaDivorce = new LocalMedia();
                    mediaDivorce.isAddPic = true;
                    upImgCommonView.getMListPic().add(mediaDivorce);
                    upImgCommonView.notifyDataSetChanged();
                    if (mCurrentView == mHouseContentView) {//房本

                    } else if (mCurrentView == mCarContentView) {//车本

                    } else if (mCurrentView == mOtherPropertyContentView) {//其他财产证明

                    }
                    break;
            }
        }
    }
}
