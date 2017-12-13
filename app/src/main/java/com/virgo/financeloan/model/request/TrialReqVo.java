package com.virgo.financeloan.model.request;

import java.io.Serializable;

import lombok.Data;

/**
 * 功能说明：
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-11-26
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
@Data
public class TrialReqVo implements Serializable {

    /*
     * 产品基类编号.
     */
    private String productBaseNo;

    /**
     * 还款方式序号.
     */
    private String repaymentWaySerialNumber;

    /**
     * 分期编号.
     */
    private String agingNo;

    /**
     * 金额，单位：分.
     */
    private String loanAmt;

    private String rate;
}
