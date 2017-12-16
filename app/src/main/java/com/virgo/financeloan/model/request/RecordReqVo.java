package com.virgo.financeloan.model.request;

import lombok.Data;

/**
 * 功能说明：贷款明细请求参数
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/14 15:40
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
@Data
public class RecordReqVo {
    /**
     * 借款申请编号.
     */
    private String loanApplyNo;
    /**
     * 账单编号
     */
    private String bookedBillId;
}
