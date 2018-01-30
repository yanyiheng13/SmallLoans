package com.virgo.financeloan.user.model.response;

import java.io.Serializable;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 功能说明：
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/11 11:47
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
@Data
public class CardVo implements Serializable {
    /**
     * 绑卡id
     */
    private String bindId;
    /**
     * 卡号.
     */
    private String bankCardNo;

    /**
     * 账户名称.
     */
    private String bankAccountName;

    /**
     * 开户行名称.
     */
    private String openAccountBankName;

    /**
     * 卡类型,1：借记卡，2：信用卡.
     */
    private String cardType;

    /**
     * 银行名称.
     */
    private String bankName;

    /**
     * 银行编号
     */
    private String bankCode;


    /**
     * 这两个字段为人为添加字段，用于控制银行卡背景颜色的
     */
    private int cardBgColor;
    private int drawableId;


    public CardVo() {

    }
    public CardVo(String bankCode, String bankName, int cardBgColor, String cardType, int drawableId) {
        this.bankCode = bankCode;
        this.bankName = bankName;
        this.cardBgColor = cardBgColor;
        this.cardType = cardType;
        this.drawableId = drawableId;

    }
}
