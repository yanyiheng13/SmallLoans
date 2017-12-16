package com.virgo.financeloan.model.request;

import lombok.Data;

/**
 * 功能说明：我的页面-待用户确认-用户贷款确认试算.
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/15 10:45
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
@Data
public class TrialByProductNoReqVo {
    /**
     * 产品小类编号
     */
    private String productNo;

    /**
     * 贷款开始日期.
     */
    private String loanStartDate;

    /**
     * 还款日.
     */
    private String repaymentDay;

    /**
     * 合同金额.
     */
    private String contractMoney;

    /**
     * 期数.
     */
    private String periodCount;
}
