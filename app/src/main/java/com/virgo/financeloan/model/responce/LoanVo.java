package com.virgo.financeloan.model.responce;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 功能说明：产品列表item对象
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-11-27
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
@Data
public class LoanVo implements Serializable {
    /**
     * 产品大类编号.
     */
    private String productClassifyNo;
    /**
     * 产品介绍 首页展示使用
     */
    private String introduce;

    /**
     * 产品大类名称.
     */
    private String productClassifyName;

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
     * 最高限额，单位分.
     */
    private String singleMaxLoanAmount;

    /**
     * 参考月利率，来源于PRODUCT_BASE_BUSINESS_MIN_LOAN_RATE
     */
    private String refMonthRate;

    /**
     * 最大期数.
     */
    private String maxAging;

    /**
     * 贷款用途列表.
     */
    private List<LoanUsingVo> loanPurposeInfoList;

    /**
     * 还款方式和还款期数列表对象列表.
     */
    private List<RepaymentWayAndAgingVo> repaymentWayAndAgingListCollection;

}
