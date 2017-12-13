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
 * 功能说明：企业资料上传界面
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/12 10:26
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */

public class EnterpriseDataActivity extends BaseActivity implements GroupView.OnUpViewGroupListener, OnAddPicClickListener {
    /**
     * 营业执照
     */
    @BindView(R.id.group_view_business_license)
    GroupView mBusinessLicenseView;
    /**
     * 公司章程
     */
    @BindView(R.id.group_view_articles_association)
    GroupView mArticlesAssociationView;
    /**
     * 开户许可证
     */
    @BindView(R.id.group_view_opening_permit)
    GroupView mOpeningPermitView;
    /**
     * 租赁合同
     */
    @BindView(R.id.group_view_lease_contract)
    GroupView mLeaseContractView;
    /**
     * 产品购销合同
     */
    @BindView(R.id.group_view_purchase_sale_contract)
    GroupView mPurchaseSaleContractView;
    /**
     * 销售证明
     */
    @BindView(R.id.group_view_sales_confirmation)
    GroupView mSalesConfirmationView;
    /**
     * 营业执照子View
     */
    private UpImgCommonView mBusinessLicenseContentView;
    /**
     * 公司章程子View
     */
    private UpImgCommonView mArticlesAssociationContentView;
    /**
     * 开户许可证子View
     */
    private UpImgCommonView mOpeningPermitContentView;
    /**
     * 租赁合同子View
     */
    private UpImgCommonView mLeaseContractContentView;
    /**
     * 产品购销合同子View
     */
    private UpImgCommonView mPurchaseSaleContractContentView;
    /**
     * 销售证明子View
     */
    private UpImgCommonView mSalesConfirmationContentView;

    private View mCurrentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterprise_data);
        ButterKnife.bind(this);
        mBusinessLicenseContentView = new UpImgCommonView(this).setOnAddPicClickListener(this);
        mArticlesAssociationContentView = new UpImgCommonView(this).setOnAddPicClickListener(this);
        mOpeningPermitContentView = new UpImgCommonView(this).setOnAddPicClickListener(this);
        mLeaseContractContentView = new UpImgCommonView(this).setOnAddPicClickListener(this);
        mPurchaseSaleContractContentView = new UpImgCommonView(this).setOnAddPicClickListener(this);
        mSalesConfirmationContentView = new UpImgCommonView(this).setOnAddPicClickListener(this);

        mBusinessLicenseView.setGroupName(R.string.business_license).isRequireDot(true).setCustomView(mBusinessLicenseContentView, true).setOnUpViewGroupListener(this);
        mArticlesAssociationView.setGroupName(R.string.articles_association).isRequireDot(false).setCustomView(mArticlesAssociationContentView, false).setOnUpViewGroupListener(this);
        mOpeningPermitView.setGroupName(R.string.opening_permit).isRequireDot(true).setCustomView(mOpeningPermitContentView, false).setOnUpViewGroupListener(this);
        mLeaseContractView.setGroupName(R.string.lease_contract).isRequireDot(false).setCustomView(mLeaseContractContentView, false).setOnUpViewGroupListener(this);
        mPurchaseSaleContractView.setGroupName(R.string.purchase_sale_contract).isRequireDot(false).setCustomView(mPurchaseSaleContractContentView, false).setOnUpViewGroupListener(this);
        mSalesConfirmationView.setGroupName(R.string.sales_confirmation).isRequireDot(false).setCustomView(mSalesConfirmationContentView, false).setOnUpViewGroupListener(this);

    }

    public static void newIntent(Context context) {
        Intent intent = new Intent(context, EnterpriseDataActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onUpClick(View view) {

    }

    @Override
    public void onGroupClick(View view) {

    }

    @Override
    public void onAddPicClick(View view) {
        mCurrentView = view;
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
                    if (mCurrentView == mBusinessLicenseContentView) {//营业执照

                    } else if (mCurrentView == mArticlesAssociationContentView) {//公司章程

                    } else if (mCurrentView == mOpeningPermitContentView) {//开户许可证

                    } else if (mCurrentView == mLeaseContractContentView) {//租赁合同

                    } else if (mCurrentView == mPurchaseSaleContractContentView) {//产品购销合同

                    } else if (mCurrentView == mSalesConfirmationContentView) {//销售证明

                    }
                    break;
            }
        }
    }

}
