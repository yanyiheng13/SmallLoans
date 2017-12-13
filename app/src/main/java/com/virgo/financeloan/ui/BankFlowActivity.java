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
 * 功能说明：银行流水资料上传
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/12 10:26
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */

public class BankFlowActivity extends BaseActivity implements GroupView.OnUpViewGroupListener, OnAddPicClickListener {
    /**
     * 企业银行流水
     */
    @BindView(R.id.group_view_enterprise_bank)
    GroupView mEnterpriseBankView;
    /**
     * 个人银行流水
     */
    @BindView(R.id.group_view_person_bank)
    GroupView mPersonBank;
    /**
     * 企业银行流水
     */
    private UpImgCommonView mEnterpriseContentView;
    /**
     * 个人银行流水
     */
    private UpImgCommonView mOtherPropertyContentView;

    private View mCurrentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_flow);
        ButterKnife.bind(this);
        mEnterpriseContentView = new UpImgCommonView(this).setOnAddPicClickListener(this);
        mOtherPropertyContentView = new UpImgCommonView(this).setOnAddPicClickListener(this);

        mEnterpriseBankView.setGroupName(R.string.enterprise_bank_flow).isRequireDot(false).setCustomView(mEnterpriseContentView, true).setOnUpViewGroupListener(this);
        mPersonBank.setGroupName(R.string.person_bank_flow).isRequireDot(true).setCustomView(mOtherPropertyContentView, false).setOnUpViewGroupListener(this);

    }

    public static void newIntent(Context context) {
        Intent intent = new Intent(context, BankFlowActivity.class);
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
                    UpImgCommonView upImgCommonView = ((UpImgCommonView) mCurrentView);
                    List<LocalMedia> listCommon = PictureSelector.obtainMultipleResult(data);
                    upImgCommonView.setMSelectPic(listCommon);
                    upImgCommonView.getMListPic().clear();
                    upImgCommonView.getMListPic().addAll(listCommon);
                    LocalMedia mediaDivorce = new LocalMedia();
                    mediaDivorce.isAddPic = true;
                    upImgCommonView.getMListPic().add(mediaDivorce);
                    upImgCommonView.notifyDataSetChanged();
                    if (mCurrentView == mEnterpriseContentView) {//企业银行流水

                    } else if (mCurrentView == mOtherPropertyContentView) {//个人银行流水

                    }
                    break;
            }
        }
    }
}
