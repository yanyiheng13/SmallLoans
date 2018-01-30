package com.virgo.financeloan.user.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.virgo.financeloan.R;
import com.virgo.financeloan.user.model.response.CardVo;
import com.virgo.financeloan.ui.BaseActivity;
import com.virgo.financeloan.ui.view.CustomTitleView;
import com.virgo.financeloan.util.BankListUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能说明：
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/22 10:48
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */

public class CardSelectListActivity extends BaseActivity {
    @BindView(R.id.title_view)
    CustomTitleView mTitleView;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    /**
     * 适配器
     */
    private BaseQuickAdapter<CardVo, BaseViewHolder> mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial_list);
        ButterKnife.bind(this);
        mTitleView.setTitle("选择银行");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new BaseQuickAdapter<CardVo, BaseViewHolder>(R.layout.view_card_item) {
            @Override
            protected void convert(BaseViewHolder helper, final CardVo item) {
                //长安实践监听
                ImageView imageIcon = helper.getView(R.id.card_icon);
                TextView bankName = helper.getView(R.id.card_name);
                TextView bankCategory = helper.getView(R.id.card_category);
                TextView bankNumber = helper.getView(R.id.card_number);
                RelativeLayout mRlParent = helper.getView(R.id.card_contain);


                CardVo cardVoUtil = BankListUtil.getBankObjInfo(item.getBankCode());
                if (cardVoUtil.getDrawableId() == 0) {
//                    imageIcon.setBackgroundResource(R.mipmap.icon_bank_logo_default);
                } else {
                    imageIcon.setBackgroundResource(cardVoUtil.getDrawableId());
                }
                if (!TextUtils.isEmpty(cardVoUtil.getBankName())) {
                    bankName.setText(cardVoUtil.getBankName());
                } else {
                    bankName.setText(item.getOpenAccountBankName());
                }
                //银行卡卡号 脱敏
                bankNumber.setText(item.getBankCardNo());
                //银行卡类型，现阶段只支持储蓄卡
                bankCategory.setText("储蓄卡");
                //银行卡背景
                Drawable d = getResources().getDrawable(R.drawable.shape_bankcard_bg);
                GradientDrawable customBackGround = (GradientDrawable) d;
                if (customBackGround != null && cardVoUtil.getCardBgColor() != 0) {
                    customBackGround.setColor(cardVoUtil.getCardBgColor());
                }
                mRlParent.setBackground(customBackGround);

                RelativeLayout bankContain = helper.getView(R.id.card_contain);
                bankContain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra("data", item);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }
                });
            }
        };
        View viewFooter = LayoutInflater.from(this).inflate(R.layout.view_layout_footer, null);
        mAdapter.addFooterView(viewFooter);
        mRecyclerView.setAdapter(mAdapter);
        List<CardVo> cardList = BankListUtil.getBankList();
        mAdapter.setNewData(cardList);

    }

    public static void newIntent(Context context) {
        Intent intent = new Intent(context, CardSelectListActivity.class);
        ((Activity)context).startActivityForResult(intent, 100);
    }
}
