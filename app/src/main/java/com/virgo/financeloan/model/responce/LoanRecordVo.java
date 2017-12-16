package com.virgo.financeloan.model.responce;

import java.io.Serializable;

import lombok.Data;

/**
 * <P>Description: 借款记录查询返回对象Vo. </P>
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
public class LoanRecordVo implements Serializable {

    /**
     * 产品基类编号.
     */
    private String productBaseNo;
    /**
     * 产品基类名称.
     */
    private String productBaseName;

    /**
     * 产品基类描述.
     */
    private String productBaseDescribe;

    /**
     * 产品小类编号.
     */
    private String productNo;

    /**
     * 还款日.
     */
    private String repayDay;

    /**
     * 期数.
     */
    private String period;

    /**
     * 借款申请编号
     */
    private String loanApplyNo;
    /**
     * 实际借款金额，单位分
     */
    private String loanAmount;
    /**
     * 借款时间
     */
    private String loanApplyDate;
    /**
     * 借款截止日期.
     */
    private String loanEndDate;
    /**
     * 借款状态
     */
    private String loanStatus;
    /**
     * 账单编号
     */
    private String bookedBillId;

    /**
     * 月利率.
     */
    private String monthRate;

    // =================  待确认独有字段   =======================

    /**
     * 借款申请金额，单位分.
     */
    private String loanApplyAmount;
    // =================  还款中独有字段   =======================
    /**
     * 未还金额，单位分.
     */
    private String noRepaymentAmount;
}
