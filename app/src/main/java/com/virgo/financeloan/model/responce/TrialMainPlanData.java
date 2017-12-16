package com.virgo.financeloan.model.responce;

import java.util.List;

import lombok.Data;

/**
 * 功能说明：
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/15 10:50
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
@Data
public class TrialMainPlanData {
    /**
     * 实际到手金额,单位分.
     */
    private String receiveMoney;
    /**
     * 合同金额,单位分.
     */
    private String contractMoney;
    /**
     * 分期数.
     */
    private String periodCount;
    /**
     * 贷款到期日，日期.
     */
    private String loanMaturityDate;
    /**
     * 预收（坐扣），各个科目的还款计划列表
     */
    private List<TrialData.RepaymentPlanInfo> advancePaymentPlanInfoList;
    /**
     * 分期计划列表
     */
    private List<TrialData.InstallmentPlanInfo> installmentPlanInfoList;
    /**
     * 预计总金额,单位分.
     */
    private String expectedTotalAmount;
}
