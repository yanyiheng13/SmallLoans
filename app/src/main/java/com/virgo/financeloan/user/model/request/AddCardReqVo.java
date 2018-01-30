package com.virgo.financeloan.user.model.request;

import lombok.Data;

/**
 * 功能说明：添加银行卡请求model
 *
 * @author： yanyiheng
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/13
 */
@Data
public class AddCardReqVo {
    /**
     * 绑卡id
     */
    private String bindId;
    /**
     * 银行卡账号
     */
    private String bankCardNo;

    /**
     * 银行预留手机号
     */
    
    private String cardPhone;

    /**
     * 身份证号
     */
    
    private String userIdNo;
    /**
     * 卡类型 1：借记卡，2：信用卡'
     */
    
    private Integer cardType;
    /**
     * 卡种名称
     */
    
    private String cardBinName;
    /**
     * 银行行号（联合号）
     */
    
    private String bankNo;
    /**
     * 银行编号
     */
    
    private String bankCode;
    /**
     * 银行名称
     */
    
    private String bankName;
    /**
     * 账户名称
     */
    private String bankAccountName;
    /**
     * 账户类别(对公账户/对私账户)
     */
    
    private String bankAccountCategory;
    /**
     * 开户行名称
     */
    private String openAccountBankName;
}
