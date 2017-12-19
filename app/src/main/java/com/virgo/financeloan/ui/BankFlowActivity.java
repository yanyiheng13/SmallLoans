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
import com.virgo.financeloan.model.responce.UploadFileVo;
import com.virgo.financeloan.ui.behavior.OnAddPicClickListener;
import com.virgo.financeloan.ui.view.GroupView;
import com.virgo.financeloan.ui.view.UpImgCommonView;

import java.io.Serializable;
import java.util.ArrayList;
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
    /**
     * 企业银行流水
     */
    private List<UploadFileVo> mEnterpriseBankList;
    /**
     * 个人银行流水
     */
    private List<UploadFileVo> mPersonBankList;
    /**
     * 订单id
     */
    private String orderNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saveOrData(savedInstanceState);
        setContentView(R.layout.activity_bank_flow);
        ButterKnife.bind(this);
        mEnterpriseContentView = new UpImgCommonView(this).setOnAddPicClickListener(this);
        mOtherPropertyContentView = new UpImgCommonView(this).setOnAddPicClickListener(this);

        //企业银行流水
        mEnterpriseContentView.setOrderNum(orderNum);
        mEnterpriseContentView.setFileType(String.valueOf(FileEnums.FileTypeEnum.ENTERPRISE_BANK_WATER.code));
        mEnterpriseContentView.setAlreadyUpPic(mEnterpriseBankList);
        //个人银行流水
        mOtherPropertyContentView.setOrderNum(orderNum);
        mOtherPropertyContentView.setFileType(String.valueOf(FileEnums.FileTypeEnum.PRIVATE_BANK_WATER.code));
        mOtherPropertyContentView.setAlreadyUpPic(mPersonBankList);

        mEnterpriseBankView.setGroupName(R.string.enterprise_bank_flow).isRequireDot(false).setCustomView(mEnterpriseContentView, true).setOnUpViewGroupListener(this);
        mPersonBank.setGroupName(R.string.person_bank_flow).isRequireDot(true).setCustomView(mOtherPropertyContentView, false).setOnUpViewGroupListener(this);

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

    /**
     * 保存和拿取数据
     * @param savedInstanceState
     */
    private void saveOrData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            orderNum = intent.getStringExtra("orderNum");
            mEnterpriseBankList = (List<UploadFileVo>) intent.getSerializableExtra("mEnterpriseBankList");
            mPersonBankList = (List<UploadFileVo>) intent.getSerializableExtra("mPersonBankList");
        } else {
            orderNum = savedInstanceState.getString("orderNum");
            mEnterpriseBankList = (List<UploadFileVo>) savedInstanceState.getSerializable("mEnterpriseBankList");
            mPersonBankList = (List<UploadFileVo>) savedInstanceState.getSerializable("mPersonBankList");
        }
    }

    public static void newIntent(Context context, String orderNum,
                                 List<UploadFileVo> enList,
                                 List<UploadFileVo> personList) {
        Intent intent = new Intent(context, BankFlowActivity.class);
        intent.putExtra("orderNum", orderNum);
        intent.putExtra("mEnterpriseBankList", (Serializable) enList);
        intent.putExtra("mPersonBankList", (Serializable) personList);
        context.startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("orderNum", orderNum);
        outState.putSerializable("mEnterpriseBankList", (Serializable) mEnterpriseBankList);
        outState.putSerializable("mPersonBankList", (Serializable) mPersonBankList);
    }
}
