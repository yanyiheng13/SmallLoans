package com.virgo.financeloan.loan.model.request;

import lombok.Data;

/**
 * <P>Description: 合同模板填充请求对象. </P>
 * <P>CALLED BY:   齐霞飞 </P>
 * <P>UPDATE BY:   齐霞飞 </P>
 * <P>CREATE DATE: 2017/12/18</P>
 * <P>UPDATE DATE: 2017/12/18</P>
 *
 * @author qixiafei
 * @version 1.0
 * @since java 1.8.0
 */
@Data
public class ContractFillReqVo {

    /**
     * 借款申请编号
     */
    private String loanApplyNo;

    /**
     * 合同编号.
     */
    private String code;
}
