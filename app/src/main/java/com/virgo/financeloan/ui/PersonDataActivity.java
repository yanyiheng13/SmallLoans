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
import com.virgo.financeloan.ui.behavior.OnAddPicClickListener2;
import com.virgo.financeloan.ui.view.GroupView;
import com.virgo.financeloan.ui.view.HouseholdView;
import com.virgo.financeloan.ui.view.IdCardView;
import com.virgo.financeloan.ui.view.UpImgCommonView;

import java.io.Serializable;
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
    private IdCardView mHouseholdContentView;

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

    /**
     * 本人身份证
     */
    private List<UploadFileVo> mListId;
    /**
     * 配偶身份证
     */
    private List<UploadFileVo> mConsortListId;
    /**
     * 户口本
     */
    private List<UploadFileVo> mHouseholdList;
    /**
     * 结婚证
     */
    private List<UploadFileVo> mMarriageList;
    /**
     * 离婚证
     */
    private List<UploadFileVo> mDivorceList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saveOrData(savedInstanceState);
        setContentView(R.layout.activity_person_data);
        ButterKnife.bind(this);

        mIdCardView = new IdCardView(this).setOnAddPicClickListener(this);//个人身份证上传图片子View
        mConsortIdCardView = new IdCardView(this).setOnAddPicClickListener(this);//配偶身份证上传图片子View
        mHouseholdContentView = new IdCardView(this).setOnAddPicClickListener(this).setIsHouse(true);//户口本上传页面
        mMarriageContentView = new UpImgCommonView(this).setOnAddPicClickListener(this);//结婚证View
        mDivorceContentView = new UpImgCommonView(this).setOnAddPicClickListener(this);//结婚证View
        //结婚证
        mMarriageContentView.setOrderNum(orderNum);
        mMarriageContentView.setFileType(String.valueOf(FileEnums.FileTypeEnum.MARRIAGE_CERTIFICATE.code));
        mMarriageContentView.setAlreadyUpPic(mMarriageList);
        //离婚证
        mDivorceContentView.setOrderNum(orderNum);
        mDivorceContentView.setFileType(String.valueOf(FileEnums.FileTypeEnum.DIVORCE_CERTIFICATE.code));
        mDivorceContentView.setAlreadyUpPic(mDivorceList);
        //身份证
        mIdCardView.setOrderNum(orderNum);
        mIdCardView.setFileType1(String.valueOf(FileEnums.FileTypeEnum.SELF_ID_CARD_FRONT.code));
        mIdCardView.setFileType2(String.valueOf(FileEnums.FileTypeEnum.SELF_ID_CARD_REVERSE.code));
        UploadFileVo uploadFileVoFont = null;
        UploadFileVo uploadFileVoSide = null;
        if (mListId != null && mListId.size() > 0) {
            for (int i = 0; i < mListId.size(); i++) {
                UploadFileVo uploadFileVo = mListId.get(i);
                if (uploadFileVo != null && String.valueOf(FileEnums.FileTypeEnum.SELF_ID_CARD_FRONT.code).equals(uploadFileVo.getFileType())) {
                    uploadFileVoFont = uploadFileVo;
                } else if (uploadFileVo != null && String.valueOf(FileEnums.FileTypeEnum.SELF_ID_CARD_REVERSE.code).equals(uploadFileVo.getFileType())) {
                    uploadFileVoSide = uploadFileVo;
                }
            }
        }
        mIdCardView.setAlreadyUpPic(uploadFileVoFont, uploadFileVoSide);

        //配偶身份证
        mConsortIdCardView.setOrderNum(orderNum);
        mConsortIdCardView.setFileType1(String.valueOf(FileEnums.FileTypeEnum.HOME_ID_CARD_FRONT.code));
        mConsortIdCardView.setFileType2(String.valueOf(FileEnums.FileTypeEnum.HOME_ID_CARD_REVERSE.code));
        UploadFileVo uploadFileVoFont1 = null;
        UploadFileVo uploadFileVoSide1 = null;
        if (mConsortListId != null && mConsortListId.size() > 0) {
            for (int i = 0; i < mConsortListId.size(); i++) {
                UploadFileVo uploadFileVo = mConsortListId.get(i);
                if (uploadFileVo != null && String.valueOf(FileEnums.FileTypeEnum.HOME_ID_CARD_FRONT.code).equals(uploadFileVo.getFileType())) {
                    uploadFileVoFont1 = uploadFileVo;
                } else if (uploadFileVo != null && String.valueOf(FileEnums.FileTypeEnum.HOME_ID_CARD_REVERSE.code).equals(uploadFileVo.getFileType())) {
                    uploadFileVoSide1 = uploadFileVo;
                }
            }
        }
        mConsortIdCardView.setAlreadyUpPic(uploadFileVoFont1, uploadFileVoSide1);

        //户口本
        mHouseholdContentView.setOrderNum(orderNum);
        mHouseholdContentView.setFileType1(String.valueOf(FileEnums.FileTypeEnum.BOOKLET_FRONT_PAGE.code));
        mHouseholdContentView.setFileType2(String.valueOf(FileEnums.FileTypeEnum.BOOKLET.code));
        UploadFileVo uploadFileVoFont2 = null;
        UploadFileVo uploadFileVoSide2 = null;
        if (mHouseholdList != null && mHouseholdList.size() > 0) {
            for (int i = 0; i < mHouseholdList.size(); i++) {
                UploadFileVo uploadFileVo = mHouseholdList.get(i);
                if (uploadFileVo != null && String.valueOf(FileEnums.FileTypeEnum.BOOKLET_FRONT_PAGE.code).equals(uploadFileVo.getFileType())) {
                    uploadFileVoFont2 = uploadFileVo;
                } else if (uploadFileVo != null && String.valueOf(FileEnums.FileTypeEnum.BOOKLET.code).equals(uploadFileVo.getFileType())) {
                    uploadFileVoSide2 = uploadFileVo;
                }
            }
        }
        mHouseholdContentView.setAlreadyUpPic(uploadFileVoFont2, uploadFileVoSide2);

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
                        IdCardView householdView = (IdCardView) mCurrentView;
                        householdView.setData(PictureSelector.obtainMultipleResult(data), mCurrentTab);
                    } else if (mCurrentView == mDivorceContentView) {//离婚证
                        UpImgCommonView divorce = ((UpImgCommonView) mCurrentView);
                        UpImgCommonView marriage = ((UpImgCommonView) mCurrentView);
                        //从相册中回来数据
                        List<LocalMedia> listMarriage = PictureSelector.obtainMultipleResult(data);
                        //清除显示集合的数据（包括历史上传数据  +  上次选中的图片  + 添加按钮数据）
                        divorce.getMListPic().clear();
                        //将历史上传数据加入到显示的集合当中

                        //再将档次相册回来的数据添加到选中的数据中
                        divorce.getMSelectPic().addAll(listMarriage);
                        //再将总共选中的数据添加到总的显示数据中
                        divorce.getMListPic().addAll(marriage.getMSelectPic());
                        //将添加按钮数据加入到显示集合的数据中
                        LocalMedia divorceDivorce = new LocalMedia();
                        divorceDivorce.isAddPic = true;
                        divorce.getMListPic().add(divorceDivorce);
                        //更新数据
                        divorce.notifyDataSetChanged();
                    } else if (mCurrentView == mMarriageContentView) {//结婚证
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

    /**
     * 保存和拿取数据
     * @param savedInstanceState
     */
    private void saveOrData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            orderNum = intent.getStringExtra("orderNum");
            mListId = (List<UploadFileVo>) intent.getSerializableExtra("mListId");
            mConsortListId = (List<UploadFileVo>) intent.getSerializableExtra("mConsortListId");
            mHouseholdList = (List<UploadFileVo>) intent.getSerializableExtra("mHouseholdList");
            mMarriageList = (List<UploadFileVo>) intent.getSerializableExtra("mMarriageList");
            mDivorceList = (List<UploadFileVo>) intent.getSerializableExtra("mDivorceList");
        } else {
            orderNum = savedInstanceState.getString("orderNum");
            mListId = (List<UploadFileVo>) savedInstanceState.getSerializable("mListId");
            mConsortListId = (List<UploadFileVo>) savedInstanceState.getSerializable("mConsortListId");
            mHouseholdList = (List<UploadFileVo>) savedInstanceState.getSerializable("mHouseholdList");
            mMarriageList = (List<UploadFileVo>) savedInstanceState.getSerializable("mMarriageList");
            mDivorceList = (List<UploadFileVo>) savedInstanceState.getSerializable("mDivorceList");
        }
    }

    public static void newIntent(Context context, String orderNum,
                                 List<UploadFileVo> listId,
                                 List<UploadFileVo> consortListId,
                                 List<UploadFileVo> householdList,
                                 List<UploadFileVo> marriageList,
                                 List<UploadFileVo> divorceList) {
        Intent intent = new Intent(context, PersonDataActivity.class);
        intent.putExtra("orderNum", orderNum);
        intent.putExtra("mListId", (Serializable) listId);
        intent.putExtra("mConsortListId", (Serializable) consortListId);
        intent.putExtra("mHouseholdList", (Serializable) householdList);
        intent.putExtra("mMarriageList", (Serializable) marriageList);
        intent.putExtra("mDivorceList", (Serializable) divorceList);
        context.startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("orderNum", orderNum);
        outState.putSerializable("mListId", (Serializable) mListId);
        outState.putSerializable("mConsortListId", (Serializable) mConsortListId);
        outState.putSerializable("mHouseholdList", (Serializable) mHouseholdList);
        outState.putSerializable("mMarriageList", (Serializable) mMarriageList);
        outState.putSerializable("mDivorceList", (Serializable) mDivorceList);
    }
}
