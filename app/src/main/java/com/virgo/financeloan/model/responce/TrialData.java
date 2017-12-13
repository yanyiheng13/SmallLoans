package com.virgo.financeloan.model.responce;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <P>Description: . </P>
 * <P>CALLED BY:   齐霞飞 </P>
 * <P>UPDATE BY:   齐霞飞 </P>
 * <P>CREATE DATE: 2017/11/23</P>
 * <P>UPDATE DATE: 2017/11/23</P>
 *
 * @author qixiafei
 * @version 1.0
 * @since java 1.7.0
 */
@Data
public class TrialData {

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
    private List<RepaymentPlanInfo> advancePaymentPlanInfoList;
    /**
     * 分期计划列表
     */
    private List<InstallmentPlanInfo> installmentPlanInfoList;
    /**
     * 预计总金额,单位分.
     */
    private String expectedTotalAmount;

    /**
     * 还款计划信息类.
     */
    @Data
    public static class RepaymentPlanInfo {
        /**
         * 还款科目
         */
        private String repaymentAccount;
        /**
         * 还款科目名称
         */
        private String repaymentAccountName;
        /**
         * 还款金额，单位分.
         */
        private String repaymentAmount;
        /**
         * 当期还款日，日期
         */
        private String repaymentDate;
        /**
         * 剩余金额，单位分.
         */
        private String remainingAmount;
        /**
         * 测算本金，单位分.
         */
        private String calculatePrincipal;
        /**
         * 下次还款日,日期
         */
        private String nextRepaymentDate;
        /**
         * 还款科目还款日,日期
         */
        private String repaymentAccountRepaymentDate;

        /**
         * 还款科目下次还款日,日期
         */
        private String repaymentAccountNextRepaymentDate;
    }

    /**
     * 分期计划类.
     */
    @Data
    public static class InstallmentPlanInfo {
        /**
         * 期数
         */
        private String period;
        /**
         * 当期还款日，日期
         */
        private String repaymentDate;
        /**
         * 下次还款日，日期
         */
        private String nextRepaymentDate;
        /**
         * 还款计划开始日期
         */
        private String repaymentPlanStartDate;
        /**
         * 还款计划结束日期
         */
        private String repaymentPlanEndDate;
        /**
         * 剩余本金,单位分
         */
        private String remainingPrincipal;
        /**
         * 当期总金额，单位分
         */
        private String currentTotalAmount;
        /**
         * 每期各个科目的还款计划信息
         */
        private List<RepaymentPlanInfo> repaymentPlanInfoList;
    }
}
