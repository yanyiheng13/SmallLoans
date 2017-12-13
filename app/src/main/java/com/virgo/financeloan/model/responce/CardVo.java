package com.virgo.financeloan.model.responce;

import java.io.Serializable;

import lombok.Data;

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
    private Integer cardType;

    /**
     * 银行名称.
     */
    private String bankName;
}
