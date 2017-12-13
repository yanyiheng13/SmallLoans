package com.virgo.financeloan.model.request;

import lombok.Data;

/**
 * 功能说明：添加银行卡请求model
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/13 13:54
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
@Data
public class AddBankCardReqVo {
    /**
     * 银行卡账号
     */
    private String bankCardNo;
    /**
     * 账户名称
     */
    private String bankAccountName;
    /**
     * 开户行名称
     */
    private String openAccountBankName;
    //下面这个字段是删除的时候需要，上面字段是添加的时候需要
    /**
     * 绑卡ID
     */
    private String bindId;
}
