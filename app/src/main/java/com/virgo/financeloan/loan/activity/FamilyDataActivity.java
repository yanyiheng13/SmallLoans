package com.virgo.financeloan.loan.activity;

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
import com.virgo.financeloan.model.responce.UploadFileVo;
import com.virgo.financeloan.ui.BaseActivity;
import com.virgo.financeloan.ui.FileEnums;
import com.virgo.financeloan.ui.behavior.OnAddPicClickListener;
import com.virgo.financeloan.ui.view.GroupView;
import com.virgo.financeloan.ui.view.UpImgCommonView;

import java.io.Serializable;
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

    /**
     * 销售证明
     */
    private List<UploadFileVo> mHouseList;
    /**
     * 车本复印件
     */
    private List<UploadFileVo> mCarList;
    /**
     * 其他财产证明
     */
    private List<UploadFileVo> mOtherPropertyList;
    /**
     * 订单id
     */
    private String orderNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saveOrData(savedInstanceState);
        setContentView(R.layout.activity_family_data);
        ButterKnife.bind(this);
        mHouseContentView = new UpImgCommonView(this).setOnAddPicClickListener(this);
        mCarContentView = new UpImgCommonView(this).setOnAddPicClickListener(this);
        mOtherPropertyContentView = new UpImgCommonView(this).setOnAddPicClickListener(this);

        //房本
        mHouseContentView.setOrderNum(orderNum);
        mHouseContentView.setFileType(String.valueOf(FileEnums.FileTypeEnum.PRIVATE_HOUSE.code));
        mHouseContentView.setAlreadyUpPic(mHouseList);
        //车本复印件
        mCarContentView.setOrderNum(orderNum);
        mCarContentView.setFileType(String.valueOf(FileEnums.FileTypeEnum.PRIVATE_VEHICLE.code));
        mCarContentView.setAlreadyUpPic(mCarList);
        //其他财产证明
        mOtherPropertyContentView.setOrderNum(orderNum);
        mOtherPropertyContentView.setFileType(String.valueOf(FileEnums.FileTypeEnum.PRIVATE_OTHER.code));
        mOtherPropertyContentView.setAlreadyUpPic(mOtherPropertyList);

        mHouseView.setGroupName(R.string.house_data).isRequireDot(false).setCustomView(mHouseContentView, true).setOnUpViewGroupListener(this);
        mCarView.setGroupName(R.string.car_data).isRequireDot(false).setCustomView(mCarContentView, false).setOnUpViewGroupListener(this);
        mOtherPropertyView.setGroupName(R.string.other_property).isRequireDot(false).setCustomView(mOtherPropertyContentView, false).setOnUpViewGroupListener(this);

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
                    UpImgCommonView marriage = ((UpImgCommonView) mCurrentView);
                    //从相册中回来数据
                    List<LocalMedia> listMarriage = PictureSelector.obtainMultipleResult(data);
                    //清除显示集合的数据（包括历史上传数据  +  上次选中的图片  + 添加按钮数据）
                    marriage.getMListPic().clear();
                    //将历史上传数据加入到显示的集合当中

                    //再将档次相册回来的数据添加到选中的数据中
                    marriage.getMSelectPic().addAll(listMarriage);
                    //再将总共选中的数据添加到总的显示数据中
                    marriage.getMListPic().addAll(marriage.getMSelectPic());
                    //将添加按钮数据加入到显示集合的数据中
                    LocalMedia mediaDivorce = new LocalMedia();
                    mediaDivorce.isAddPic = true;
                    marriage.getMListPic().add(mediaDivorce);
                    //更新数据
                    marriage.notifyDataSetChanged();
                    if (mCurrentView == mHouseContentView) {//房本

                    } else if (mCurrentView == mCarContentView) {//车本

                    } else if (mCurrentView == mOtherPropertyContentView) {//其他财产证明

                    }
                    break;
            }
        }
    }

    /**
     * 保存和拿取数据
     * @param savedInstanceState
     */
    private void saveOrData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            orderNum = intent.getStringExtra("orderNum");
            mHouseList = (List<UploadFileVo>) intent.getSerializableExtra("mHouseList");
            mCarList = (List<UploadFileVo>) intent.getSerializableExtra("mCarList");
            mOtherPropertyList = (List<UploadFileVo>) intent.getSerializableExtra("mOtherPropertyList");
        } else {
            orderNum = savedInstanceState.getString("orderNum");
            mHouseList = (List<UploadFileVo>) savedInstanceState.getSerializable("mHouseList");
            mCarList = (List<UploadFileVo>) savedInstanceState.getSerializable("mCarList");
            mOtherPropertyList = (List<UploadFileVo>) savedInstanceState.getSerializable("mOtherPropertyList");
        }
    }

    public static void newIntent(Context context, String orderNum,
                                 List<UploadFileVo> houseList,
                                 List<UploadFileVo> carList,
                                 List<UploadFileVo> otherList) {
        Intent intent = new Intent(context, FamilyDataActivity.class);
        intent.putExtra("orderNum", orderNum);
        intent.putExtra("mHouseList", (Serializable) houseList);
        intent.putExtra("mCarList", (Serializable) carList);
        intent.putExtra("mOtherPropertyList", (Serializable) otherList);
        context.startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("orderNum", orderNum);
        outState.putSerializable("mHouseList", (Serializable) mHouseList);
        outState.putSerializable("mCarList", (Serializable) mCarList);
        outState.putSerializable("mOtherPropertyList", (Serializable) mOtherPropertyList);
    }
}
