package com.virgo.financeloan.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.virgo.financeloan.R;
import com.virgo.financeloan.ui.behavior.OnAddPicClickListener;
import com.virgo.financeloan.ui.behavior.OnAddPicClickListener2;
import com.virgo.financeloan.ui.view.GroupView;
import com.virgo.financeloan.ui.view.HouseholdView;
import com.virgo.financeloan.ui.view.IdCardView;
import com.virgo.financeloan.ui.view.UpImgCommonView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能说明：个人资料上传界面
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/12 10:26
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */

public class PersonDataActivity extends BaseActivity implements GroupView.OnUpViewGroupListener, OnAddPicClickListener, OnAddPicClickListener2 {
    /**
     * 本人身份证
     */
    @BindView(R.id.group_view_individual_id)
    GroupView mIndividualIdView;
    /**
     * 配偶身份证
     */
    @BindView(R.id.group_view_consort_id)
    GroupView mConsortIdView;
    /**
     * 户口本
     */
    @BindView(R.id.group_view_household)
    GroupView mHouseholdView;
    /**
     * 结婚证
     */
    @BindView(R.id.group_view_marriage_certificate)
    GroupView mMarriageView;
    /**
     * 结婚证
     */
    @BindView(R.id.group_view_divorce_certificates)
    GroupView mDivorceView;

    /**
     * 本人身份证
     */
    private IdCardView mIdCardView;
    /**
     * 配偶身份证
     */
    private IdCardView mConsortIdCardView;
    /**
     * 户口本上传页面
     */
    private HouseholdView mHouseholdContentView;

    /**
     * 离婚证页面
     */
    private UpImgCommonView mDivorceContentView;
    /**
     * 结婚证页面
     */
    private UpImgCommonView mMarriageContentView;

    private View mCurrentView;

    private int mCurrentTab = 0;// 0 正面 1反面
    private String orderNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            orderNum = getIntent().getStringExtra("orderNum");
        } else {
            orderNum = savedInstanceState.getString("orderNum");
        }
        setContentView(R.layout.activity_person_data);
        ButterKnife.bind(this);
        mIdCardView = new IdCardView(this).setOnAddPicClickListener(this);//个人身份证上传图片子View
        mConsortIdCardView = new IdCardView(this).setOnAddPicClickListener(this);//配偶身份证上传图片子View
        mHouseholdContentView = new HouseholdView(this).setOnAddPicClickListener(this);//户口本上传页面
        mMarriageContentView = new UpImgCommonView(this).setOnAddPicClickListener(this);//结婚证View
        mDivorceContentView = new UpImgCommonView(this).setOnAddPicClickListener(this);//结婚证View
        mMarriageContentView.setOrderNum(orderNum);
        mDivorceContentView.setOrderNum(orderNum);
        //本人身份证
        mIndividualIdView.setGroupName(R.string.individual_id).isRequireDot(true).setCustomView(mIdCardView, true).setOnUpViewGroupListener(this);
        mConsortIdView.setGroupName(R.string.consort_id).isRequireDot(false).setCustomView(mConsortIdCardView, false).setOnUpViewGroupListener(this);
        mHouseholdView.setGroupName(R.string.household).isRequireDot(true).setCustomView(mHouseholdContentView, false).setOnUpViewGroupListener(this);
        mMarriageView.setGroupName(R.string.marriage_cer).isRequireDot(false).setCustomView(mMarriageContentView, false).setOnUpViewGroupListener(this);
        mDivorceView.setGroupName(R.string.divorce_cer).isRequireDot(false).setCustomView(mDivorceContentView, false).setOnUpViewGroupListener(this);

    }

    /**
     * 上传按钮的点击
     *
     * @param view
     */
    @Override
    public void onUpClick(View view) {

    }

    /**
     * 展开还是伸缩
     *
     * @param view
     */
    @Override
    public void onGroupClick(View view) {

    }

    public static void newIntent(Context context, String orderNum) {
        Intent intent = new Intent(context, PersonDataActivity.class);
        intent.putExtra("orderNum", orderNum);
        context.startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("orderNum", orderNum);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    if (mCurrentView == mIdCardView) {//个人身份证
                        IdCardView idCardView = (IdCardView) mCurrentView;
                        idCardView.setData(PictureSelector.obtainMultipleResult(data), mCurrentTab);
                    } else if (mCurrentView == mConsortIdCardView) {//配偶身份证
                        IdCardView idCardView = (IdCardView) mCurrentView;
                        idCardView.setData(PictureSelector.obtainMultipleResult(data), mCurrentTab);
                    } else if (mCurrentView == mHouseholdContentView) {//户口本
                        HouseholdView householdView = (HouseholdView) mCurrentView;
                        householdView.setData(PictureSelector.obtainMultipleResult(data), mCurrentTab);
                    } else if (mCurrentView == mDivorceContentView) {//离婚证
                        UpImgCommonView divorce = ((UpImgCommonView) mCurrentView);
                        List<LocalMedia> listDivorce = PictureSelector.obtainMultipleResult(data);
                        divorce.setMSelectPic(listDivorce);
                        divorce.getMListPic().clear();
                        divorce.getMListPic().addAll(listDivorce);
                        LocalMedia mediaDivorce = new LocalMedia();
                        mediaDivorce.isAddPic = true;
                        divorce.getMListPic().add(mediaDivorce);
                        divorce.notifyDataSetChanged();
                    } else if (mCurrentView == mMarriageContentView) {//结婚证
                        UpImgCommonView marriage = ((UpImgCommonView) mCurrentView);
                        List<LocalMedia> listMarriage = PictureSelector.obtainMultipleResult(data);
                        marriage.setMSelectPic(listMarriage);
                        marriage.getMListPic().clear();
                        marriage.getMListPic().addAll(listMarriage);
                        LocalMedia mediaDivorce = new LocalMedia();
                        mediaDivorce.isAddPic = true;
                        marriage.getMListPic().add(mediaDivorce);
                        marriage.notifyDataSetChanged();
                    }
                    break;
            }
        }
    }

    @Override
    public void onAddPicClick(View view) {
        mCurrentView = view;
    }

    @Override
    public void onAddPicClick(View view, int position) {
        mCurrentView = view;
        mCurrentTab = position;
    }
}
