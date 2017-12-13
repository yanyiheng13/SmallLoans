package com.virgo.financeloan.model.request;

import lombok.Data;

/**
 * 功能说明：待确认订单用户确认或者取消请求
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 17-11-26
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
@Data
public class LoanUserConfirmReqVo {

    /**
     * 借款申请编号.
     */
    private String loanApplyNo;

    /**
     * 是否确认，确认-true，放弃-false
     */
    private Boolean confirm;
}
