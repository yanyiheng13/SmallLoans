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

    /**
     * 营业执照
     */
    private List<UploadFileVo> mBusinessList;
    /**
     * 公司章程
     */
    private List<UploadFileVo> mArticlesAssociationList;
    /**
     * 开户许可
     */
    private List<UploadFileVo> mOpeningPermitList;
    /**
     * 租赁合同
     */
    private List<UploadFileVo> mLeaseContractList;
    /**
     * 产品购销合同
     */
    private List<UploadFileVo> mPurchaseSaleContractList;
    /**
     * 销售证明
     */
    private List<UploadFileVo> mSalesConfirmationList;
    /**
     * 订单id
     */
    private String orderNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saveOrData(savedInstanceState);
        setContentView(R.layout.activity_enterprise_data);
        ButterKnife.bind(this);
        mBusinessLicenseContentView = new UpImgCommonView(this).setOnAddPicClickListener(this);
        mArticlesAssociationContentView = new UpImgCommonView(this).setOnAddPicClickListener(this);
        mOpeningPermitContentView = new UpImgCommonView(this).setOnAddPicClickListener(this);
        mLeaseContractContentView = new UpImgCommonView(this).setOnAddPicClickListener(this);
        mPurchaseSaleContractContentView = new UpImgCommonView(this).setOnAddPicClickListener(this);
        mSalesConfirmationContentView = new UpImgCommonView(this).setOnAddPicClickListener(this);

        //营业执照
        mBusinessLicenseContentView.setOrderNum(orderNum);
        mBusinessLicenseContentView.setFileType(String.valueOf(FileEnums.FileTypeEnum.BUSINESS_LICENSE.code));
        mBusinessLicenseContentView.setAlreadyUpPic(mBusinessList);
        //公司章程
        mArticlesAssociationContentView.setOrderNum(orderNum);
        mArticlesAssociationContentView.setFileType(String.valueOf(FileEnums.FileTypeEnum.COMPANY_ARTICLES.code));
        mArticlesAssociationContentView.setAlreadyUpPic(mArticlesAssociationList);
        //开户许可
        mOpeningPermitContentView.setOrderNum(orderNum);
        mOpeningPermitContentView.setFileType(String.valueOf(FileEnums.FileTypeEnum.OPEN_ACCOUNT_PERMISSION.code));
        mOpeningPermitContentView.setAlreadyUpPic(mOpeningPermitList);
        //租赁合同
        mLeaseContractContentView.setOrderNum(orderNum);
        mLeaseContractContentView.setFileType(String.valueOf(FileEnums.FileTypeEnum.RENT_CONTRACT.code));
        mLeaseContractContentView.setAlreadyUpPic(mLeaseContractList);
        //产品购销合同
        mPurchaseSaleContractContentView.setOrderNum(orderNum);
        mPurchaseSaleContractContentView.setFileType(String.valueOf(FileEnums.FileTypeEnum.PURCHASING_SELLING_CONTRACT.code));
        mPurchaseSaleContractContentView.setAlreadyUpPic(mPurchaseSaleContractList);
        //销售证明
        mSalesConfirmationContentView.setOrderNum(orderNum);
        mSalesConfirmationContentView.setFileType(String.valueOf(FileEnums.FileTypeEnum.SALE_CERTIFICATE.code));
        mSalesConfirmationContentView.setAlreadyUpPic(mSalesConfirmationList);

        mBusinessLicenseView.setGroupName(R.string.business_license).isRequireDot(true).setCustomView(mBusinessLicenseContentView, true).setOnUpViewGroupListener(this);
        mArticlesAssociationView.setGroupName(R.string.articles_association).isRequireDot(false).setCustomView(mArticlesAssociationContentView, false).setOnUpViewGroupListener(this);
        mOpeningPermitView.setGroupName(R.string.opening_permit).isRequireDot(false).setCustomView(mOpeningPermitContentView, false).setOnUpViewGroupListener(this);
        mLeaseContractView.setGroupName(R.string.lease_contract).isRequireDot(false).setCustomView(mLeaseContractContentView, false).setOnUpViewGroupListener(this);
        mPurchaseSaleContractView.setGroupName(R.string.purchase_sale_contract).isRequireDot(false).setCustomView(mPurchaseSaleContractContentView, false).setOnUpViewGroupListener(this);
        mSalesConfirmationView.setGroupName(R.string.sales_confirmation).isRequireDot(false).setCustomView(mSalesConfirmationContentView, false).setOnUpViewGroupListener(this);

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

    /**
     * 保存和拿取数据
     * @param savedInstanceState
     */
    private void saveOrData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            orderNum = intent.getStringExtra("orderNum");
            mBusinessList = (List<UploadFileVo>) intent.getSerializableExtra("mBusinessList");
            mArticlesAssociationList = (List<UploadFileVo>) intent.getSerializableExtra("mArticlesAssociationList");
            mOpeningPermitList = (List<UploadFileVo>) intent.getSerializableExtra("mOpeningPermitList");
            mLeaseContractList = (List<UploadFileVo>) intent.getSerializableExtra("mLeaseContractList");
            mPurchaseSaleContractList = (List<UploadFileVo>) intent.getSerializableExtra("mPurchaseSaleContractList");
            mSalesConfirmationList = (List<UploadFileVo>) intent.getSerializableExtra("mSalesConfirmationList");
        } else {
            orderNum = savedInstanceState.getString("orderNum");
            mBusinessList = (List<UploadFileVo>) savedInstanceState.getSerializable("mBusinessList");
            mArticlesAssociationList = (List<UploadFileVo>) savedInstanceState.getSerializable("mArticlesAssociationList");
            mOpeningPermitList = (List<UploadFileVo>) savedInstanceState.getSerializable("mOpeningPermitList");
            mLeaseContractList = (List<UploadFileVo>) savedInstanceState.getSerializable("mLeaseContractList");
            mPurchaseSaleContractList = (List<UploadFileVo>) savedInstanceState.getSerializable("mPurchaseSaleContractList");
            mSalesConfirmationList = (List<UploadFileVo>) savedInstanceState.getSerializable("mSalesConfirmationList");
        }
    }

    public static void newIntent(Context context, String orderNum,
                                 List<UploadFileVo> businessList,
                                 List<UploadFileVo> articlesAssociationList,
                                 List<UploadFileVo> openingPermitList,
                                 List<UploadFileVo> leaseContractList,
                                 List<UploadFileVo> purchaseSaleContractList,
                                 List<UploadFileVo> salesConfirmationList) {
        Intent intent = new Intent(context, EnterpriseDataActivity.class);
        intent.putExtra("orderNum", orderNum);
        intent.putExtra("mBusinessList", (Serializable) businessList);
        intent.putExtra("mArticlesAssociationList", (Serializable) articlesAssociationList);
        intent.putExtra("mOpeningPermitList", (Serializable) openingPermitList);
        intent.putExtra("mLeaseContractList", (Serializable)leaseContractList);
        intent.putExtra("mPurchaseSaleContractList", (Serializable) purchaseSaleContractList);
        intent.putExtra("mSalesConfirmationList", (Serializable) salesConfirmationList);
        context.startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("orderNum", orderNum);
        outState.putSerializable("mBusinessList", (Serializable) mBusinessList);
        outState.putSerializable("mArticlesAssociationList", (Serializable) mArticlesAssociationList);
        outState.putSerializable("mOpeningPermitList", (Serializable) mOpeningPermitList);
        outState.putSerializable("mLeaseContractList", (Serializable) mLeaseContractList);
        outState.putSerializable("mPurchaseSaleContractList", (Serializable) mPurchaseSaleContractList);
        outState.putSerializable("mSalesConfirmationList", (Serializable) mSalesConfirmationList);
    }

}
